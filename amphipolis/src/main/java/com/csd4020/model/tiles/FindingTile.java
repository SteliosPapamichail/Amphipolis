package com.csd4020.model.tiles;

/**
 * An abstract class representing a finding type of tile.
 *
 * @author Stelios Papamichail csd4020
 */
public abstract class FindingTile extends Tile {
    private String findingCategory;
    private int points;

    /**
     * "Constructor" for initializing the attributes of the class.
     * @param points The points of the finding
     * @param color The color of the finding
     * @param findingCategory The finding category
     * Precondition: Points > 0
     * Postcondition: The attributes of the finding tile will be initialized correctly
     */
    protected FindingTile(int points, String color, String findingCategory) {
        super(color,"Finding");
        this.points = points;
        this.findingCategory = findingCategory;
    }

    /**
     * Accessor function which returns the category of the finding.
     * @return The finding's category
     */
    public String getFindingCategory() {
        return findingCategory;
    }

    /**
     * Accessor function which returns the points of the finding.
     * @return The finding's points
     */
    public int getPoints() {return points;}
}
