package com.csd4020.view.TileLabels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 *
 * A UI class representing a MosaicLabel.
 *
 * @author Stelios Papamichail csd4020
 */
public class MosaicLabel extends TileLabel {
    private final String color;

    /**
     * Constructor for the MosaicLabel class.
     *
     * @param color The color of the mosaic
     * Precondition: color is either of these values: [green,red,yellow]
     * Postcondition: A new MosaicLabel is created with the appropriate image and color
     */
    public MosaicLabel(String color) {
        super();
        this.color = color;
        try {
            setLabelIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/mosaic_"+ color.toLowerCase()+".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accessor function which returns the color of the mosaic label.
     * @return The color of the mosaic label
     */
    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MosaicLabel)) return false;
        MosaicLabel mosaicLabel = (MosaicLabel) obj;
        return getColor().equals(mosaicLabel.getColor());
    }
}
