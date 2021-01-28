package com.csd4020.model.Board.TileAreas;

import com.csd4020.model.Board.TileArea;
import com.csd4020.model.tiles.LandslideTile;

/**
 * A class representing an area of the board
 * where landslide tiles can be placed.
 * @author Stelios Papamichail csd4020
 */
public class LandslideTileArea extends TileArea<LandslideTile> {
    public LandslideTileArea() {super();}

    /**
     * Accessor function which returns the number of landslide tiles
     * in the area.
     */
    public int getLandslideCount() {return getAreaTiles().size();}
}
