package com.csd4020.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * A UI class representing the Player Info panel which
 * holds all necessary player info such as the player's name,
 * active & inactive cards as well as buttons for interacting with the board.
 *
 * @author Stelios Papamichail csd4020
 */
public class PlayerInfoPanel extends JPanel {
    // Text
    private final JLabel playerName;
    private final JLabel useCardText;
    // Buttons
    private final JButton drawBtn;
    private final JButton endTurnBtn;
    private final JButton stopCardEffectBtn;
    // Card Labels
    private final JLabel assistantCard;
    private final JLabel diggerCard;
    private final JLabel archaeologistCard;
    private final JLabel professorCard;

    // default card images
    private Image professorImg;
    private Image assistantImg;
    private Image diggerImg;
    private Image archaeologistImg;

    // disabled card images
    private Image disabledProfessorImg;
    private Image disabledDiggerImg;
    private Image disabledAssistantImg;
    private Image disabledArchaeologistImg;

    /**
     * Constructor for the PlayerInfoPanel UI class.
     */
    public PlayerInfoPanel() {
        super();
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        setSize(300,500);
        setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

        // Initialize attributes
        playerName = new JLabel("[Player]");
        useCardText = new JLabel("Use Card");
        drawBtn = new JButton("Draw tiles");
        endTurnBtn = new JButton("End turn");
        stopCardEffectBtn = new JButton("Stop card effect early");
        assistantCard = new JLabel();
        assistantCard.setToolTipText("The player takes 1 tile from any tile area");
        diggerCard = new JLabel();
        diggerCard.setToolTipText("The player takes up to 2 tiles from the previously selected tile area");
        archaeologistCard = new JLabel();
        archaeologistCard.setToolTipText("The player takes up to 2 tiles from any tile area except from the previously selected one");
        professorCard = new JLabel();
        professorCard.setToolTipText("The player takes 1 tile from each tile area except from the previously selected one");

        setCardImages();
        populateLayout();
    }

    /**
     * Sets each card image and image size
     */
    private void setCardImages() {
        try {
            assistantImg = ImageIO.read(getClass().getResourceAsStream("/assistant.png")).getScaledInstance(150,200,Image.SCALE_SMOOTH);
            assistantCard.setIcon(new ImageIcon(assistantImg));
            disabledAssistantImg = GrayFilter.createDisabledImage(assistantImg);

            diggerImg = ImageIO.read(getClass().getResourceAsStream("/digger.png")).getScaledInstance(150,200,Image.SCALE_SMOOTH);
            diggerCard.setIcon(new ImageIcon(diggerImg));
            disabledDiggerImg = GrayFilter.createDisabledImage(diggerImg);

            archaeologistImg = ImageIO.read(getClass().getResourceAsStream("/archaeologist.png")).getScaledInstance(150,200,Image.SCALE_SMOOTH);
            archaeologistCard.setIcon(new ImageIcon(archaeologistImg));
            disabledArchaeologistImg = GrayFilter.createDisabledImage(archaeologistImg);

            professorImg = ImageIO.read(getClass().getResourceAsStream("/professor.png")).getScaledInstance(150,200,Image.SCALE_SMOOTH);
            professorCard.setIcon(new ImageIcon(professorImg));
            disabledProfessorImg = GrayFilter.createDisabledImage(professorImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the necessary components of the layout to the panel
     */
    private void populateLayout() {
        // Create the grid bag constraints for each component with the necessary attributes
        GridBagConstraints playerTextConstraints = new GridBagConstraints();
        playerTextConstraints.gridx = 0;
        playerTextConstraints.gridy = 0;
        playerTextConstraints.gridwidth = GridBagConstraints.REMAINDER;
        playerTextConstraints.insets = new Insets(0,0,10,0);

        GridBagConstraints useCardTextConstraints = new GridBagConstraints();
        useCardTextConstraints.gridx = 0;
        useCardTextConstraints.gridy = 1;
        useCardTextConstraints.gridwidth = GridBagConstraints.REMAINDER;
        useCardTextConstraints.insets = new Insets(0,0,10,0);

        GridBagConstraints assistantConstraints = new GridBagConstraints();
        assistantConstraints.gridx = 0;
        assistantConstraints.gridy = 2;

        GridBagConstraints diggerConstraints = new GridBagConstraints();
        diggerConstraints.gridx = 1;
        diggerConstraints.gridy = 2;

        GridBagConstraints archaeologistConstraints = new GridBagConstraints();
        archaeologistConstraints.gridx = 0;
        archaeologistConstraints.gridy = 3;

        GridBagConstraints professorConstraints = new GridBagConstraints();
        professorConstraints.gridx = 1;
        professorConstraints.gridy = 3;

        GridBagConstraints drawBtnConstraints = new GridBagConstraints();
        drawBtnConstraints.gridx = 0;
        drawBtnConstraints.gridy = 4;
        drawBtnConstraints.gridwidth = GridBagConstraints.REMAINDER;
        drawBtnConstraints.fill = GridBagConstraints.HORIZONTAL;
        drawBtnConstraints.ipady = 30;
        drawBtnConstraints.insets = new Insets(10,0,10,0);

        GridBagConstraints endTurnBtnConstraints = new GridBagConstraints();
        endTurnBtnConstraints.gridx = 0;
        endTurnBtnConstraints.gridy = 5;
        endTurnBtnConstraints.gridwidth = GridBagConstraints.REMAINDER;
        endTurnBtnConstraints.fill = GridBagConstraints.HORIZONTAL;
        endTurnBtnConstraints.ipady = 30;

        GridBagConstraints stopCardEffectBtnConstraints = new GridBagConstraints();
        stopCardEffectBtnConstraints.gridx = 0;
        stopCardEffectBtnConstraints.gridy = 6;
        stopCardEffectBtnConstraints.gridwidth = GridBagConstraints.REMAINDER;
        stopCardEffectBtnConstraints.fill = GridBagConstraints.HORIZONTAL;
        stopCardEffectBtnConstraints.ipady = 30;
        stopCardEffectBtnConstraints.insets = new Insets(10,0,10,0);

        add(playerName,playerTextConstraints);
        add(useCardText, useCardTextConstraints);
        add(assistantCard,assistantConstraints);
        add(diggerCard,diggerConstraints);
        add(professorCard,professorConstraints);
        add(archaeologistCard,archaeologistConstraints);
        add(drawBtn,drawBtnConstraints);
        add(endTurnBtn,endTurnBtnConstraints);
        add(stopCardEffectBtn,stopCardEffectBtnConstraints);
    }

    /**
     * Visually "disables" the card matching the passed card name
     * to indicate that it has been used.
     * @param cardName The name of the card to be disable
     * Precondition: cardName is a valid card name (i.e. Professor)
     * Postcondition: The selected card will be visually "disabled"
     * @throws IllegalArgumentException When an invalid card name is given
     */
    private void disableCard(String cardName) throws IllegalArgumentException {
        switch (cardName) {
            case "Professor":
                professorCard.setIcon(new ImageIcon(disabledProfessorImg));
                break;
            case "Assistant":
                assistantCard.setIcon(new ImageIcon(disabledAssistantImg));
                break;
            case "Digger":
                diggerCard.setIcon(new ImageIcon(disabledDiggerImg));
                break;
            case "Archaeologist":
                archaeologistCard.setIcon(new ImageIcon(disabledArchaeologistImg));
                break;
            default:
                throw new IllegalArgumentException("You need to pass a valid card name!");
        }
    }

    /**
     * Enables or disables the card matching the given card name based
     * on the value of hasBeenPlayed.
     * @param cardName The card's name
     * @param hasBeenPlayed True if it has been played, false otherwise
     */
    public void setUpCard(String cardName, boolean hasBeenPlayed) {
        if (hasBeenPlayed) {
            disableCard(cardName);
        } else {
            enableCard(cardName);
        }
    }

    /**
     * Visually "enables" the card matching the passed card name
     * to indicate that the card can be used.
     * @param cardName The name of the card to enable
     * Precondition: cardName is a valid card name (i.e. Professor)
     * Postcondition: The selected card will be visually "enabled"
     * @throws IllegalArgumentException When an invalid card name is given
     */
    private void enableCard(String cardName) throws IllegalArgumentException {
        switch (cardName) {
            case "Professor":
                professorCard.setIcon(new ImageIcon(professorImg));
                break;
            case "Assistant":
                assistantCard.setIcon(new ImageIcon(assistantImg));
                break;
            case "Digger":
                diggerCard.setIcon(new ImageIcon(diggerImg));
                break;
            case "Archaeologist":
                archaeologistCard.setIcon(new ImageIcon(archaeologistImg));
                break;
            default:
                throw new IllegalArgumentException("You need to pass a valid card name!");
        }
    }

    /**
     * Updates the player's name
     * with the passed value.
     * @param name The player's name to update the view with
     * Precondition: Name != null
     * Postcondition: The player's name will be updated
     */
    public void setPlayerName(String name) {playerName.setText(name);}

    /**
     * Accessor function which returns the Draw Tile button.
     * @return The Draw tile button
     */
    public JButton getDrawBtn() {
        return drawBtn;
    }

    /**
     * Accessor function which returns the End Turn button.
     * @return The End turn button
     */
    public JButton getEndTurnBtn() {
        return endTurnBtn;
    }

    /**
     * Accessor function which returns the Stop card effect button.
     * @return The stop card effect button
     */
    public JButton getStopCardEffectBtn() {return stopCardEffectBtn;}

    /**
     * Accessor function which returns the Assistant card's JLabel.
     * @return The Assistant card's JLabel
     */
    public JLabel getAssistantCard() {
        return assistantCard;
    }

    /**
     * Accessor function which returns the Digger card's JLabel.
     * @return The Digger card's JLabel
     */
    public JLabel getDiggerCard() {
        return diggerCard;
    }

    /**
     * Accessor function which returns the Archaeologist card's JLabel.
     * @return The Archaeologist card's JLabel
     */
    public JLabel getArchaeologistCard() {
        return archaeologistCard;
    }

    /**
     * Accessor function which returns the Professor card's JLabel.
     * @return The Professor card's JLabel
     */
    public JLabel getProfessorCard() {
        return professorCard;
    }
}
