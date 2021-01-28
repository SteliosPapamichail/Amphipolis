package com.csd4020.view.TileAreas;

import com.csd4020.view.TileLabels.StatueLabel;

/**
 * A UI class representing a TileArea that holds
 * statue tiles.
 * @author Stelios Papamichail csd4020
 */
public class StatueTileAreaView extends TileArea {
    private final StatueLabel[] labels = new StatueLabel[2];

    /**
     * Constructor for the StatueTileAreaView class.
     */
    public StatueTileAreaView() {
        super(2);
        populateTileLabels();
        setTileLabels(labels);
        addTileLabelsToGrid();
    }


    @Override
    public void populateTileLabels() {
        labels[0] = new StatueLabel("Sphinx");
        labels[1] = new StatueLabel("Caryatid");
    }
}
