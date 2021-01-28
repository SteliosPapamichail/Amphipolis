package com.csd4020.view.TileAreas;

import com.csd4020.view.TileLabels.LandslideLabel;

/**
 * A UI class representing a TileArea that holds
 * landslide tiles.
 * @author Stelios Papamichail csd4020
 */
public class LandslideTileAreaView  extends TileArea {
    private final LandslideLabel[] labels = new LandslideLabel[1];

    /**
     * Constructor for the LandslideTileAreaView class.
     */
    public LandslideTileAreaView() {
        super(1);
        populateTileLabels();
        setTileLabels(labels);
        addTileLabelsToGrid();
    }


    @Override
    public void populateTileLabels() {
        labels[0] = new LandslideLabel();
    }
}
