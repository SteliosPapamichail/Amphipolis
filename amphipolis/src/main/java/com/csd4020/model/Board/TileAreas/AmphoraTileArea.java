package com.csd4020.model.Board.TileAreas;

import com.csd4020.model.Board.TileArea;
import com.csd4020.model.tiles.findings.Amphora;

import java.util.Optional;

/**
 * A class representing an area of the board
 * where amphora tiles can be placed.
 * @author Stelios Papamichail csd4020
 */
public class AmphoraTileArea extends TileArea<Amphora> {
    public AmphoraTileArea() {
        super();
    }

    /**
     * Retrieves an amphora tile matching the given color and removes it from the area.
     * @param color The color of the amphora tile to retrieve and remove
     * @return The retrieved amphora tile
     * Precondition: color != null , color is either ["BLUE","RED","YELLOW","BROWN","PURPLE","GREEN"] and such a tile exists
     * Postcondition: A matching amphora tile will be returned or NULL if none exists in the area
     */
    public Amphora takeAmphoraTile(String color) {
        Optional<Amphora> result = getAreaTiles().stream().filter(amphora -> amphora.getColor().equals(color)).findFirst();
        if(result.isPresent()) {
            getAreaTiles().remove(result.get()); // remove the tile from the area
            return result.get();
        }
        else return null;
    }

    /**
     * Counts the number of amphoras of the given color in the tile area.
     * @param color The color of the amphoras to count
     * @return The count of amphoras with the given color
     * Precondition: color != null , color is either ["BLUE","RED","YELLOW","BROWN","PURPLE","GREEN"]
     * Postcondition: The count is returned
     * Invariant: The returned value will always be >= 0
     */
    public int getAmphoraCountOfColor(String color) {
        return (int) getAreaTiles().stream().filter(amphora -> amphora.getColor().equals(color)).count();
    }
}
