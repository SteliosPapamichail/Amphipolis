package com.csd4020.controller;

import com.csd4020.exceptions.Exceptions;
import com.csd4020.model.Board.Board;
import com.csd4020.model.Player;
import com.csd4020.model.cards.*;
import com.csd4020.model.tiles.findings.Amphora;
import com.csd4020.model.tiles.findings.Mosaic;
import com.csd4020.model.tiles.findings.Skeleton;
import com.csd4020.model.tiles.findings.Statue;
import com.csd4020.view.AmphipolisFrame;
import com.csd4020.view.TileLabels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The Controller class coordinates interactions
 * between the View and the Model.
 *
 * @author Stelios Papamichail csd4020
 */
public class Controller {
    private final AmphipolisFrame view; // view reference
    private final Board boardModel; // model reference
    private Player activePlayer; // the player who's playing this turn
    private Card activeCard = null; // the currently active card (if any)
    private String tileAreaUsedAtBeginningOfTurn = ""; // the tile area that the player took tiles from at the beginning of the turn
    private boolean canDraw = true; // tracks whether the player can use the draw button or not
    private boolean isCardEffectActive = false; // when this is true, the player can't end his turn before executing the card's effect
    private boolean cardUsedThisRound = false; // when a card has been used this round, this will be true indicating that no other card can be played this round
    private int remainingTilesThisRound = 2; // keeps track of how many tiles the player can draw each round
    private int extraTilesThisRound = 0; // keep track of the total extra tiles the player can draw due to a card effect this round
    // keep track of the extra tiles that the player is allowed to take this round due to a card effect
    private int cardEffectNumOfExtraAmphoras = 0;
    private int cardEffectNumOfExtraMosaics = 0;
    private int cardEffectNumOfExtraSkeletons = 0;
    private int cardEffectNumOfExtraStatues = 0;
    // used by the assistant and archaeologist cards to determine how many extra tiles the player can take from one specific area
    private int extraTilesFromSameArea = 0;
    private final ArrayList<Player> players = new ArrayList<>(4);

    /**
     * Constructor for the Controller class.
     *
     * @param view       A JFrame subclass containing the final GUI of the game.
     * @param boardModel A Model class representing the Board of the game and its functionalities.
     *                   Precondition: view is a subclass of JFrame & exposes all necessary view components to the Controller & boardModel
     *                   is a model class that exposes all necessary functionality of the game's board to the Controller..
     *                   Postcondition: A Controller object is created which coordinates everything needed for the uninterrupted flow of a game lot.
     */
    public Controller(AmphipolisFrame view, Board boardModel) {
        this.view = view;
        this.boardModel = boardModel;

        // set draw, endCardEffect and end turn button listeners
        view.getInfoPanel().getDrawBtn().addActionListener(new DrawTileListener());
        view.getInfoPanel().getEndTurnBtn().addActionListener(new EndTurnListener());
        view.getInfoPanel().getStopCardEffectBtn().addActionListener(new StopCardEffectListener());

        // set mouse listeners for each tile in each area
        for (TileLabel tl : view.getBoard().getAmphoraArea().getTileLabels()) {
            tl.addMouseListener(new TakeTileFromAreaListener(tl));
        }
        for (TileLabel tl : view.getBoard().getMosaicArea().getTileLabels()) {
            tl.addMouseListener(new TakeTileFromAreaListener(tl));
        }
        for (TileLabel tl : view.getBoard().getSkeletonArea().getTileLabels()) {
            tl.addMouseListener(new TakeTileFromAreaListener(tl));
        }
        for (TileLabel tl : view.getBoard().getStatueArea().getTileLabels()) {
            tl.addMouseListener(new TakeTileFromAreaListener(tl));
        }

        // set mouse listeners for each card
        view.getInfoPanel().getArchaeologistCard().addMouseListener(new PlayCardListener("Archaeologist"));
        view.getInfoPanel().getAssistantCard().addMouseListener(new PlayCardListener("Assistant"));
        view.getInfoPanel().getDiggerCard().addMouseListener(new PlayCardListener("Digger"));
        view.getInfoPanel().getProfessorCard().addMouseListener(new PlayCardListener("Professor"));


        // create players
        Player p1 = new Player("Player 1", Color.RED);
        Player p2 = new Player("Player 2", Color.GREEN);
        Player p3 = new Player("Player 3", Color.BLUE);
        Player p4 = new Player("Player 4", Color.YELLOW);
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        // initialize model
        boardModel.initializeBoard(players);
        boardModel.drawAndPlaceInitialTiles();
        // setup gui
        updateTileCounts();
        startGame();

        // set the active player to be the first player and init UI
        activePlayer = players.get(0);
        setPlayerInfo(players.get(0));
    }

    /**
     * Called when the Draw button is clicked the first time each round.
     * Responsible for drawing tiles from the bag
     * and placing them on the board.
     */
    private void drawTilesFromBag() {
        boardModel.draw();
        updateTileCounts();
        // check if the game should end
        if (boardModel.shouldGameEnd()) {
            displayWinner();
        }
    }

    /**
     * Displays the winner of the game in a dialog and exits upon clicking 'OK'.
     */
    private void displayWinner() {
        boardModel.determineWinner(players);
        int maxPts = 0;
        Player winner = null;
        for(Player p:players) {
            if(p.getFinalPoints() > maxPts) {
                maxPts = p.getFinalPoints();
                winner = p;
            }
        }
        if (winner != null) {
            StringBuilder dialogMsg = new StringBuilder();
            dialogMsg.append(winner.getName()).append(" is the winner!\n\n");
            players.forEach(player -> dialogMsg.append(player.getName()).append(" gathered ").append(player.getFinalPoints()).append("pts\n"));
            JOptionPane.showMessageDialog(null, dialogMsg, "WINNER ANNOUNCEMENT", JOptionPane.INFORMATION_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "Everyone had 0 points!", "Invalid game", JOptionPane.ERROR_MESSAGE); // all players had 0 pts
        System.exit(0);
    }

    /**
     * Updates the view's tile count of each tile in every area based on the model.
     */
    private void updateTileCounts() {

        for (TileLabel tl : view.getBoard().getAmphoraArea().getTileLabels()) {
            AmphoraLabel amphoraLabel = (AmphoraLabel) tl;
            int count = boardModel.getAmphoraTilesArea().getAmphoraCountOfColor(amphoraLabel.getColor());
            tl.setText("x" + count);
        }

        Arrays.stream(view.getBoard().getMosaicArea().getTileLabels()).forEach(tileLabel ->
                tileLabel.setText(("x" + boardModel.getMosaicTilesArea().getMosaicCountOfColor(((MosaicLabel) tileLabel).getColor()))));
        Arrays.stream(view.getBoard().getStatueArea().getTileLabels()).forEach(tileLabel ->
                tileLabel.setText(("x" + boardModel.getStatueTilesArea().getStatueCountOfCategory(((StatueLabel) tileLabel).getType()))));
        Arrays.stream(view.getBoard().getSkeletonArea().getTileLabels()).forEach(tileLabel ->
                tileLabel.setText(("x" + boardModel.getSkeletonTilesArea()
                        .getSkeletonCountOfCategoryAndPart(((SkeletonLabel) tileLabel).getType(), ((SkeletonLabel) tileLabel).getPart()))));
        Arrays.stream(view.getBoard().getLandslideArea().getTileLabels()).forEach(tileLabel ->
                tileLabel.setText(("x" + boardModel.getLandSlideTileArea().getLandslideCount())));
    }

    /**
     * Called when the End Turn button is clicked. Responsible
     * for handling end of turn events, such as swapping player info and
     * starting the next player's turn.
     */
    private void endTurn() {
        int playerIndex = players.indexOf(activePlayer);
        // if the last player played this turn, start from the beginning of the list
        if (playerIndex == 3) playerIndex = 0;
        else playerIndex++;
        activePlayer = players.get(playerIndex); // find the next active player

        setPlayerInfo(activePlayer);
        setCanDraw(true);
        remainingTilesThisRound = 2;
        tileAreaUsedAtBeginningOfTurn = "";
        cardUsedThisRound = false;
        activeCard = null;
        cardEffectNumOfExtraAmphoras = 0;
        cardEffectNumOfExtraMosaics = 0;
        cardEffectNumOfExtraSkeletons = 0;
        cardEffectNumOfExtraStatues = 0;
        extraTilesFromSameArea = 0;
        extraTilesThisRound = 0;
    }

    /**
     * This method is responsible for showing the GUI of the game
     * so that the game can begin.
     */
    public void startGame() {
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setResizable(false);
        view.pack();
        /*
        From javadoc: If the component is null, or the GraphicsConfiguration associated with this component is null,
         the window is placed in the center of the screen.
         */
        view.setLocationRelativeTo(null); // places the window in the center of the screen
        view.setVisible(true);
    }

    /**
     * Method responsible for updating the Player UI info (name,tile collection,cards)
     * with the passed player's data.
     *
     * @param curPlayer The current player
     */
    private void setPlayerInfo(Player curPlayer) {
        setPlayerNameInView(curPlayer.getName());
        setPlayerCardSetInView(curPlayer.getCards());
        setPlayerTilesListInView();
    }

    /**
     * Method responsible for updating the active player's name
     * shown in the UI.
     *
     * @param playerName The current player's name
     */
    private void setPlayerNameInView(String playerName) {
        view.getInfoPanel().setPlayerName(playerName);
    }

    /**
     * Method responsible for updating the UI with the active player's card set.
     *
     * @param playerCards The current player's cards
     */
    private void setPlayerCardSetInView(Card[] playerCards) {
        for (Card c : playerCards) view.getInfoPanel().setUpCard(c.getName(), c.hasBeenPlayed());
    }

    /**
     * Method responsible for changing the player's tile collection when
     * the previous player has ended his turn.
     */
    private void setPlayerTilesListInView() {
        Optional<TileLabel> tl;

        // Create separate lists for each tile label subtype using streams
        List<TileLabel> amphoraLabels = Arrays.stream(view.getPlayerTiles().getPlayerTiles()).filter(tileLabel ->
                tileLabel instanceof AmphoraLabel).collect(Collectors.toList());

        List<TileLabel> mosaicLabels = Arrays.stream(view.getPlayerTiles().getPlayerTiles())
                .filter(tileLabel -> tileLabel instanceof MosaicLabel).collect(Collectors.toList());

        List<TileLabel> skeletonLabels = Arrays.stream(view.getPlayerTiles().getPlayerTiles())
                .filter(tileLabel -> tileLabel instanceof SkeletonLabel).collect(Collectors.toList());

        List<TileLabel> statueLabels = Arrays.stream(view.getPlayerTiles().getPlayerTiles())
                .filter(tileLabel -> tileLabel instanceof StatueLabel).collect(Collectors.toList());


        // Update the player's count for each tile in each list based on the model

        // amphora label updates
        tl = amphoraLabels.stream().filter(tileLabel -> ((AmphoraLabel) tileLabel).getColor().equals("Red")).findFirst();
        tl.get().setText("x" + activePlayer.countAmphorasOfColor("Red"));

        tl = amphoraLabels.stream().filter(tileLabel -> ((AmphoraLabel) tileLabel).getColor().equals("Green")).findFirst();
        tl.get().setText("x" + activePlayer.countAmphorasOfColor("Green"));

        tl = amphoraLabels.stream().filter(tileLabel -> ((AmphoraLabel) tileLabel).getColor().equals("Blue")).findFirst();
        tl.get().setText("x" + activePlayer.countAmphorasOfColor("Blue"));

        tl = amphoraLabels.stream().filter(tileLabel -> ((AmphoraLabel) tileLabel).getColor().equals("Purple")).findFirst();
        tl.get().setText("x" + activePlayer.countAmphorasOfColor("Purple"));

        tl = amphoraLabels.stream().filter(tileLabel -> ((AmphoraLabel) tileLabel).getColor().equals("Brown")).findFirst();
        tl.get().setText("x" + activePlayer.countAmphorasOfColor("Brown"));

        tl = amphoraLabels.stream().filter(tileLabel -> ((AmphoraLabel) tileLabel).getColor().equals("Yellow")).findFirst();
        tl.get().setText("x" + activePlayer.countAmphorasOfColor("Yellow"));

        // Mosaic label updates
        tl = mosaicLabels.stream().filter(tileLabel -> ((MosaicLabel) tileLabel).getColor().equals("Yellow")).findFirst();
        tl.get().setText("x" + activePlayer.countMosaicsOfColor("Yellow"));

        tl = mosaicLabels.stream().filter(tileLabel -> ((MosaicLabel) tileLabel).getColor().equals("Green")).findFirst();
        tl.get().setText("x" + activePlayer.countMosaicsOfColor("Green"));

        tl = mosaicLabels.stream().filter(tileLabel -> ((MosaicLabel) tileLabel).getColor().equals("Red")).findFirst();
        tl.get().setText("x" + activePlayer.countMosaicsOfColor("Red"));

        // skeleton label updates
        tl = skeletonLabels.stream().filter(tileLabel -> ((SkeletonLabel) tileLabel).getPart().equals("Top")
                && ((SkeletonLabel) tileLabel).getType().equals("Big")).findFirst();
        tl.get().setText("x" + activePlayer.countSkeletonOfSizeAndPart("Big", "Top"));

        tl = skeletonLabels.stream().filter(tileLabel -> ((SkeletonLabel) tileLabel).getPart().equals("Bottom")
                && ((SkeletonLabel) tileLabel).getType().equals("Big")).findFirst();
        tl.get().setText("x" + activePlayer.countSkeletonOfSizeAndPart("Big", "Bottom"));

        tl = skeletonLabels.stream().filter(tileLabel -> ((SkeletonLabel) tileLabel).getPart().equals("Top")
                && ((SkeletonLabel) tileLabel).getType().equals("Small")).findFirst();
        tl.get().setText("x" + activePlayer.countSkeletonOfSizeAndPart("Small", "Top"));

        tl = skeletonLabels.stream().filter(tileLabel -> ((SkeletonLabel) tileLabel).getPart().equals("Bottom")
                && ((SkeletonLabel) tileLabel).getType().equals("Small")).findFirst();
        tl.get().setText("x" + activePlayer.countSkeletonOfSizeAndPart("Small", "Bottom"));

        // statue label updates
        tl = statueLabels.stream().filter(tileLabel -> ((StatueLabel) tileLabel).getType().equals("Sphinx")).findFirst();
        tl.get().setText("x" + activePlayer.countStatueOfCategory("Sphinx"));

        tl = statueLabels.stream().filter(tileLabel -> ((StatueLabel) tileLabel).getType().equals("Caryatid")).findFirst();
        tl.get().setText("x" + activePlayer.countStatueOfCategory("Caryatid"));
    }

    /**
     * Method responsible for executing the effect of the selected
     * card if it hasn't been used already.
     *
     * @param cardName The card the player selected to play
     */
    private void handlePlayCardEvent(String cardName) {
        Optional<Card> cardToPlay = Arrays.stream(activePlayer.getCards()).filter(card -> card.getName().equals(cardName)).findFirst();
        if (cardToPlay.isPresent()) {
            if (!cardToPlay.get().hasBeenPlayed() && !tileAreaUsedAtBeginningOfTurn.isEmpty() && !cardUsedThisRound) { // card hasn't been played
                boardModel.playCard(activePlayer, cardToPlay.get());
                view.getInfoPanel().setUpCard(cardName, cardToPlay.get().hasBeenPlayed());
                activeCard = cardToPlay.get();
                enableCardEffect(cardName);
            } else {
                if (tileAreaUsedAtBeginningOfTurn.isEmpty())
                    JOptionPane.showMessageDialog(null, "You must choose a tile from an area first!");
                else if (cardToPlay.get().hasBeenPlayed())
                    JOptionPane.showMessageDialog(null, "You can't play this card again!");
                else JOptionPane.showMessageDialog(null, "You can't play another card this round!");
            }
        }
    }

    /**
     * Enables the effect of the card matching the given card name.
     *
     * @param cardName The name of the card whose effect to enable
     *                 Precondition: cardName is a valid card name
     *                 Postcondition: The effect will be enabled
     */
    private void enableCardEffect(String cardName) {
        isCardEffectActive = true;
        cardUsedThisRound = true;
        // determine which card was played and perform necessary actions to enable its effect
        switch (cardName) {
            case "Professor":
                // player can take 1 tile from each area excluding the one he chose earlier
                switch (tileAreaUsedAtBeginningOfTurn) {
                    case "Amphora":
                        cardEffectNumOfExtraMosaics = 1;
                        cardEffectNumOfExtraStatues = 1;
                        cardEffectNumOfExtraSkeletons = 1;
                        break;
                    case "Mosaic":
                        cardEffectNumOfExtraAmphoras = 1;
                        cardEffectNumOfExtraStatues = 1;
                        cardEffectNumOfExtraSkeletons = 1;
                        break;
                    case "Skeleton":
                        cardEffectNumOfExtraMosaics = 1;
                        cardEffectNumOfExtraStatues = 1;
                        cardEffectNumOfExtraAmphoras = 1;
                        break;
                    default:  // equals "Statue"
                        cardEffectNumOfExtraMosaics = 1;
                        cardEffectNumOfExtraAmphoras = 1;
                        cardEffectNumOfExtraSkeletons = 1;
                        break;
                }
                extraTilesThisRound = 3;
                break;
            case "Digger":
                // player can take up to two tiles from the area he chose earlier
                switch (tileAreaUsedAtBeginningOfTurn) {
                    case "Amphora":
                        cardEffectNumOfExtraAmphoras = 2;
                        break;
                    case "Mosaic":
                        cardEffectNumOfExtraMosaics = 2;
                        break;
                    case "Skeleton":
                        cardEffectNumOfExtraSkeletons = 2;
                        break;
                    case "Statue":
                        cardEffectNumOfExtraStatues = 2;
                        break;
                }
                extraTilesThisRound = 2;
                break;
            case "Archaeologist":
                extraTilesFromSameArea = 2; // archaeologist allows up to 2 extra tiles from any area except the previously selected one
                extraTilesThisRound = 2;
                break;
            case "Assistant":
                extraTilesFromSameArea++; // assistant allows 1 extra tile from any area
                extraTilesThisRound = 1;
                break;
        }
    }

    /**
     * Performs all necessary actions to stop the currently active
     * card's effect.
     */
    public void disableCardEffect() {
        activeCard = null;
        cardEffectNumOfExtraAmphoras = 0;
        cardEffectNumOfExtraMosaics = 0;
        cardEffectNumOfExtraSkeletons = 0;
        cardEffectNumOfExtraStatues = 0;
        extraTilesFromSameArea = 0;
        extraTilesThisRound = 0;
        isCardEffectActive = false;
    }

    /**
     * Observer function which returns whether the player
     * can draw or not.
     */
    public boolean getCanDraw() {
        return canDraw;
    }

    /**
     * Transformer function which changes whether the
     * player can draw or not.
     *
     * @param canDraw The value to set canDraw to
     */
    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }

    /**
     * Inner class which implements the ActionListener interface in order
     * to be used as the View's Draw button listener.
     */
    class DrawTileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getCanDraw()) {
                drawTilesFromBag();
                setCanDraw(false);
            } else {
                JOptionPane.showMessageDialog(null, "You've already drawn tiles this turn!");
            }
        }
    }

    /**
     * Inner class which implements the ActionListener interface in order
     * to be used as the View's End turn button listener.
     */
    class EndTurnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            endTurn();
        }
    }

    /**
     * Inner class which implements the ActionListener interface in order
     * to be used as the View's Stop card effect button listener.
     */
    class StopCardEffectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isCardEffectActive) {
                disableCardEffect();
            } else {
                JOptionPane.showMessageDialog(null,"There are no active cards!","Error!",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Inner class which implements the MouseListener interface in order
     * to allow the user to click on a Tile of the board and interact with it.
     */
    class TakeTileFromAreaListener implements MouseListener {

        private final TileLabel clickedTile;

        /**
         * Constructor for the TakeTileFromAreaListener class.
         *
         * @param clickedTile The TileLabel object whose data to change when it is clicked.
         *                    Precondition: clickedTile instanceof TileLabel
         *                    Postcondition: A new TakeTileFromAreaListener object will be created which executes the
         *                    necessary code when the given tile label is clicked.
         */
        public TakeTileFromAreaListener(TileLabel clickedTile) {
            this.clickedTile = clickedTile;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int totalTilesRemainingThisRound = remainingTilesThisRound + extraTilesThisRound;

            if (clickedTile.getCount() > 0 && totalTilesRemainingThisRound > 0) {
                Optional<TileLabel> optionalTileLabel;
                TileLabel[] playerTiles = view.getPlayerTiles().getPlayerTiles();

                // Figure out which tile label was clicked in order to decrease its count and also update the model

                if (clickedTile instanceof AmphoraLabel) {
                    // update selected tile area the first time an area is selected
                    if (tileAreaUsedAtBeginningOfTurn.isEmpty()) {
                        tileAreaUsedAtBeginningOfTurn = "Amphora";
                        remainingTilesThisRound--;
                    }
                    else { // player has already selected a tile area
                        if (!tileAreaUsedAtBeginningOfTurn.equals("Amphora") && !isCardEffectActive) { // show msg and don't complete action
                            showInvalidAreaSelectionDialog();
                            return;
                        }

                        if (isCardEffectActive) {
                            if (!tileAreaUsedAtBeginningOfTurn.equals("Amphora")) { // find out which card has been used and act accordingly
                                if (activeCard instanceof Professor) {
                                    if (cardEffectNumOfExtraAmphoras > 0) {
                                        cardEffectNumOfExtraAmphoras--;
                                        extraTilesThisRound--;
                                        if (extraTilesThisRound == 0) isCardEffectActive = false;
                                    } else {
                                        showExtraAreaTileAlreadyTakenDialog("amphora");
                                        return; // no more amphoras should be drawn due to the effect
                                    }
                                } else if (activeCard instanceof Archaeologist) { // if the player chose to draw tiles from this area using the effect or if he's drawing for the first time after playing the card
                                    if(extraTilesFromSameArea > 0) {
                                        extraTilesFromSameArea--;
                                        extraTilesThisRound--;
                                        if(extraTilesFromSameArea == 0) isCardEffectActive = false;
                                    }
                                } else if(activeCard instanceof Digger) { // digger is active but the player selected a tile from a forbidden area
                                    JOptionPane.showMessageDialog(null,"You can only pick extra tiles from the " + tileAreaUsedAtBeginningOfTurn +
                                            " area while playing the Digger card!");
                                    return;
                                }
                            } else { // tileAreaUsed == "amphora' and card effect is active
                                if (activeCard instanceof Digger) {
                                    if (cardEffectNumOfExtraAmphoras > 0) {
                                        cardEffectNumOfExtraAmphoras--;
                                        extraTilesThisRound--;
                                        if (cardEffectNumOfExtraAmphoras == 0) isCardEffectActive = false;
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null,"Can't take tile from this area when playing the " + activeCard.getName() + " card!");
                                    return;
                                }
                            }
                            if (activeCard instanceof Assistant && extraTilesFromSameArea > 0) {
                                extraTilesFromSameArea--;
                                extraTilesThisRound--;
                                if (extraTilesFromSameArea == 0) isCardEffectActive = false;
                            }
                        } else {
                            remainingTilesThisRound--;
                        }
                    }

                    optionalTileLabel = Arrays.stream(playerTiles).filter(tileLabel ->
                            tileLabel instanceof AmphoraLabel).
                            filter(tileLabel -> tileLabel.equals(clickedTile)).findFirst();


                    try {
                        boardModel.takeTileFromArea(activePlayer, new Amphora(((AmphoraLabel) optionalTileLabel.get()).getColor()));
                    } catch (Exceptions.NotRemovableTileException | Exceptions.NoSuchTileFoundException notRemovableTileException) {
                        notRemovableTileException.printStackTrace();
                    }

                    if (optionalTileLabel.isPresent()) {
                        optionalTileLabel.get().increaseCnt();
                    }

                } else if (clickedTile instanceof MosaicLabel) {
                    // update selected tile area the first time an area is selected
                    if (tileAreaUsedAtBeginningOfTurn.isEmpty()) {
                        tileAreaUsedAtBeginningOfTurn = "Mosaic";
                        remainingTilesThisRound--;
                    }
                    else { // player has selected a tile area
                        if (!tileAreaUsedAtBeginningOfTurn.equals("Mosaic") && !isCardEffectActive) { // show msg and don't complete action
                            showInvalidAreaSelectionDialog();
                            return;
                        }

                        if (isCardEffectActive) {
                            if (!tileAreaUsedAtBeginningOfTurn.equals("Mosaic")) { // find out which card has been used and act accordingly
                                if (activeCard instanceof Professor) {
                                    if (cardEffectNumOfExtraMosaics > 0) {
                                        cardEffectNumOfExtraMosaics--;
                                        extraTilesThisRound--;
                                        if (extraTilesThisRound == 0) isCardEffectActive = false;
                                    }  else {
                                        showExtraAreaTileAlreadyTakenDialog("mosaic");
                                        return; // no more mosaics should be drawn due to the effect
                                    }
                                } else if (activeCard instanceof Archaeologist) { // if the player chose to draw tiles from this area using the effect or if he's drawing for the first time after playing the card
                                    if(extraTilesFromSameArea > 0) {
                                        extraTilesFromSameArea--;
                                        extraTilesThisRound--;
                                        if(extraTilesFromSameArea == 0) isCardEffectActive = false;
                                    }
                                } else if(activeCard instanceof Digger) { // digger is active but the player selected a tile from a forbidden area
                                    JOptionPane.showMessageDialog(null,"You can only pick extra tiles from the " + tileAreaUsedAtBeginningOfTurn +
                                            " area while playing the Digger card!");
                                    return;
                                }
                            } else { // tileAreaUsed == "mosaic' and card effect is active
                                if (activeCard instanceof Digger) {
                                    if (cardEffectNumOfExtraMosaics > 0) {
                                        cardEffectNumOfExtraMosaics--;
                                        extraTilesThisRound--;
                                        if (cardEffectNumOfExtraMosaics == 0) isCardEffectActive = false;
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null,"Can't take tile from this area when playing the " + activeCard.getName() + " card!");
                                    return;
                                }
                            }
                            if (activeCard instanceof Assistant && extraTilesFromSameArea > 0) {
                                extraTilesFromSameArea--;
                                extraTilesThisRound--;
                                if (extraTilesFromSameArea == 0) isCardEffectActive = false;
                            }
                        } else {
                            remainingTilesThisRound--;
                        }
                    }

                    optionalTileLabel = Arrays.stream(playerTiles).filter(tileLabel ->
                            tileLabel instanceof MosaicLabel).
                            filter(tileLabel -> tileLabel.equals(clickedTile)).findFirst();


                    try {
                        boardModel.takeTileFromArea(activePlayer, new Mosaic(((MosaicLabel) optionalTileLabel.get()).getColor()));
                    } catch (Exceptions.NotRemovableTileException | Exceptions.NoSuchTileFoundException notRemovableTileException) {
                        notRemovableTileException.printStackTrace();
                    }

                    if (optionalTileLabel.isPresent()) {
                        optionalTileLabel.get().increaseCnt();
                    }

                } else if (clickedTile instanceof SkeletonLabel) {
                    // update selected tile area the first time an area is selected
                    if (tileAreaUsedAtBeginningOfTurn.isEmpty()) {
                        tileAreaUsedAtBeginningOfTurn = "Skeleton";
                        remainingTilesThisRound--;
                    }
                    else { // player has selected a tile area
                        if (!tileAreaUsedAtBeginningOfTurn.equals("Skeleton") && !isCardEffectActive) { // show msg and don't complete action
                            showInvalidAreaSelectionDialog();
                            return;
                        }

                        if (isCardEffectActive) {
                            if (!tileAreaUsedAtBeginningOfTurn.equals("Skeleton")) { // find out which card has been used and act accordingly
                                if (activeCard instanceof Professor) {
                                    if (cardEffectNumOfExtraSkeletons > 0) {
                                        cardEffectNumOfExtraSkeletons--;
                                        extraTilesThisRound--;
                                        if (extraTilesThisRound == 0) isCardEffectActive = false;
                                    }  else {
                                        showExtraAreaTileAlreadyTakenDialog("skeleton");
                                        return; // no more skeletons should be drawn due to the effect
                                    }
                                } else if (activeCard instanceof Archaeologist) { // if the player chose to draw tiles from this area using the effect or if he's drawing for the first time after playing the card
                                    if(extraTilesFromSameArea > 0) {
                                        extraTilesFromSameArea--;
                                        extraTilesThisRound--;
                                        if(extraTilesFromSameArea == 0) isCardEffectActive = false;
                                    }
                                } else if(activeCard instanceof Digger) { // digger is active but the player selected a tile from a forbidden area
                                    JOptionPane.showMessageDialog(null,"You can only pick extra tiles from the " + tileAreaUsedAtBeginningOfTurn +
                                            " area while playing the Digger card!");
                                    return;
                                }
                            } else { // tileAreaUsed == "skeleton" and card effect is active
                                if (activeCard instanceof Digger) {
                                    if (cardEffectNumOfExtraSkeletons > 0) {
                                        cardEffectNumOfExtraSkeletons--;
                                        extraTilesThisRound--;
                                        if (cardEffectNumOfExtraSkeletons == 0) isCardEffectActive = false;
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null,"Can't take tile from this area when playing the " + activeCard.getName() + " card!");
                                    return;
                                }
                            }
                            if (activeCard instanceof Assistant && extraTilesFromSameArea > 0) {
                                extraTilesFromSameArea--;
                                extraTilesThisRound--;
                                if (extraTilesFromSameArea == 0) isCardEffectActive = false;
                            }
                        } else {
                            remainingTilesThisRound--;
                        }
                    }

                    optionalTileLabel = Arrays.stream(playerTiles).filter(tileLabel ->
                            tileLabel instanceof SkeletonLabel).
                            filter(tileLabel -> tileLabel.equals(clickedTile))
                            .findFirst();


                    try {
                        SkeletonLabel skeleton = (SkeletonLabel) optionalTileLabel.get();
                        boardModel.takeTileFromArea(activePlayer, new Skeleton(skeleton.getType(), skeleton.getPart()));
                    } catch (Exceptions.NotRemovableTileException | Exceptions.NoSuchTileFoundException notRemovableTileException) {
                        notRemovableTileException.printStackTrace();
                    }

                    if (optionalTileLabel.isPresent()) {
                        optionalTileLabel.get().increaseCnt();
                    }

                } else if (clickedTile instanceof StatueLabel) {
                    // update selected tile area the first time an area is selected
                    if (tileAreaUsedAtBeginningOfTurn.isEmpty()) {
                        tileAreaUsedAtBeginningOfTurn = "Statue";
                        remainingTilesThisRound--;
                    }
                    else { // player has selected a tile area
                        if (!tileAreaUsedAtBeginningOfTurn.equals("Statue") && !isCardEffectActive) { // show msg and don't complete action if it doesn't match the prev selected tile area
                            showInvalidAreaSelectionDialog();
                            return;
                        }

                        if (isCardEffectActive) {
                            if (!tileAreaUsedAtBeginningOfTurn.equals("Statue")) { // find out which card has been used and act accordingly
                                if (activeCard instanceof Professor) {
                                    if (cardEffectNumOfExtraStatues > 0) {
                                        cardEffectNumOfExtraStatues--;
                                        extraTilesThisRound--;
                                        if (extraTilesThisRound == 0) isCardEffectActive = false;
                                    }  else {
                                        showExtraAreaTileAlreadyTakenDialog("statue");
                                        return; // no more statues should be drawn due to the effect
                                    }
                                } else if (activeCard instanceof Archaeologist) { // if the player chose to draw tiles from this area using the effect or if he's drawing for the first time after playing the card
                                    if(extraTilesFromSameArea > 0) {
                                        extraTilesFromSameArea--;
                                        extraTilesThisRound--;
                                        if(extraTilesFromSameArea == 0) isCardEffectActive = false;
                                    }
                                } else if(activeCard instanceof Digger) { // digger is active but the player selected a tile from a forbidden area
                                    JOptionPane.showMessageDialog(null,"You can only pick extra tiles from the " + tileAreaUsedAtBeginningOfTurn +
                                            " area while playing the Digger card!");
                                    return;
                                }
                            } else { // tileAreaUsed == "statue" and card effect is active
                                if (activeCard instanceof Digger) {
                                    if (cardEffectNumOfExtraStatues > 0) {
                                        cardEffectNumOfExtraStatues--;
                                        extraTilesThisRound--;
                                        if (cardEffectNumOfExtraStatues == 0) isCardEffectActive = false;
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null,"Can't take tile from this area when playing the " + activeCard.getName() + " card!");
                                    return;
                                }
                            }
                            if (activeCard instanceof Assistant && extraTilesFromSameArea > 0) {
                                extraTilesFromSameArea--;
                                extraTilesThisRound--;
                                if (extraTilesFromSameArea == 0) isCardEffectActive = false;
                            }
                        } else {
                            remainingTilesThisRound--;
                        }
                    }

                    optionalTileLabel = Arrays.stream(playerTiles).filter(tileLabel ->
                            tileLabel instanceof StatueLabel).
                            filter(tileLabel -> tileLabel.equals(clickedTile)).findFirst();


                    StatueLabel statueLbl = (StatueLabel) optionalTileLabel.get();
                    Statue statue;
                    try {
                        statue = (Statue) Class.forName("com.csd4020.model.tiles.findings." + statueLbl.getType()).newInstance();
                        boardModel.takeTileFromArea(activePlayer, statue);
                    } catch (Exceptions.NotRemovableTileException | Exceptions.NoSuchTileFoundException | IllegalAccessException | InstantiationException | ClassNotFoundException notRemovableTileException) {
                        notRemovableTileException.printStackTrace();
                    }

                    if (optionalTileLabel.isPresent()) {
                        optionalTileLabel.get().increaseCnt();
                    }

                } else throw new IllegalArgumentException("Clicked tile isn't valid!");
            } else if (clickedTile.getCount() == 0) {
                JOptionPane.showMessageDialog(null, "You can't take any more tiles of this type!");
            } else {
                JOptionPane.showMessageDialog(null, "You can't take any more tiles this turn!");
            }
        }

        /**
         * Shows a dialog notifying the user that he/she has already taken an extra tile from an area
         * Used for card effects such as that of the Professor card
         * @param tileType The type of the tile already taken
         */
        private void showExtraAreaTileAlreadyTakenDialog(String tileType) {
            JOptionPane.showMessageDialog(null, "You have already taken an extra " + tileType + " tile!");
        }

        /**
         * Shows a dialog notifying the user that the tile area he/she selected is invalid
         */
        private void showInvalidAreaSelectionDialog() {
            JOptionPane.showMessageDialog(null, "You can only choose tiles from the " + tileAreaUsedAtBeginningOfTurn + " area!");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            clickedTile.decreaseCnt();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Inner class which implements the MouseListener interface in order
     * to allow the user to click on a card of the board and interact with it.
     */
    class PlayCardListener implements MouseListener {

        private final String card;

        public PlayCardListener(String cardName) {
            this.card = cardName;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            handlePlayCardEvent(card);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
