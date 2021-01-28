package com.csd4020.view.TileLabels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class LandslideLabel extends TileLabel {
    /**
     * "Constructor" for the LandslideLabel class.
     */
    public LandslideLabel() {
        super();
        try {
            setLabelIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/landslide.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
