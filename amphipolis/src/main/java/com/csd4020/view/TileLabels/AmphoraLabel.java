package com.csd4020.view.TileLabels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 *
 * A UI class representing an AmphoraLabel.
 *
 * @author Stelios Papamichail csd4020
 */
public class AmphoraLabel extends TileLabel {
    private final String color;

    /**
     * Constructor for the AmphoraLabel class.
     *
     * @param color The color of the amphora
     * Precondition: color is either of these values: [red,yellow,purple,green,blue,brown]
     * Postcondition: A new AmphoraLabel is created with the appropriate image and color
     */
    public AmphoraLabel(String color) {
        super();
        this.color = color;
        try {
            setLabelIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/amphora_"+color.toLowerCase()+".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accessor function which returns the color of the amphora label.
     * @return The color of the amphora label
     */
    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof AmphoraLabel)) return false;
        AmphoraLabel amphoraLabel = (AmphoraLabel) obj;
        return getColor().equals(amphoraLabel.getColor());
    }
}
