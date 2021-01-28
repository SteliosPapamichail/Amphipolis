package com.csd4020.model.tiles.findings;

import com.csd4020.model.tiles.FindingTile;

/**
 * An abstract class representing a Statue tile. A
 * statue tile can either be a Sphinx or Caryatid tile.
 *
 * @author Stelios Papamichail csd4020
 */
public abstract class Statue extends FindingTile {
    private final String statueCategory;
    private static final int playerWithMostTilesPoints = 6; // the points given to the player with the most tiles
    private static final int nonMinMaxPlayerTilesPoints = 3; // the points given to the players that don't have the max or min number of tiles in the game.

    /**
     * "Constructor" for the StatueTile class used to initialize its attributes.
     * @param statueCategory The statue's category
     * Precondition: The statue's category must either be "Sphinx" or "Caryatid"
     * Postcondition: The attributes of the class will be initialized.
     */
    protected Statue(String statueCategory) {
        super(0, "", "Statue");
        this.statueCategory = statueCategory;
    }

    /**
     * Accessor function which returns the statue's category.
     * @return The statue's category
     */
    public String getStatueCategory() {
        return statueCategory;
    }

    /**
     * Accessor function which returns the points that should be
     * given to the player with the most tiles.
     * @return The points to be given to the player with the most tiles.
     */
    public static int getPlayerWithMostTilesPoints() {
        return playerWithMostTilesPoints;
    }

    /**
     * Accessor function which returns the points that should be
     * given to the players that have neither the most tiles nor the least.
     * @return The points to be given to the players that have neither the most tiles nor the least.
     */
    public static int getNonMinMaxPlayerTilesPoints() {
        return nonMinMaxPlayerTilesPoints;
    }
}
