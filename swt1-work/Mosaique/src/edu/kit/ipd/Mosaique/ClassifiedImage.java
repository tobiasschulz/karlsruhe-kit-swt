package edu.kit.ipd.Mosaique;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * Klasse zum Einlesen einer Bilddatei und zum Berechnen der
 * Farbdurchschnittswerte
 * 
 * @author Thomas Karcher
 * 
 */
public class ClassifiedImage implements Serializable {
	/**
	 * Konstante für Serialisierung
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
			colorAvg = calculateColorAvg(bi, 0, 0, bi.getWidth(),
					bi.getHeight());
		} catch (IOException e) {
			System.out.println("Konnte Datei " + sourcePath + " nicht lesen.");
			e.printStackTrace();
		}
	}

	/**
	 * 
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
	 * @param topLeftX
	 *            the x value of the top left corner
	 * @param topLeftY
	 *            the y value of the top left corner
	 * @param bottomRightX
	 *            the x value of the bottom right corner
	 * @param bottomRightY
	 *            the y value of the bottom right corner
	 * @return The 3-value array of RGB averages.
	 */
	public static Color calculateColorAvg(BufferedImage myBi, int topLeftX,
			int topLeftY, int bottomRightX, int bottomRightY) {
		long[] myColorAvg = new long[3];
		for (int y = topLeftY; y < bottomRightY; y++) {
			for (int x = topLeftX; x < bottomRightX; x++) {
				Color c = new Color(myBi.getRGB(x, y));
				myColorAvg[0] += c.getRed();
				myColorAvg[1] += c.getGreen();
				myColorAvg[2] += c.getBlue();
			}
		}
		int pixelCount = (bottomRightY - topLeftY) * (bottomRightX - topLeftX);
		return new Color((int) (myColorAvg[0] / pixelCount),
				(int) (myColorAvg[1] / pixelCount),
				(int) (myColorAvg[2] / pixelCount));
	}

	/*
	 * public void writeTo() {
	 * 
	 * try { ImageIO.write(bi, "png", config.getTarget()); } catch (IOException
	 * e) { // Behandle IOException beim Schreiben
	 * System.out.println("Zieldatei konnte nicht geschrieben werden: ");
	 * e.printStackTrace(); } }
	 */

	/**
	 * 
	 * @return The 3-value array of RGB averages.
	 */
	public Color getColorAvg() {
		return colorAvg;
	}

}
