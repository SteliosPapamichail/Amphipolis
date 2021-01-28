package com.csd4020.model.tiles.findings;

import com.csd4020.model.tiles.FindingTile;

/**
 * A class representing an amphora tile.
 */
public class Amphora extends FindingTile {
    private static final int threeDistinctColorsPoints = 1;
    private static final int fourDistinctColorsPoints = 2;
    private static final int fiveDistinctColorsPoints = 4;
    private static final int sixDistinctColorsPoints = 6;

    /**
     * Constructor for the amphora class
     * @param color The color of the amphora
     * Precondition: color != null && color is either ["BLUE","BROWN","RED","GREEN","YELLOW","PURPLE"]
     * Postcondition: A new amphora tile object will be created.
     */
    public Amphora(String color) {
        super(0,color,"Amphora");
    }

    /**
     * Accessor function which returns the points to award
     * if a player has [@numOfDistinctColors] distinct colors of amphora tiles in hand.
     * @param numOfDistinctColors The number of distinct colors of amphoras
     * @return The points to award
     * Precondition: numOfDistinctColors is either [3,4,5,6]
     * Postcondition: A valid number of points will be returned
     */
    public static int getAmphoraPoints(int numOfDistinctColors) {
        int points;
        switch (numOfDistinctColors) {
            case 3:
                points = threeDistinctColorsPoints;
                break;
            case 4:
                points = fourDistinctColorsPoints;
                break;
            case 5:
                points = fiveDistinctColorsPoints;
                break;
            case 6:
                points = sixDistinctColorsPoints;
                break;
            default:
                points = 0;
        }
        return points;
    }
}
