package edu.kit.ipd.Mosaique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * 
 * @author tk
 * 
 */
public class TilePreview extends JPanel {
    private static final long serialVersionUID = -9095168942924775630L;

    private Dimension mySize = new Dimension(300, 300);
    private Integer myTileSize;

    /**
     * Ctor for the tile preview window.
     * 
     * @param ts
     *            The tile size to start with.
     */
    public TilePreview(Integer ts) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setSize(mySize);
        setMinimumSize(mySize);
        setMaximumSize(mySize);
        setTileSize(ts);
    }

    /**
     * Mandatory method for re-painting.
     * 
     * @param g
     *            The graphics object to repaint.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Integer ts = myTileSize; ts < 300; ts += myTileSize) {
            g.drawLine(ts, 0, ts, 300);
            g.drawLine(0, ts, 300, ts);
        }
    }

    /**
     * Mandatory method needed for proper rendering.
     * 
     * @return The window size for the preview.
     */
    @Override
    public Dimension getPreferredSize() {
        return mySize;
    }

    /**
     * Sets a new tile size to show.
     * 
     * @param ts
     *            The tile size in pixel.
     */
    public void setTileSize(Integer ts) {
        myTileSize = ts;
        repaint();
    }
}
