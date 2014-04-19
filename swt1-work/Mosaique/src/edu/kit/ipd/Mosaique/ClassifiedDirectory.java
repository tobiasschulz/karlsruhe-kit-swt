package edu.kit.ipd.Mosaique;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;

/**
 * Klasse zur Verarbeitung für ein Verzeichnis von Bilddateien im PNG-Format
 * 
 * @author Thomas Karcher
 * 
 */
public class ClassifiedDirectory implements Serializable {
	/**
	 * Konstante für Serialisierung
	 */
	private static final long serialVersionUID = -7772365297953380301L;

	private RGBAvgHolder rgbAvg;

	/**
	 * 
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
	 * Druckt die drei Farbdurchnittswerte und den Dateinamen für jede Bilddatei
	 * zeilenweise aus.
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
	 * Returns the numbers of image files in the directory.
	 * 
	 * @return the number of image files
	 */
	public int size() {
		return rgbAvg.getAllImages().size();
	}

	/**
	 * Find the best Kachel for the given color. Implements the algorithm
	 * describes on the Übungsblatt.
	 * 
	 * @param color
	 *            the given color
	 * @return the best matching Kachel file
	 */
	public File findKachel(Color color) {
		int smallestDiff = 0;
		File bestMatch = null;
		Color bestAvg = null;
		for (ClassifiedImage bi : rgbAvg.getAllImages().values()) {
			Color avg = bi.getColorAvg();
			int diff = ((int) Math.pow(avg.getRed() - color.getRed(), 2)
					+ (int) Math.pow(avg.getGreen() - color.getGreen(), 2) + (int) Math
					.pow(avg.getBlue() - color.getBlue(), 2));
			if (diff < smallestDiff || bestMatch == null) {
				smallestDiff = diff;
				bestMatch = new File(bi.getFilename());
				bestAvg = avg;
			}
		}
		System.out.println("findKachel: " + color + " and " + bestAvg + " ("
				+ bestMatch + "): " + smallestDiff);
		return bestMatch;
	}
}
