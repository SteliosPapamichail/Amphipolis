package com.csd4020.model.tiles;

/**
 * An abstract class representing a Tile.
 * @author Stelios Papamichail csd4020
 */
public abstract class Tile {
    private final String color;
    private final String category;

    /**
     * "Constructor" function, used to initialize the data
     * for the Tile class.
     * @param color The color of the tile
     * @param category The tile's category
     * Precondition: A color & a category are provided. The category must either be "Landslide" or "Finding".
     * Postcondition: The data of the class will be successfully initialized.
     */
    protected Tile(String color, String category) {
        this.color = color;
        this.category = category;
    }

    /**
     * Accessor function which returns the tile's color.
     * @return The color of the tile.
     */
    public String getColor() {
        return color;
    }

    /**
     * Accessor function which returns the tile's category.
     * @return The category of the tile.
     */
    public String getCategory() {
        return category;
    }
}
