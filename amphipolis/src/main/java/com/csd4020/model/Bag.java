package com.csd4020.model;

import com.csd4020.model.tiles.LandslideTile;
import com.csd4020.model.tiles.Tile;
import com.csd4020.model.tiles.findings.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * A Singleton class representing a Bag of tiles.
 *
 * @author Stelios Papamichail csd4020
 */
public class Bag {
    // 27xMosaicTiles, 24xStatueTiles, 24xLandSlideTiles, 30xSkeletonTiles and 30xAmphoraTiles.
    private final ArrayList<Tile> tiles = new ArrayList<>(135);
    // static variable of type Bag used to make this a Singleton class
    private static Bag bag = null;

    /**
     * Private constructor of the Bag class to prevent multiple instances
     */
    private Bag() {}

    /**
     * Accessor function which returns the only instance
     * of the Bag class.
     * @return The only instance of the Bag class
     */
    public static Bag getInstance() {
        if (bag == null) bag = new Bag();
        return bag;
    }

    /**
     * Initializes the bag with the Tiles needed
     * for the game and shuffles them.
     */
    public void initialize() {
        String[] mosaicColors = {"Red", "Green", "Yellow"};
        // create and add 9 red, 9 green & 9 yellow mosaic tiles
        for(String color:mosaicColors) {
            for(int i =0; i < 9; i++) {
                Mosaic mosaic = new Mosaic(color);
                tiles.add(mosaic);
            }
        }

        // create 12 caryatids & 12 sphinx statues
        String[] statueTypes = {"Caryatid","Sphinx"};
        for(String type:statueTypes) {
            for(int i=0; i < 12; i++) {
                if(type.equals("Caryatid")) tiles.add(new Caryatid());
                else tiles.add(new Sphinx());
            }
        }

        // create 24 landslide tiles
        for(int i=0; i < 24; i++) tiles.add(new LandslideTile());

        // create skeleton tiles (30 total: 10 top big, 10 bottom big, 5 top small, 5 bottom small)
        for(int i=0; i < 10; i++) tiles.add(new Skeleton("Big","Top"));

        for(int i=0; i < 10; i++) tiles.add(new Skeleton("Big","Bottom"));

        for(int i=0; i < 5; i++) tiles.add(new Skeleton("Small","Top"));

        for(int i=0; i < 5; i++) tiles.add(new Skeleton("Small","Bottom"));

        // create 30 amphora tiles, 5 of each color
        String[] amphoraColors = {"Blue","Brown","Red","Green","Yellow","Purple"};
        for(String color:amphoraColors) {
            for(int i=0; i < 5; i++) tiles.add(new Amphora(color));
        }

        // shuffle the tiles
        Collections.shuffle(tiles); // call by reference so there's no list being returned
    }

    /**
     * Draw a random Tile from the bag and returns it.
     * @return The drawn tile.
     */
    public Tile drawTile() {
        Tile drawnTile = tiles.get(0); // always draw the top one (since the list was shuffled that should be ok)
        tiles.remove(drawnTile);
        return drawnTile;
    }

    /**
     * Function that's used to draw tiles of certain types
     * from the bag. [Used at game initialization]
     * @param tileClass The type of tile to look for
     * @return The tile of the given type or null if none found
     */
    public Tile drawTileOfType(Class<? extends Tile> tileClass) {
        Optional<Tile> tile =  tiles.stream().filter(tileClass::isInstance).findFirst();
        tiles.remove(tile.orElse(null));
        return tile.orElse(null);
    }
}
