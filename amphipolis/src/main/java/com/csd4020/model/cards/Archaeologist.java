package com.csd4020.model.cards;

import com.csd4020.model.Player;

/**
 *
 *
 * @author Stelios Papamichail csd4020
 */
public class Archaeologist extends Card {
    /**
     * Constructor for the Archaeologist class.
     *
     * @param owner The card's owner
     * Precondition: owner is an instance of Player
     * Postcondition: A new Archaeologist card object is created
     */
    public Archaeologist(Player owner) {
        super("Archaeologist", "The player takes up to 2 tiles from any tile area except from the previously selected one", owner);
    }
}
