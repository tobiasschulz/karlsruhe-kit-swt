package org.jis.mosaique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.jis.options.Options;

/**
 * A Preview for the kachel size.
 * 
 * @author tobias
 */
public class Preview extends JPanel {

	private static final long serialVersionUID = -7017557099639861227L;

	/**
	 * the minimum kachel size
	 */
	public static final int MIN_KACHEL_SIZE = 20;
	/**
	 * the maximum kachel size
	 */
	public static final int MAX_KACHEL_SIZE = 300;
	/**
	 * the preview size
	 */
	public static final int SIZE = 300;

	private int kachelSize = 0;
	private int kacheln = 1;
	private Options o;

	/**
	 * Set a new kachel size and repaint the preview.
	 * 
	 * @param kachelSize
	 *            the new kachel size
	 */
	public void setKachelSize(int kachelSize) {
		this.kacheln = SIZE / kachelSize;
		this.kachelSize = SIZE / kacheln;
		if (this.isVisible()) {
			this.repaint();
		}
	}

	/**
	 * The default constructor.
	 */
	public Preview() {
		this.o = Options.getInstance();
		Dimension dim = new Dimension(SIZE, SIZE + 1);
		this.setSize(dim);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setKachelSize(o.getKachelSize());
	}

	/**
	 * Paint the preview.
	 * 
	 * @param g
	 *            the Graphics object
	 */
	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, SIZE, SIZE);
		for (int x = 0; x <= kacheln; ++x) {
			g.drawLine(x * kachelSize, 0, x * kachelSize, SIZE);
			System.out.println("drawLine: " + x * kachelSize + ", " + 0 + ", "
					+ x * kachelSize + ", " + SIZE);
		}
		for (int y = 0; y <= kacheln; ++y) {
			g.drawLine(0, y * kachelSize, SIZE, y * kachelSize);
			System.out.println("drawLine: " + 0 + ", " + y * kachelSize + ", "
					+ SIZE + ", " + y * kachelSize);
		}

	}
}
