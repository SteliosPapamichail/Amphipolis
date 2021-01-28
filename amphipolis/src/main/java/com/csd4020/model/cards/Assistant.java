package com.csd4020.model.cards;

import com.csd4020.model.Player;

/**
 * A class representing an Assistant Card
 *
 * @author Stelios Papamichail csd4020
 */
public class Assistant extends Card {

    /**
     * Constructor for the Assistant class.
     *
     * @param owner The card's owner
     * Precondition: owner is an instance of Player
     * Postcondition: A new Assistant card object is created
     */
    public Assistant(Player owner) {
        super("Assistant","The player takes 1 tile from any tile area", owner);
    }
}
