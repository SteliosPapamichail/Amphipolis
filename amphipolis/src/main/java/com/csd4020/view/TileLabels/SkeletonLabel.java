package com.csd4020.view.TileLabels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 *
 * A UI class representing a skeleton label.
 *
 * @author Stelios Papamichail csd4020
 */
public class SkeletonLabel extends TileLabel {
    private final String type;
    private final String part;

    /**
     * Constructor for the StatueLabel class.
     *
     * @param type The type of the skeleton
     * @param part The part of the skeleton
     * Precondition: type is either of these values: [big,small] & part is either [top,bottom]
     * Postcondition: A new SkeletonLabel is created with the appropriate image and type
     */
    public SkeletonLabel(String type, String part) {
        super();
        this.type = type;
        this.part = part;
        try {
            setLabelIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/skeleton_"+type.toLowerCase()+"_"+part.toLowerCase()+".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accessor function which returns the type of the skeleton label.
     * @return The skeleton label's type
     */
    public String getType() {return type;}

    /**
     * Accessor function which returns the part attribute of the skeleton label.
     * @return The skeleton label's part.
     * Invariant: The part will either be "Top" or "Bottom"
     */
    public String getPart() {return part;}

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SkeletonLabel)) return false;
        SkeletonLabel otherSkel = (SkeletonLabel) obj;
        return (getType().equals(otherSkel.getType()) && getPart().equals(otherSkel.getPart()));
    }
}
