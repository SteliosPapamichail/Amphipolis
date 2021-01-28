package com.csd4020.view.TileAreas;

import com.csd4020.view.TileLabels.SkeletonLabel;

/**
 * A UI class representing a TileArea that holds
 * skeleton tiles.
 * @author Stelios Papamichail csd4020
 */
public class SkeletonTileAreaView extends TileArea {
    private final SkeletonLabel[] labels = new SkeletonLabel[4];

    /**
     * Constructor for the SkeletonTileAreaView class.
     */
    public SkeletonTileAreaView() {
        super(4);
        populateTileLabels();
        setTileLabels(labels);
        addTileLabelsToGrid();
    }


    @Override
    public void populateTileLabels() {
        labels[0] = new SkeletonLabel("Big","Top");
        labels[1] = new SkeletonLabel("Big","Bottom");
        labels[2] = new SkeletonLabel("Small","Top");
        labels[3] = new SkeletonLabel("Small","Bottom");
    }
}
