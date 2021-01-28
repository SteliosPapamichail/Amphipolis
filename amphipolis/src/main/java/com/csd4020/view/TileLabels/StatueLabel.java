package com.csd4020.view.TileLabels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 *
 * A UI class representing a statue label.
 *
 * @author Stelios Papamichail csd4020
 */
public class StatueLabel extends TileLabel {
    private final String type;

    /**
     * Constructor for the StatueLabel class.
     *
     * @param type The type of the statue
     * Precondition: type is either of these values: [sphinx,caryatid]
     * Postcondition: A new StatueLabel is created with the appropriate image and type
     */
    public StatueLabel(String type) {
        super();
        this.type = type;
        try {
            setLabelIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/"+type.toLowerCase()+".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accessor function which returns the type of the statue label.
     * @return The statue label's type
     */
    public String getType() {return type;}

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StatueLabel)) return false;
        StatueLabel statueLabel = (StatueLabel) obj;
        return getType().equals(statueLabel.getType());
    }
}
