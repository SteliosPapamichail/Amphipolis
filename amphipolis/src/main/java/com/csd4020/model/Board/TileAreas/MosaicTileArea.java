package com.csd4020.model.Board.TileAreas;

import com.csd4020.model.Board.TileArea;
import com.csd4020.model.tiles.findings.Mosaic;

import java.util.Optional;

/**
 * A class representing an area of the board
 * where mosaic tiles can be placed.
 * @author Stelios Papamichail csd4020
 */
public class MosaicTileArea  extends TileArea<Mosaic> {
    public MosaicTileArea() {super();}

    /**
     * Retrieves a mosaic tile matching the given color and removes it from the area.
     * @param color The color of the mosaic tile to retrieve and remove
     * @return The retrieved mosaic tile
     * Precondition: color != null , color is either ["RED","YELLOW","GREEN"] and such a tile exists
     * Postcondition: A matching mosaic tile will be returned or NULL if none exists in the area
     */
    public Mosaic takeMosaicTile(String color) {
        Optional<Mosaic> result = getAreaTiles().stream().filter(mosaic -> mosaic.getColor().equals(color)).findFirst();
        if(result.isPresent()) {
            getAreaTiles().remove(result.get()); // remove the tile from the area
            return result.get();
        }
        else return null;
    }

    /**
     * Counts the number of mosaics of the given color in the tile area.
     * @param color The color of the mosaic to count
     * @return The count of mosaics with the given color
     * Precondition: color != null , color is either ["RED","YELLOW","GREEN"]
     * Postcondition: The count is returned
     * Invariant: The returned value will always be >= 0
     */
    public int getMosaicCountOfColor(String color) {
        return (int) getAreaTiles().stream().filter(mosaic -> mosaic.getColor().equals(color)).count();
    }
}
