package com.csd4020.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * JPanel subclass that will act as the background
 * of the board.
 */
public class BackgroundPanel extends JPanel {
    BufferedImage img;

    public BackgroundPanel() {
        super();
        // try to read the image from the resources
        {
            try {
                img = ImageIO.read(getClass().getResourceAsStream("/background.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw the custom image
        g.drawImage(img,0,0,800,800,null);
    }
}
