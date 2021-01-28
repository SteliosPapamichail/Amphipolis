package com.csd4020.view.TileAreas;

import com.csd4020.view.TileLabels.TileLabel;

import javax.swing.*;
import java.awt.*;

/**
 * An abstract UI class representing a TileArea as a JPanel
 * with grid which holds an array of Tile Labels.
 *
 * @author Stelios Papamichail csd4020
 */
public abstract class TileArea extends JPanel {
    private TileLabel[] tileLabels;

    /**
     * Constructor for the TileArea class.
     * @param numOfTilesToDisplay The number of tile labels the grid
     *                            will hold.
     * Precondition: numOfTilesToDisplay > 0
     * Postcondition: A TileArea JPanel will be created with a grid with the given number of columns
     * Invariant: The grid always has 1 row.
     */
    public TileArea(int numOfTilesToDisplay) {
        super();
        GridLayout layout = new GridLayout(2, numOfTilesToDisplay);
        setOpaque(false);
        setLayout(layout);
        setPreferredSize(new Dimension(150,100));
        tileLabels = new TileLabel[numOfTilesToDisplay];
    }

    /**
     * Populates the tile label array with the appropriate type of tile labels
     * based on the class implementing this function.
     */
    public abstract void populateTileLabels();

    /**
     * Adds the areas tile labels to the grid
     */
    public void addTileLabelsToGrid() {
        for(TileLabel tl : tileLabels) add(tl);
    }

    /**
     * Accessor function which returns the tile labels of the grid area.
     * @return The tile labels on the grid area
     */
    public TileLabel[] getTileLabels() {return tileLabels;}

    /**
     * Transformer function which sets the tile labels of the grid area.
     * @param labels The tile labels to set for the area
     */
    public void setTileLabels(TileLabel[] labels) {this.tileLabels = labels;}
}
