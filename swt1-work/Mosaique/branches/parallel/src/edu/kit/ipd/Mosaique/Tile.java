package edu.kit.ipd.Mosaique;

import java.awt.image.BufferedImage;

/**
 * Interface for mosaique tiles
 * 
 * @author tk
 * 
 */
public interface Tile {
    /**
     * Resizes a BufferedImage.
     * 
     * @param img
     *            The image to resize
     * @param newW
     *            The width to resize to
     * @param newH
     *            The height to resize to
     * @param imageType
     *            The type (color or gray) of the target image
     * @return The resized image
     */
    BufferedImage resize(BufferedImage img, int newW, int newH,
            int imageType);
    
    /**
     * @return The corresponding filename of the image.
     */
    String getFilename();

}
