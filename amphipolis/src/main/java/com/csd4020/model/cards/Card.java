package com.csd4020.model.cards;

import com.csd4020.model.Player;

/**
 * An abstract class representing a Card of the game.
 * A card can either be [Assistant,Archaeologist,Digger,Professor].
 *
 * @author Stelios Papamichail csd4020
 */
public abstract class Card {
    private final String name;
    private final Player owner;
    private final String effect;
    private boolean hasBeenPlayed;

    /**
     * "Constructor" for the Card class used to initialize its attributes.
     * @param name The card's name
     * @param owner The card's owner
     * Precondition: Name is any one of the following : [Assistant,Archaeologist,Digger,Professor] & owner is an instance of Player
     * Postcondition: The attributes of the class are initialized using the passed values.
     */
    protected Card(String name, String effect, Player owner) {
        this.name = name;
        this.owner = owner;
        this.effect = effect;
        hasBeenPlayed = false;
    }

    /**
     * Accessor function which returns the name of the card.
     * @return The card's name
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor function which returns the card's owner.
     * @return The owner of the card.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Accessor function which returns the card's effect.
     * @return The effect of the card
     */
    public String getEffect() {
        return effect;
    }

    /**
     * Observer function which returns whether the card has been played.
     * @return True if the card has been played, false otherwise.
     */
    public boolean hasBeenPlayed() {
        return hasBeenPlayed;
    }

    /**
     * Transformer function which updates the card's played status.
     * @param hasBeenPlayed The value to update the card's played status with.
     * Precondition: hasBeenPlayed is true or false.
     * Postcondition: The card's played status will be updated.
     */
    public void setHasBeenPlayed(boolean hasBeenPlayed) {
        this.hasBeenPlayed = hasBeenPlayed;
    }
}
