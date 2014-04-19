package edu.kit.ipd.Mosaique;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class represents a mosaique image.
 * 
 * @author tobias
 */
public class MosaiqueImage {

	private BufferedImage original;
	private Color[][] kachelAvgColor;
	private int rows;
	private int columns;
	private int kachelHeight;
	private int kachelWidth;

	/**
	 * The Constructor. It reads the given file and calculates the average color
	 * of all Kacheln.
	 * 
	 * @param sourcePath
	 *            The path for the image file to be read.
	 * @param rows
	 *            the rows of the mosaique
	 * @param columns
	 *            the columns of the mosaique
	 */
	public MosaiqueImage(File sourcePath, int rows, int columns) {
		try {
			this.rows = rows;
			this.columns = columns;
			this.original = ImageIO.read(sourcePath);
			this.kachelHeight = original.getHeight() / rows;
			this.kachelWidth = original.getWidth() / columns;

			calculateKachelAvgColor();

		} catch (IOException e) {
			System.out.println("Konnte Datei " + sourcePath + " nicht lesen.");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Calculate the average color of all Kacheln.
	 */
	private void calculateKachelAvgColor() {
		if (original.getHeight() % rows != 0
				|| original.getWidth() % columns != 0) {
			throw new IllegalArgumentException(
					"Die Höhe und Breite des Bildes ist nicht teilbar"
							+ "durch Anzahl der Zeilen und Spalten.");
		} else if (original.getHeight() / rows < 40
				|| original.getWidth() / columns < 40) {
			throw new IllegalArgumentException(
					"Die Kacheln müssen mindestens 40x40 Pixel groß sein.");
		}

		int kachelHeight = original.getHeight() / rows;
		int kachelWidth = original.getWidth() / columns;

		kachelAvgColor = new Color[rows][columns];

		for (int y = 0; y < rows; ++y) {
			for (int x = 0; x < columns; ++x) {
				kachelAvgColor[y][x] = ClassifiedImage.calculateColorAvg(
						original, x * kachelWidth, y * kachelHeight, (x + 1)
								* kachelWidth, (y + 1) * kachelHeight);
				System.out.println("kachel[" + y + "][" + x + "] = "
						+ Util.printColor(kachelAvgColor[y][x]));
			}
		}
	}

	/**
	 * Create the final mosaique image and save it to the given target file. The
	 * Kacheln come from the given classified directory.
	 * 
	 * @param target
	 *            the file to save the mosaique to
	 * @param directory
	 *            the directory containing the Kacheln
	 */
	public void createMosaique(File target, ClassifiedDirectory directory) {
		// find image files for each kachel
		File[][] kachelFiles = new File[rows][columns];
		for (int y = 0; y < rows; ++y) {
			for (int x = 0; x < columns; ++x) {
				kachelFiles[y][x] = directory.findKachel(kachelAvgColor[y][x]);
			}
		}

		// construct the new image
		BufferedImage newImage = new BufferedImage(original.getWidth(),
				original.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		// read the image files, scale them and draw them to the new image
		for (int y = 0; y < rows; ++y) {
			for (int x = 0; x < columns; ++x) {

				final File file = kachelFiles[y][x];

				System.out.print("draw: kachel (" + y + "," + x + "): " + file
						+ ": ");

				Image scaled = Util.scaleImage(file, kachelWidth, kachelHeight);
				graphics.drawImage(scaled, x * kachelWidth, y * kachelHeight,
						null);
			}
		}
		graphics.dispose();

		try {
			ImageIO.write(newImage, "png", target);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
