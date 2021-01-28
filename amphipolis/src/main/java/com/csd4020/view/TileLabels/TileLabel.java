package com.csd4020.view.TileLabels;

import javax.swing.*;
import java.awt.*;

/**
 * An abstract UI class representing a Tile of the game
 * which has an image and a counter keeping
 * track of how many instances of that tile are present on the board.
 *
 * @author Stelios Papamichail csd4020
 */
public abstract class TileLabel extends JLabel {

    /**
     * "Constructor" for the TileLabel class.
     * Postcondition: A TileLabel object will be created with the passed values
     */
    public TileLabel() {
        super("x0");
    }

    /**
     * Sets the Jlabel's image icon to the passed icon
     * @param icon The icon to use as the JLabel's image
     */
    public void setLabelIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(40,40,Image.SCALE_SMOOTH); // scale down the tile image
        setIcon(new ImageIcon(newImg));
    }

    /**
     * Accessor function which returns the count of the
     * tile label.
     * @return The tile label's count
     */
    public int getCount() {
        return Integer.parseInt(this.getText().substring(1));
    }

    /**
     * Transformer function which updates the label's
     * text by increasing it by one.
     */
    public void increaseCnt() {
        int newCnt = getCount()+1;
        this.setText("x"+ newCnt);
    }

    /**
     * Transformer function which updates the label's
     * text by decreasing it by one.
     */
    public void decreaseCnt() {
        int newCnt = getCount()-1;
        if(newCnt < 0) return;
        this.setText("x"+ newCnt);
    }
}
