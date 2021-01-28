package com.csd4020.model;

import com.csd4020.exceptions.Exceptions;
import com.csd4020.model.cards.Card;
import com.csd4020.model.tiles.Tile;
import com.csd4020.model.tiles.findings.Amphora;
import com.csd4020.model.tiles.findings.Mosaic;
import com.csd4020.model.tiles.findings.Skeleton;
import com.csd4020.model.tiles.findings.Statue;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class representing a player.
 *
 * @author Stelios Papamichail csd4020
 */
public class Player {
    private final ArrayList<Tile> tiles = new ArrayList<>(); // player tile collection
    private Card[] cards; // card collection
    private final String name;
    private final Color color; // player color
    private int finalPoints; // will hold the total points of the player when the game has ended

    /**
     * Constructor of the Player class
     * @param name The player's name
     * @param color The player's color
     * Precondition: name.isNotEmpty() && color != null
     * Postcondition: A new player object is created with the given attributes.
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Transformer function which sets the player's cards.
     * @param cards An array containing the player's cards
     * Precondition: cards.size == 4 && the cards array has one copy of each card
     * Postcondition: The cards are given to the player
     */
    public void setCards(Card[] cards) {this.cards = cards;}

    /**
     * Transformer function which adds a tile to the player's list of tiles
     * @param tile The tile to add
     */
    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    /**
     * Transformer function which removes the given tile from the player's
     * tile collection.
     * @param tile The tile to remove
     * Precondition: A tile of the given type exists
     * Postcondition: The tile will be removed from the collection
     * @throws Exceptions.NoSuchTileFoundException When an invalid tile class is passed
     */
    public void removeTile(Tile tile) throws Exceptions.NoSuchTileFoundException {
        if(tiles.contains(tile)) tiles.remove(tile);
        else throw new Exceptions.NoSuchTileFoundException("The player's tile collection doesn't contain a tile of type " + tile.getClass().getName());
    }

    /**
     * Accessor function which returns the tiles of the player.
     * @return The player's tiles
     */
    public ArrayList<Tile> getTiles() {return tiles;}

    /**
     * Accessor function which returns the name of the player.
     * @return The player's name
     */
    public String getName() {return name;}

    /**
     * Calculates the player's points
     * on the tiles gathered (excluding statue pts).
     * @return The total points earned (excluding statue pts. Awarded later)
     */
    public int calculatePoints() {
        int mosaicPoints = getMosaicPoints();
        int amphoraPoints = getAmphoraPoints();
        int skeletonPoints = getSkeletonPoints();
        return mosaicPoints+amphoraPoints+skeletonPoints;
    }

    /**
     * Calculates the points gathered by the player
     * based on his Skeleton Tiles in hand.
     * @return The points gathered
     */
    private int getSkeletonPoints() {
        int smallBottomParts = countSkeletonOfSizeAndPart("Small","Bottom");
        int smallTopParts = countSkeletonOfSizeAndPart("Small","Top");
        int bigBottomParts = countSkeletonOfSizeAndPart("Big","Bottom");
        int bigTopParts = countSkeletonOfSizeAndPart("Big","Top");

        if((smallBottomParts < 1 || smallTopParts < 1) && (bigBottomParts < 1 || bigTopParts < 1)) return 0; // we can't construct any skeletons

        int points = 0;
        // determine how many full skeletons of each type (adult,child) can be made
        int numOfChildrenSkeletons = Math.min(smallTopParts,smallBottomParts);
        int numOfAdultSkeletons = Math.min(bigBottomParts,bigTopParts);

        int numOfSkeletonPairs = numOfAdultSkeletons/2; // parents
        int numOfFamilies = Math.min(numOfSkeletonPairs,numOfChildrenSkeletons);

        for(int i=0; i < numOfFamilies; i++) {
            points += Skeleton.getFamilyPoints();
            numOfAdultSkeletons -= 2; // each family consists of two adult skeletons
            numOfChildrenSkeletons--; // and one child skeleton
        }

        int numOfSkeletonsLeft = numOfAdultSkeletons + numOfChildrenSkeletons;
        for(int i=0; i < numOfSkeletonsLeft; i++) points += Skeleton.getCompleteSkeletonPoints();

        return points;
    }

    /**
     * Calculates the points gathered by the player
     * based on his Amphora Tiles in hand.
     * @return The points gathered
     */
    private int getAmphoraPoints() {
        // group amphora tiles based on their color
        Map<String, List<Tile>> map = tiles.stream().filter(tile -> tile instanceof Amphora).collect(Collectors.groupingBy(Tile::getColor));
        if(map.size() == 0) return 0;

        return Amphora.getAmphoraPoints(map.size()); // map.size() returns the number of K,V pairs (thus the number of different colors of amphoras)
    }

    /**
     * Calculates the points gathered by the player
     * based on his Mosaic Tiles in hand.
     * @return The points gathered
     */
    private int getMosaicPoints() {
        ArrayList<Tile> mosaics = (ArrayList<Tile>) tiles.stream().filter(x-> x instanceof Mosaic).collect(Collectors.toList());
        if(mosaics.size() < 4) { // doesn't have any full mosaics (2x2)
            return 0;
        }

        // Calculate the points to return based on the formula of the exercise
        int points = 0;
        int numOfRedMosaicTiles = countMosaicsOfColor("Red");
        int numOfGreenMosaicTiles = countMosaicsOfColor("Green");
        int numOfYellowMosaicTiles = countMosaicsOfColor("Yellow");

        // Determine how many 2x2 Mosaics can be made from tiles of the same color
        if(numOfRedMosaicTiles/4 > 0) {
            for(int i=0; i < numOfRedMosaicTiles/4; i++) {
                points += Mosaic.getSameColorMosaicPoints();
                numOfRedMosaicTiles -= 4;
            }
        }

        if(numOfGreenMosaicTiles/4 > 0) {
            for(int i=0; i < numOfGreenMosaicTiles/4; i++) {
                points += Mosaic.getSameColorMosaicPoints();
                numOfGreenMosaicTiles -= 4;
            }
        }

        if(numOfYellowMosaicTiles/4 > 0) {
            for(int i=0; i < numOfYellowMosaicTiles/4; i++) {
                points += Mosaic.getSameColorMosaicPoints();
                numOfYellowMosaicTiles -= 4;
            }
        }

        // Determine how many (if any) 2x2 different color mosaics can be made with the rest of the tiles
        int mosaicTilesLeft = numOfRedMosaicTiles + numOfGreenMosaicTiles + numOfYellowMosaicTiles;
        int diffColorMosaics = mosaicTilesLeft/4; // holds the num of different color mosaics possible
        for(int i=0; i < diffColorMosaics; i++) points += Mosaic.getDifferentColorMosaicPoints();

        return points;
    }

    /**
     * Accessor function which returns the player's color.
     * @return The color of the player
     */
    public Color getColor() {
        return color;
    }

    /**
     * Accessor function which returns the cards of the player.
     * @return The player's cards.
     */
    public Card[] getCards() {return cards;}

    /**
     * Counts how many amphoras of the given color
     * the player has in his hand.
     * @param color The color of the amphoras to count
     * @return The count of the amphoras that match the given color
     * Precondition: Color != null && is a valid amphora color
     * Postcondition: The count will be returned
     * Invariant: The count will always be >= 0.
     */
    public int countAmphorasOfColor(String color) {
        int count = 0;
        for(Tile t : tiles) {
            if(t instanceof Amphora && t.getColor().equals(color)) count++;
        }
        return  count;
    }

    /**
     * Counts how many Mosaics of the given color
     * the player has in his hand.
     * @param color The color of the Mosaics to count
     * @return The count of the Mosaics that match the given color
     * Precondition: Color != null && is a valid mosaic color
     * Postcondition: The count will be returned
     * Invariant: The count will always be >= 0.
     */
    public int countMosaicsOfColor(String color) {
        int count = 0;
        for(Tile t : tiles) {
            if(t instanceof Mosaic && t.getColor().equals(color)) count++;
        }
        return  count;
    }

    /**
     * Counts how many Skeletons of the given size and
     * part the player has in his hand.
     * @param size The size of the skeleton to count
     * @param part The part of the skeleton to count
     * @return The count of the skeletons that match the given attributes
     * Precondition: size is either ["small","big"] & part is either ["top","bottom"]
     * Postcondition: The count will be returned
     * Invariant: The count will always be >= 0.
     */
    public int countSkeletonOfSizeAndPart(String size, String part) {
        int count = 0;
        for(Tile t : tiles) {
            if(t instanceof Skeleton) {
                Skeleton skeleton = (Skeleton) t;
                if(skeleton.getSkeletonCategory().equals(size) && skeleton.getSkeletonPart().equals(part)) count++;
            }
        }
        return  count;
    }

    /**
     * Counts how many Statues of the given category
     * the player has in his hand.
     * @param category The category of the statues to count
     * @return The count of the statues that match the given category
     * Precondition: category is either ["Sphinx","Caryatid"]
     * Postcondition: The count will be returned
     * Invariant: The count will always be >= 0.
     */
    public int countStatueOfCategory(String category) {
        int count = 0;
        for(Tile t : tiles) {
            if(t instanceof Statue)  {
                Statue statue = (Statue) t;
                if(statue.getStatueCategory().equals(category)) count ++;
            }
        }
        return  count;
    }

    /**
     * Accessor function which returns the final points the player has gathered
     * @return The total number of points gathered
     */
    public int getFinalPoints() {
        return finalPoints;
    }

    /**
     * Sets the total points the player has gathered to the given
     * value
     * @param finalPoints The points gathered
     */
    public void setFinalPoints(int finalPoints) {
        this.finalPoints = finalPoints;
    }
}
