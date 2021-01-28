package com.csd4020.view;

import javax.swing.*;
import java.awt.*;

/**
 * A UI class representing the final GUI of the game.
 *
 * @author Stelios Papamichail csd4020
 */
public class AmphipolisFrame extends JFrame {
    private final BoardPane board;
    private final PlayerInfoPanel infoPanel;
    private final PlayerTilesGrid playerTiles;

    /**
     * Constructor for the AmphipolisFrame class.
     */
    public AmphipolisFrame() {
        super("Amphipolis");
        setSize(800,800);
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        // Component initialization
        board = new BoardPane();
        infoPanel = new PlayerInfoPanel();
        playerTiles = new PlayerTilesGrid();

        // Place components correctly in the view
        board.setBounds(0,0,400,400);
        add(board,BorderLayout.CENTER);
        add(infoPanel,BorderLayout.EAST);
        add(playerTiles,BorderLayout.SOUTH);
    }

    /**
     * Accessor function which returns the BoardPane view.
     * @see BoardPane
     * @return The BoardPane view of the gui.
     */
    public BoardPane getBoard() {
        return board;
    }

    /**
     * Accessor function which returns the PlayerInfoPanel view.
     * @see PlayerInfoPanel
     * @return The PlayerInfoPanel view of the gui.
     */
    public PlayerInfoPanel getInfoPanel() {
        return infoPanel;
    }

    /**
     * Accessor function which returns the PlayerTilesGrid view.
     * @see PlayerTilesGrid
     * @return The PlayerTilesGrid view of the gui.
     */
    public PlayerTilesGrid getPlayerTiles() {return playerTiles;}
}
