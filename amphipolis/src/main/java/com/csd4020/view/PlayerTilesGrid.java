package com.csd4020.view;

import com.csd4020.view.TileLabels.*;

import javax.swing.*;
import java.awt.*;

/**
 * A UI class representing a player's tile collection as a grid.
 *
 * @author Stelios Papamichail csd4020
 */
public class PlayerTilesGrid extends JPanel {
    private final TileLabel[] playerTiles;

    /**
     * Constructor for the PlayerTilesGrid class.
     * Invariant: The grid holding the tiles always has 1 row and 15 columns.
     */
    public PlayerTilesGrid() {
        super();
        GridLayout gridLayout = new GridLayout(1, 15); // one column for each tile
        setLayout(gridLayout);
        setSize(800,300);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        playerTiles = new TileLabel[15]; // the player can hold tiles of 15 different types
        initializePlayerTileLabels();
        addTileLabelsToGrid();
    }

    /**
     * Adds the necessary Tile labels to the grid of the JPanel.
     */
    private void addTileLabelsToGrid() {
        for(TileLabel tl : playerTiles) add(tl);
    }

    /**
     * Fills the player's tiles grid with one TileLabel for each
     * type of tile. Important: Each TileLabel's counter is initialized with 0.
     */
    private void initializePlayerTileLabels() {
        playerTiles[0] = new AmphoraLabel("Blue");
        playerTiles[1] = new AmphoraLabel("Purple");
        playerTiles[2] = new AmphoraLabel("Red");
        playerTiles[3] = new AmphoraLabel("Yellow");
        playerTiles[4] = new AmphoraLabel("Green");
        playerTiles[5] = new AmphoraLabel("Brown");

        playerTiles[6] = new MosaicLabel("Red");
        playerTiles[7] = new MosaicLabel("Green");
        playerTiles[8] = new MosaicLabel("Yellow");

        playerTiles[9] = new SkeletonLabel("Big","Top");
        playerTiles[10] = new SkeletonLabel("Big","Bottom");
        playerTiles[11] = new SkeletonLabel("Small","Top");
        playerTiles[12] = new SkeletonLabel("Small","Bottom");

        playerTiles[13] = new StatueLabel("Sphinx");
        playerTiles[14] = new StatueLabel("Caryatid");
    }

    /**
     * Accessor function which returns the player's tileLabels.
     * @return The player's TileLabels
     */
    public TileLabel[] getPlayerTiles() {return playerTiles;}
}
