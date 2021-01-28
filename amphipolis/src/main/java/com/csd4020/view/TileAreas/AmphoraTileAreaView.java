package com.csd4020.view.TileAreas;

import com.csd4020.view.TileLabels.AmphoraLabel;

/**
 * A UI class representing a TileArea that holds
 * amphora tiles.
 * @author Stelios Papamichail csd4020
 */
public class AmphoraTileAreaView extends TileArea {
    private final AmphoraLabel[] labels = new AmphoraLabel[6];

    /**
     * Constructor for the AmphoraTileAreaView class.
     */
    public AmphoraTileAreaView() {
        super(6);
        populateTileLabels();
        setTileLabels(labels);
        addTileLabelsToGrid();
    }


    @Override
    public void populateTileLabels() {
        labels[0] = new AmphoraLabel("Blue");
        labels[1] = new AmphoraLabel("Purple");
        labels[2] = new AmphoraLabel("Red");
        labels[3] = new AmphoraLabel("Yellow");
        labels[4] = new AmphoraLabel("Green");
        labels[5] = new AmphoraLabel("Brown");
    }
}
