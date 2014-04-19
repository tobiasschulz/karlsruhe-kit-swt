package edu.kit.ipd.Mosaique;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Class to hold a collection of BufferedImages Retrieve them by RGB colors
 * 
 * @author Thomas Karcher
 * 
 */
public class RGBAvgHolder implements Serializable {
    /**
     * Konstante für Serialisierung
     */
    private static final long serialVersionUID = -5501967820054898355L;

    private TreeMap<Integer, ArrayList<ClassifiedImage>> red;
    private TreeMap<Integer, ArrayList<ClassifiedImage>> green;
    private TreeMap<Integer, ArrayList<ClassifiedImage>> blue;
    private TreeMap<String, ClassifiedImage> allImages;

    /**
     * Konstruktor: Initialisiert die Datenstrukturen
     */
    public RGBAvgHolder() {
        red = new TreeMap<Integer, ArrayList<ClassifiedImage>>();
        green = new TreeMap<Integer, ArrayList<ClassifiedImage>>();
        blue = new TreeMap<Integer, ArrayList<ClassifiedImage>>();
        allImages = new TreeMap<String, ClassifiedImage>();
    }

    /**
     * Fügt eine Bilddatei in die Datenstruktur ein.
     * 
     * @param bi
     *            The image to store
     */
    public void addImage(ClassifiedImage bi) {
        if (!red.containsKey(bi.getColorAvg().getRed())) {
            // create new (empty) entry
            red.put(bi.getColorAvg().getRed(), new ArrayList<ClassifiedImage>());
        }
        red.get(bi.getColorAvg().getRed()).add(bi);

        if (!green.containsKey(bi.getColorAvg().getGreen())) {
            // create new (empty) entry
            green.put(bi.getColorAvg().getGreen(),
                    new ArrayList<ClassifiedImage>());
        }
        green.get(bi.getColorAvg().getGreen()).add(bi);

        if (!blue.containsKey(bi.getColorAvg().getBlue())) {
            // create new (empty) entry
            blue.put(bi.getColorAvg().getBlue(),
                    new ArrayList<ClassifiedImage>());
        }
        blue.get(bi.getColorAvg().getBlue()).add(bi);

        allImages.put(bi.getFilename(), bi);
    }

    /**
     * 
     * @return The list of red values with their images
     */
    public TreeMap<Integer, ArrayList<ClassifiedImage>> getRed() {
        return red;
    }

    /**
     * 
     * @return The list of green values with their images
     */
    public TreeMap<Integer, ArrayList<ClassifiedImage>> getGreen() {
        return green;
    }

    /**
     * 
     * @return The list of blue values with their images
     */
    public TreeMap<Integer, ArrayList<ClassifiedImage>> getBlue() {
        return blue;
    }

    /**
     * 
     * @return The list of all images
     */
    public TreeMap<String, ClassifiedImage> getAllImages() {
        return allImages;
    }
}
