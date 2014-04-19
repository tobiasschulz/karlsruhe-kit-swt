package edu.kit.ipd.Mosaique;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;

/**
 * Class to process a directory of PNG image files
 * 
 * @author Thomas Karcher
 * 
 */
public class ClassifiedDirectory implements Serializable {
    /**
     * Constant for serialization
     */
    private static final long serialVersionUID = -7772365297953380301L;

    /**
     * The collection of PNG files
     */
    private RGBAvgHolder rgbAvg;

    /**
     * @param dir
     *            The directory with image files.
     */
    public ClassifiedDirectory(File dir) {
        rgbAvg = new RGBAvgHolder();
        for (File curFile : dir.listFiles()) {
            if (!curFile.getName().toLowerCase().endsWith("png")) {
                continue;
            }
            rgbAvg.addImage(new ClassifiedImage(curFile));
        }
    }

    /**
     * Prints the RGB averages and the filename for every file, one per line.
     */
    public void printFilesAndColors() {
        for (ClassifiedImage bi : rgbAvg.getAllImages().values()) {
            System.out.println(String.format("%02X", bi.getColorAvg().getRed())
                    + " " + String.format("%02X", bi.getColorAvg().getGreen())
                    + " " + String.format("%02X", bi.getColorAvg().getBlue())
                    + " " + bi.getFilename());
        }
    }

    /**
     * Returns the best fitting ClassifiedImage.
     * 
     * @param myTileAvgColor
     *            The color to match to
     * @return The best (i. e. first-best) ClassifiedImage
     */
    public ClassifiedImage getFittingImage(Color myTileAvgColor) {
        // For each ClassifiedImage, calculate the quadratic color difference
        // and return the best fitting
        ClassifiedImage bestFittingImage = null;
        int bestColorError = Integer.MAX_VALUE;

        for (ClassifiedImage bi : rgbAvg.getAllImages().values()) {
            int colorError = (int) Math.pow(myTileAvgColor.getRed()
                    - bi.getColorAvg().getRed(), 2);

            colorError += (int) Math.pow(myTileAvgColor.getGreen()
                    - bi.getColorAvg().getGreen(), 2);

            colorError += (int) Math.pow(myTileAvgColor.getBlue()
                    - bi.getColorAvg().getBlue(), 2);

            if (bestColorError > colorError) {
                // we have a new best fitting image!
                bestColorError = colorError;
                bestFittingImage = bi;
            }

            if (bestColorError == 0) {
                return bestFittingImage;
            }
        }
        return bestFittingImage;
    }
}
