package com.csd4020.model.tiles.findings;

import com.csd4020.model.tiles.FindingTile;

/**
 * A class representing a Mosaic tile.
 *
 * @author Stelios Papamichail csd4020
 */
public class Mosaic extends FindingTile {
    private static final int sameColorMosaicPoints = 4;
    private static final int differentColorMosaicPoints = 2;

    /**
     * Constructor for the MosaicTile class.
     * @param color The color of the tile
     * Precondition: color != null
     * Postcondition: A MosaicTile object will be created with the passed color
     */
    public Mosaic(String color) {
        super(0, color, "Mosaic");
    }

    /**
     * Accessor function which returns the points for a 4-piece same color mosaic.
     * @return The points for a 4-piece same color mosaic.
     */
    public static int getSameColorMosaicPoints() {
        return sameColorMosaicPoints;
    }

    /**
     * Accessor function which returns the points for a 4-piece different color mosaic.
     * @return The points for a 4-piece different color mosaic.
     */
    public static int getDifferentColorMosaicPoints() {
        return differentColorMosaicPoints;
    }
}
