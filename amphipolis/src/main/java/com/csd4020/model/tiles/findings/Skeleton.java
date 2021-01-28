package com.csd4020.model.tiles.findings;

import com.csd4020.model.tiles.FindingTile;

/**
 * A class representing a SkeletonTile.
 *
 * @author Stelios Papamichail csd4020
 */
public class Skeleton extends FindingTile {
    private final String skeletonCategory;
    private final String skeletonPart;
    private static final int familyPoints = 6;
    private static final int completeSkeletonPoints = 1;

    /**
     * Constructor for the SkeletonTile class
     * @param skeletonCategory The category of the skeleton tile
     * @param skeletonPart  The part of the skeleton tile
     * Precondition: skeletonCategory is "big" or "small" & part is either "top" or "bottom"
     * Postcondition: A SkeletonTile object will be created with the passed attributes
     */
    public Skeleton(String skeletonCategory, String skeletonPart) {
        super(0, "","Skeleton");
        this.skeletonCategory = skeletonCategory;
        this.skeletonPart = skeletonPart;
    }

    /**
     * Accessor function which returns the points awarded for a family of skeletons
     * (2 adults & 1 child).
     * @return The points to award
     */
    public static int getFamilyPoints() {
        return familyPoints;
    }

    /**
     * Accessor function which returns the points awarded for a complete skeleton
     * which doesn't belong to a family.
     * @return The points to award
     */
    public static int getCompleteSkeletonPoints() {
        return completeSkeletonPoints;
    }

    /**
     * Accessor function which returns the skeleton tile's category.
     * @return The category of the skeleton tile.
     * Invariant: The category will either be "small" or "big"
     */
    public String getSkeletonCategory() {
        return skeletonCategory;
    }

    /**
     * Accessor function which returns the skeleton tile's part.
     * @return The category of the skeleton tile.
     * Invariant: The part will either be "top" or "bottom"
     */
    public String getSkeletonPart() {return skeletonPart;}
}
