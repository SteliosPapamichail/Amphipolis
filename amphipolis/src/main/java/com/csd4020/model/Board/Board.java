package com.csd4020.model.Board;

import com.csd4020.exceptions.Exceptions;
import com.csd4020.model.Bag;
import com.csd4020.model.Board.TileAreas.*;
import com.csd4020.model.Player;
import com.csd4020.model.cards.*;
import com.csd4020.model.tiles.LandslideTile;
import com.csd4020.model.tiles.Tile;
import com.csd4020.model.tiles.findings.Amphora;
import com.csd4020.model.tiles.findings.Mosaic;
import com.csd4020.model.tiles.findings.Skeleton;
import com.csd4020.model.tiles.findings.Statue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

/**
 * A Singleton class representing the Board of the game.
 * This class is responsible for determining player turns,
 * board initialization,determining the winner and more.
 *
 * @author Stelios Papamichail csd4020
 */
public class Board {
    private final Bag tilesBag = Bag.getInstance(); // get the bag of tiles
    // Tile areas
    private final LandslideTileArea landSlideTileArea = new LandslideTileArea();
    private final AmphoraTileArea amphoraTilesArea = new AmphoraTileArea();
    private final MosaicTileArea mosaicTilesArea = new MosaicTileArea();
    private final StatueTileArea statueTilesArea = new StatueTileArea();
    private final SkeletonTileArea skeletonTilesArea = new SkeletonTileArea();

    // static variable of type Board used to make this a Singleton class
    private static Board board = null;

    /**
     * Private constructor of the Board class to prevent multiple instances
     */
    private Board() {}

    /**
     * Accessor function which returns the only instance
     * of the Board class.
     * @return The only instance of the Board class
     */
    public static Board getInstance() {
        if (board == null) board = new Board();
        return board;
    }

    /**
     * Performs all necessary initializations to guarantee
     * that the game will flow as expected. This includes but is not
     * limited to :card distribution, bag initialization,
     * player turn order determination, etc.
     */
    public void initializeBoard(ArrayList<Player> players) {
        tilesBag.initialize(); // init bag
        determinePlayOrder(players); // determine order
        // hand out cards to players
        for(Player p:players) p.setCards(createPlayerCards(p));
    }

    /**
     * Creates a set of cards for a player consisting of a single copy
     * of each one.
     * @return A set of cards
     */
    private Card[] createPlayerCards(Player owner) {
        Card[] cards = new Card[4];
        cards[0] = new Professor(owner);
        cards[1] = new Archaeologist(owner);
        cards[2] = new Digger(owner);
        cards[3] = new Assistant(owner);
        return cards;
    }

    /**
     * Removes a tile from the tile area that matches the given tile type
     * and gives it to the passed player.
     * @param player The player to whom the tile will be given
     * @param tile The tile to take from the respective tile area.
     * Precondition: player != null, a tile of the given type exists in the respective area
     * & tile is a valid tile type (i.e. Amphora,Mosaic,etc.)
     * Postcondition: A tile of the matching type will be removed from the appropriate area and given
     * to the player
     * @throws Exceptions.NotRemovableTileException When the given tile class is a non removable type (i.e. landslide tiles)
     * @throws Exceptions.NoSuchTileFoundException When a tile area doesn't contain a tile matching the given attributes of the tile
     */
    public void takeTileFromArea(Player player, Tile tile) throws Exceptions.NotRemovableTileException, Exceptions.NoSuchTileFoundException {
        Tile tileToGiveToPlayer;

        // determine the type of tile to return
        if(tile instanceof LandslideTile) throw new Exceptions.NotRemovableTileException("Landslide tiles can't be removed!");
        else if(tile instanceof Mosaic) tileToGiveToPlayer = getMosaicTilesArea().takeMosaicTile(tile.getColor());
        else if(tile instanceof Amphora) tileToGiveToPlayer = getAmphoraTilesArea().takeAmphoraTile(tile.getColor());
        else if(tile instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) tile;
            tileToGiveToPlayer = getSkeletonTilesArea().takeSkeletonTile(skeleton.getSkeletonCategory(),skeleton.getSkeletonPart());
        }
        else if(tile instanceof Statue) {
            Statue tl = (Statue) tile;
            tileToGiveToPlayer = getStatueTilesArea().takeStatueTile(tl.getStatueCategory());
        } else {
            throw new Exceptions.NoSuchTileFoundException("Invalid tile type");
        }

        if(tileToGiveToPlayer == null) { // one of the takeXTile methods returned NULL
            throw new Exceptions.NoSuchTileFoundException("There are no tiles with the attributes of the given tile in the respective tile area.");
        } else {
            player.addTile(tileToGiveToPlayer);
        }
    }

    /**
     * Randomly determines the order in which the given players will be taking turns
     * @param players The player list
     */
    private void determinePlayOrder(ArrayList<Player> players) {
        Collections.shuffle(players);
    }

    /**
     * Draws tiles from the bag and places them on the board
     */
    public void draw() {
        for(int i=0; i < 4; i++) { // player draws 4 tiles from the bag
            Tile drawnTile = tilesBag.drawTile();
            // determine where to place the tile and place it
            try {
                addTileToArea(drawnTile);
            } catch (Exceptions.IllegalTileTypeException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Draws a tile of each type and places it in the appropriate
     * area.
     * Important: Use only during game initialization
     */
    public void drawAndPlaceInitialTiles() {
        // array of tile subclasses/types
        Class[] tileTypes = {Amphora.class,Mosaic.class,Skeleton.class,Statue.class};
        // get a tile of each type from the bag and add it to the correct area
        for(Class clz : tileTypes) {
            Tile tileToPlace = tilesBag.drawTileOfType(clz);
            try {
                addTileToArea(tileToPlace);
            } catch (Exceptions.IllegalTileTypeException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper function which places the given tile in the appropriate
     * tile area of the board based on its type.
     * @param tile The tile to place
     * @throws Exceptions.IllegalTileTypeException When the given tile isn't of a valid tile type
     */
    private void addTileToArea(Tile tile) throws Exceptions.IllegalTileTypeException {
        if(tile instanceof LandslideTile) getLandSlideTileArea().addTile((LandslideTile) tile);
        else if(tile instanceof Mosaic) getMosaicTilesArea().addTile((Mosaic) tile);
        else if(tile instanceof Amphora) getAmphoraTilesArea().addTile((Amphora) tile);
        else if(tile instanceof Skeleton) getSkeletonTilesArea().addTile((Skeleton) tile);
        else if(tile instanceof Statue) getStatueTilesArea().addTile((Statue) tile);
        else throw new Exceptions.IllegalTileTypeException("The given class must be of type Tile!");
    }

    /**
     * Observer function that checks whether or not the game
     * should end.
     * @return True if the game should end, false otherwise.
     */
    public boolean shouldGameEnd() {// when the landslide area has this number of tiles in it, the game ends
        int numberOfLandSlideTilesToEndGame = 16;
        return getLandSlideTileArea().getAreaTiles().size() >= numberOfLandSlideTilesToEndGame;}

    /**
     * Plays the given card from the given player's hand.
     * @param player The player playing the card
     * @param card The card being played
     */
    public void playCard(Player player, Card card) {
        Optional<Card> cardPlayed = Arrays.stream(player.getCards()).filter(card1 -> card1.getName().equals(card.getName())).findFirst();
        if(cardPlayed.isPresent() && !cardPlayed.get().hasBeenPlayed()) cardPlayed.get().setHasBeenPlayed(true);
    }

    /**
     * Determines the winner of the game once it has ended.
     * @param players The player list.
     * Precondition: The game has ended (shouldGameEnd() == true)
     * Postcondition: The points earned by each player will be successfully set
     */
    public void determineWinner(ArrayList<Player> players) {
        int[] playerPts = new int[4];
        int[] playerCaryatidCnt = new int[4];
        int[] playerSphinxCnt = new int[4];
        int i = 0;

        // calculate points and count statues for each player
        for(Player player : players) {
            playerPts[i] = player.calculatePoints();
            playerSphinxCnt[i] = player.countStatueOfCategory("Sphinx");
            playerCaryatidCnt[i] = player.countStatueOfCategory("Caryatid");
            i++;
        }

        // find the maximum and minimum num of caryatids and sphinx
        int maxNumOfCaryatids = Arrays.stream(playerCaryatidCnt).max().getAsInt();
        int maxNumOfSphinx = Arrays.stream(playerSphinxCnt).max().getAsInt();
        int minNumOfCaryatids = Arrays.stream(playerCaryatidCnt).min().getAsInt();
        int minNumOfSphinx = Arrays.stream(playerSphinxCnt).min().getAsInt();

        // award 6pts to any player who has @maxNumOfCaryatids and/or @maxNumOfSphinx tiles
        i = 0;

        for(Player player:players) {
            int caryatidCnt = player.countStatueOfCategory("Caryatid");
            int sphinxCnt = player.countStatueOfCategory("Sphinx");

            if(maxNumOfCaryatids != 0 && caryatidCnt == maxNumOfCaryatids) playerPts[i] += Statue.getPlayerWithMostTilesPoints();
            else if(caryatidCnt != minNumOfCaryatids) playerPts[i] += Statue.getNonMinMaxPlayerTilesPoints();

            if(maxNumOfSphinx != 0 && sphinxCnt == maxNumOfSphinx) playerPts[i] += Statue.getPlayerWithMostTilesPoints();
            else if(sphinxCnt != minNumOfSphinx) playerPts[i] += Statue.getNonMinMaxPlayerTilesPoints();
            i++;
        }

        i = 0;
        for(Player player : players) {
            player.setFinalPoints(playerPts[i]);
            i++;
        }
    }

    /**
     * Accessor function which returns the landslide tile area.
     */
    public LandslideTileArea getLandSlideTileArea() {
        return landSlideTileArea;
    }

    /**
     * Accessor function which returns the amphora tile area.
     */
    public AmphoraTileArea getAmphoraTilesArea() {
        return amphoraTilesArea;
    }

    /**
     * Accessor function which returns the mosaic tile area.
     */
    public MosaicTileArea getMosaicTilesArea() {
        return mosaicTilesArea;
    }

    /**
     * Accessor function which returns the statue tile area.
     */
    public StatueTileArea getStatueTilesArea() {
        return statueTilesArea;
    }

    /**
     * Accessor function which returns the skeleton tile area.
     */
    public SkeletonTileArea getSkeletonTilesArea() {
        return skeletonTilesArea;
    }
}
