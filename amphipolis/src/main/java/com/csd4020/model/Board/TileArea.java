package com.csd4020.model.Board;

import com.csd4020.model.tiles.Tile;

import java.util.ArrayList;

/**
 * A class representing an area of the board
 * where tiles can be placed.
 * @param <T> The subclass of Tile representing the type of tiles the area will hold.
 * @author Stelios Papamichail csd4020
 */
abstract public class TileArea<T extends Tile> {
    // The tiles the area will hold
    private final ArrayList<T> areaTiles = new ArrayList<>();

    /**
     * Constructor for the TileArea class
     */
    public TileArea() { }

    /**
     * Adds a tile to the tile area.
     * @param tile The tile to add
     */
    public void addTile(T tile) {
        areaTiles.add(tile);
    }

    /**
     * Accessor function which returns the tiles that have been placed on the area.
     * @return The area's tiles
     */
    public ArrayList<T> getAreaTiles() {return areaTiles;}
}
