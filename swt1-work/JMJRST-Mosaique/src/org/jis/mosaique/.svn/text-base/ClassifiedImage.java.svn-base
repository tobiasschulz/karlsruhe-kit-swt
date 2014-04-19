package org.jis.mosaique;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * Class to read a PNG file and calculating RGB color averages
 * 
 * @author Thomas Karcher
 * 
 */
public class ClassifiedImage implements Serializable {
    /**
     * Constant for serialization
     */
    private static final long serialVersionUID = -3901760682799023831L;

    /**
     * Variable holds 3 color averages in this order: R - G - B
     */
    private Color colorAvg;
    private String filename;

    /**
     * 
     * @param sourcePath
     *            The path for the image file to be read.
     */
    public ClassifiedImage(File sourcePath) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(sourcePath);
            filename = sourcePath.getPath();
            colorAvg = calculateColorAvg(bi);
        } catch (IOException e) {
            System.out.println("Could not read " + sourcePath + ".");
            e.printStackTrace();
        }
    }

    /**
     * @return The corresponding filename of the image.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Static method to calculate the RGB averages.
     * 
     * @param myBi
     *            The BufferedImage that the averages should be calculated for.
     * @return The 3-value array of RGB averages.
     */
    public static Color calculateColorAvg(BufferedImage myBi) {
        long[] myColorAvg = new long[3];
        for (int y = 0; y < myBi.getHeight(); y++) {
            for (int x = 0; x < myBi.getWidth(); x++) {
                Color c = new Color(myBi.getRGB(x, y));
                myColorAvg[0] += c.getRed();
                myColorAvg[1] += c.getGreen();
                myColorAvg[2] += c.getBlue();
            }
        }
        int pixelCount = myBi.getHeight() * myBi.getWidth();
        return new Color((int) (myColorAvg[0] / pixelCount),
                (int) (myColorAvg[1] / pixelCount),
                (int) (myColorAvg[2] / pixelCount));
    }

    /**
     * @return The 3-value array of RGB averages.
     */
    public Color getColorAvg() {
        return colorAvg;
    }

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
    public static BufferedImage resize(BufferedImage img, int newW, int newH,
            int imageType) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, imageType);
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
}
