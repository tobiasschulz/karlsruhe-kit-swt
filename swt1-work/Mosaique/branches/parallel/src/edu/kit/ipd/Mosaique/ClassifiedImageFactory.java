package edu.kit.ipd.Mosaique;

import java.awt.Color;
import java.util.TreeMap;

/**
 * Factory class for classified images
 * 
 * @author tk
 * 
 */
public class ClassifiedImageFactory {
	private TreeMap<Integer, ClassifiedImage> fittingImages;
	private ClassifiedDirectory myDirectory;

	/**
	 * Ctor taking the directory to ask for images
	 * 
	 * @param cd
	 *            The directory
	 */
	public ClassifiedImageFactory(ClassifiedDirectory cd) {
		fittingImages = new TreeMap<Integer, ClassifiedImage>();
		myDirectory = cd;
	}

	/**
	 * Returns the image best fitting to the given color If this color was asked
	 * for before, get it from the "cache"
	 * 
	 * @param avgColor
	 *            The color to find the best fitting tile for
	 * @return The ClassifiedImage fitting to the color
	 */
	public ClassifiedImage getImage(Color avgColor) {
		ClassifiedImage ci = fittingImages.get(avgColor.getRGB());
		if (null == ci) {
			ci = myDirectory.getFittingImage(avgColor);
			fittingImages.put(avgColor.getRGB(), ci);
		}
		return ci;
	}
}
