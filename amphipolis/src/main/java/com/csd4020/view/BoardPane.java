package com.csd4020.view;

import com.csd4020.view.TileAreas.*;

import javax.swing.*;
import java.awt.*;

/**
 *
 * A UI class representing the board of the game.
 *
 * @author Stelios Papamichail csd4020
 */
public class BoardPane extends JLayeredPane {
    // The 5 Tile areas
    private final MosaicTileAreaView mosaicArea = new MosaicTileAreaView();
    private final AmphoraTileAreaView amphoraArea = new AmphoraTileAreaView();
    private final StatueTileAreaView statueArea = new StatueTileAreaView();
    private final SkeletonTileAreaView skeletonArea = new SkeletonTileAreaView();
    private final LandslideTileAreaView landslideArea = new LandslideTileAreaView();

    /**
     * Constructor for the BoardPane class.
     */
    public BoardPane() {
        super();
        setPreferredSize(new Dimension(800,800));
        setBoardBg();
        addTileAreasToBoard();
    }

    /**
     * Performs all necessary actions to create the background of the board
     * and add it on the layered pane.
     */
    private void setBoardBg() {
        BackgroundPanel bg = new BackgroundPanel();
        add(bg, Integer.valueOf(0));
        bg.setBounds(0,0, 1000, 1000);
    }

    /**
     * Adds each TileArea on the layered pane in the proper position.
     */
    public void addTileAreasToBoard() {
        add(mosaicArea,Integer.valueOf(1));
        mosaicArea.setBounds(50,70,200,100);

        add(amphoraArea,Integer.valueOf(1));
        amphoraArea.setBounds(50,600,200,100);

        add(skeletonArea,Integer.valueOf(1));
        skeletonArea.setBounds(600,600,200,100);

        add(statueArea,Integer.valueOf(1));
        statueArea.setBounds(650,70,200,100);

        add(landslideArea,Integer.valueOf(1));
        landslideArea.setBounds(385,425,200,100);
    }

    /**
     * Accessor function which returns the Mosaic tile area.
     * @return the Mosaic tile area.
     */
    public MosaicTileAreaView getMosaicArea() {
        return mosaicArea;
    }

    /**
     * Accessor function which returns the Amphora tile area.
     * @return the Amphora tile area.
     */
    public AmphoraTileAreaView getAmphoraArea() {
        return amphoraArea;
    }

    /**
     * Accessor function which returns the Statue tile area.
     * @return the Statue tile area.
     */
    public StatueTileAreaView getStatueArea() {
        return statueArea;
    }

    /**
     * Accessor function which returns the Skeleton tile area.
     * @return the skeleton tile area.
     */
    public SkeletonTileAreaView getSkeletonArea() {
        return skeletonArea;
    }

    /**
     * Accessor function which returns the Landslide tile area.
     * @return the Landslide tile area.
     */
    public LandslideTileAreaView getLandslideArea() {
        return landslideArea;
    }
}
