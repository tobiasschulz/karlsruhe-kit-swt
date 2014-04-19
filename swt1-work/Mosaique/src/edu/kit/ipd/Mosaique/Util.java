package edu.kit.ipd.Mosaique;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;

/**
 * Utility class.
 * 
 * @author tobias
 */
public final class Util {

	/**
	 * Convert the red, green, blue values of the given color to a string as
	 * space-separated hexadecimal numbers.
	 * 
	 * @param color
	 *            the color
	 * @return the string containing the three values
	 */
	public static String printColor(Color color) {
		return String.format("%02X", color.getRed()) + " "
				+ String.format("%02X", color.getGreen()) + " "
				+ String.format("%02X", color.getBlue());
	}

	// for caching
	private static TreeMap<String, Image> cache = new TreeMap<String, Image>();
	private static TreeMap<String, Integer> cacheCountAccess = new TreeMap<String, Integer>();
	private static int cleanCacheAbove = 25;

	/**
	 * Non-public constructor.
	 */
	private Util() {
	}

	/**
	 * A static method for scaling images. It automatically caches the most
	 * scaled images so they don't have to be scaled multiple times.
	 * 
	 * @param file
	 *            the file containing the image to scale. Only opened if not
	 *            cached.
	 * @param width
	 *            the width to scale to
	 * @param height
	 *            the height to scale to
	 * @return the scaled image
	 */
	public static Image scaleImage(File file, int width, int height) {
		final String path = file.getPath();

		Image scaled = null;
		if (cache.containsKey(path)) {
			scaled = cache.get(path);
			int countAccess = cacheCountAccess.get(path);
			++countAccess;
			cacheCountAccess.put(path, countAccess);
			System.out.println("accessed " + countAccess + " times");
		} else {
			System.out.println("scale...");
			if (cache.size() > cleanCacheAbove) {
				for (String cachePath : cacheCountAccess.keySet()) {
					if (cacheCountAccess.get(cachePath) <= 1) {
						cache.remove(cachePath);
						System.out.println("remove " + cachePath
								+ " from cache.");
					}
				}
				cleanCacheAbove = cache.size() + 10;
			}

			BufferedImage bufImg;
			try {
				bufImg = ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			scaled = bufImg
					.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			cache.put(path, scaled);
			if (cacheCountAccess.containsKey(path)) {
				int countAccess = cacheCountAccess.get(path);
				++countAccess;
				cacheCountAccess.put(path, countAccess);
			} else {
				cacheCountAccess.put(path, 0);
			}
		}
		return scaled;
	}
}
