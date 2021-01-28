package com.csd4020;

import com.csd4020.controller.Controller;
import com.csd4020.model.Board.Board;
import com.csd4020.view.AmphipolisFrame;

/**
 * The Main class is the starting point of the game.
 *
 * @author Stelios Papamichail csd4020
 */
public class Main {
    public static void main(String[] args) {
        new Controller(new AmphipolisFrame(), Board.getInstance());
    }
}
