package com.csd4020.model.cards;

import com.csd4020.model.Player;

/**
 * A class representing a Professor card.
 *
 * @author Stelios Papamichail csd4020
 */
public class Professor extends Card {
    /**
     * Constructor for the Professor class.
     *
     * @param owner The card's owner
     * Precondition: owner is an instance of Player
     * Postcondition: A new Professor card object is created
     */
    public Professor(Player owner) {
        super("Professor", "ΟThe player takes 1 tile from each tile area except from the previously selected one" ,owner);
    }
}
