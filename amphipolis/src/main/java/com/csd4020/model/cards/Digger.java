package com.csd4020.model.cards;

import com.csd4020.model.Player;

/**
 * A class representing a Digger card.
 *
 * @author Stelios Papamichail csd4020
 */
public class Digger extends Card {
    /**
     * Constructor for the Digger class.
     *
     * @param owner The card's owner
     * Precondition: owner is an instance of Player
     * Postcondition: A new Digger card object is created
     */
    public Digger(Player owner) {
        super("Digger", "The player takes up to 2 tiles from the previously selected tile area" ,owner);
    }
}
