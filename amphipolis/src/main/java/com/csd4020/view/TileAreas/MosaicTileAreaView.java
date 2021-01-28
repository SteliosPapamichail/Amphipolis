package com.csd4020.view.TileAreas;

import com.csd4020.view.TileLabels.MosaicLabel;

/**
 * A UI class representing a TileArea that holds
 * mosaic tiles.
 * @author Stelios Papamichail csd4020
 */
public class MosaicTileAreaView extends TileArea {
    private final MosaicLabel[] labels = new MosaicLabel[3];

    /**
     * Constructor for the MosaicTileAreaView class.
     */
    public MosaicTileAreaView() {
        super(3);
        populateTileLabels();
        setTileLabels(labels);
        addTileLabelsToGrid();
    }


    @Override
    public void populateTileLabels() {
        labels[0] = new MosaicLabel("Red");
        labels[1] = new MosaicLabel("Green");
        labels[2] = new MosaicLabel("Yellow");
    }
}
