package com.csd4020.model.Board.TileAreas;

import com.csd4020.model.Board.TileArea;
import com.csd4020.model.tiles.findings.Skeleton;

import java.util.Optional;

/**
 * A class representing an area of the board
 * where skeleton tiles can be placed.
 * @author Stelios Papamichail csd4020
 */
public class SkeletonTileArea extends TileArea<Skeleton> {
    public SkeletonTileArea() {super();}

    /**
     * Retrieves a Skeleton tile matching the given size and part and removes it from the area.
     * @param category The category of the skeleton tile to look for
     * @param part The part of the skeleton tile to look for
     * @return The retrieved skeleton tile
     * Precondition: category must be either ["small","big"] && part must be either ["top","bottom"] and such a tile exists
     * Postcondition: A skeleton mosaic tile will be returned or NULL if none exists in the area
     */
    public Skeleton takeSkeletonTile(String category, String part) {
        Optional<Skeleton> result = getAreaTiles().stream().filter(skeleton -> skeleton.getSkeletonPart().equals(part) &&
                skeleton.getSkeletonCategory().equals(category)).findFirst();
        if(result.isPresent()) {
            getAreaTiles().remove(result.get()); // remove the tile from the area
            return result.get();
        }
        else return null;
    }

    /**
     * Counts the skeletons that match the given category and part
     * @param category The category of the skeleton tile
     * @param part The part of the skeleton tile
     * Precondition: category must be either ["Small","Big"] && part must be either ["Top","Bottom"]
     * Postcondition: The count will be returned
     * Invariant: the count will always be >= 0
     */
    public int getSkeletonCountOfCategoryAndPart(String category, String part) {
        return (int) getAreaTiles().stream().filter(skeleton -> skeleton.getSkeletonCategory().equals(category) && skeleton.getSkeletonPart().equals(part)).count();
    }
}
