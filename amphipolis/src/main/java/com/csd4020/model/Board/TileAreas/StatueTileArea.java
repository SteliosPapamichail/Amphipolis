package com.csd4020.model.Board.TileAreas;

import com.csd4020.model.Board.TileArea;
import com.csd4020.model.tiles.findings.Statue;

import java.util.Optional;

/**
 * A class representing an area of the board
 * where statue tiles can be placed.
 * @author Stelios Papamichail csd4020
 */
public class StatueTileArea extends TileArea<Statue> {
    public StatueTileArea() {super();}

    /**
     * Retrieves a statue tile matching the given category and removes it from the area.
     * @param category The category of the statue tile to retrieve and remove
     * @return The retrieved statue tile
     * Precondition: category != null && category is either ["Sphinx","Caryatid"] && such a tile exists
     * Postcondition: A matching statue tile will be returned or NULL if none exists in the area
     */
    public Statue takeStatueTile(String category) {
        Optional<Statue> result = getAreaTiles().stream().filter(statue -> statue.getStatueCategory().equals(category)).findFirst();
        if(result.isPresent()) {
            getAreaTiles().remove(result.get()); // remove the tile from the area
            return result.get();
        }
        else return null;
    }

    /**
     * Counts the statues with the given category in the area
     * @param category The category of the statue
     * Precondition: category != null && category is either ["Sphinx","Caryatid"]
     */
    public int getStatueCountOfCategory(String category) {
        return (int) getAreaTiles().stream().filter(statue -> statue.getStatueCategory().equals(category)).count();
    }
}
