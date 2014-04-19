package org.jis.mosaique;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import org.jis.Main;
import org.jis.options.Options;

/**
 * Create Mosaiques for the fiels selected in the JMHRST window.
 * 
 * @author tobias
 */
public class MosaiqueMaker {

	private File[] images;
	private File directory;
	private File infoFile;
	private File kachelDir;
	private int kachelSize;
	private Main m;
	private Options o;

	/**
	 * Constructor.
	 * 
	 * @param main
	 *            the Main class from JMJRST
	 * @param infoFile
	 *            the serialized info file
	 * @param kachelDir
	 *            the kachel directory
	 * @param kachelSize
	 *            the kachel size
	 */
	public MosaiqueMaker(Main main, final File infoFile, final File kachelDir,
			final int kachelSize) {

		super();
		o = Options.getInstance();
		this.directory = new File(o.getOutput_dir());
		this.m = main;
		this.infoFile = infoFile;
		this.kachelDir = kachelDir;
		this.kachelSize = kachelSize;

		init();
	}

	private void init() {
		if (this.directory.isDirectory()
				&& this.directory.listFiles().length > 0) {
			int response = JOptionPane.showConfirmDialog(
					m.list,
					m.mes.getString("Generator.53") + " "
							+ o.getOutput_dir_gallerie() + " "
							+ m.mes.getString("Generator.54"),
					m.mes.getString("Generator.52"), JOptionPane.YES_NO_OPTION);
			if (response != JOptionPane.YES_OPTION) {
				return;
			}
		}

		if (m.list.getSelectedValues().size() == 0) {
			this.images = m.list.getPictures();
		} else if (m.list.getSelectedValues().size() > 0
				&& m.list.getSelectedValues().size() < m.list.getPictures().length) {
			int response = JOptionPane.showConfirmDialog(m.list,
					m.mes.getString("Generator.23"),
					m.mes.getString("Generator.24"),
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (response) {
			case JOptionPane.YES_OPTION:
				Vector<File> vf = m.list.getSelectedValues();
				this.images = new File[vf.size()];
				for (int i = 0; i < this.images.length; i++) {
					this.images[i] = vf.get(i);
				}
				break; // generate only the selected images
			case JOptionPane.NO_OPTION:
				this.images = m.list.getPictures();
				break; // generate the whole directory
			case JOptionPane.CANCEL_OPTION:
				return; // do nothing
			case JOptionPane.CLOSED_OPTION:
				return; // do nothing
			default:
				break;
			}
		} else {
			Vector<File> vf = m.list.getSelectedValues();
			this.images = new File[vf.size()];
			for (int i = 0; i < this.images.length; i++) {
				this.images[i] = vf.get(i);
			}
		}

		Thread t = new Thread() {
			public void run() {
				m.status.setStatusOn();
				m.p_monitor = new ProgressMonitor(m,
						m.mes.getString("MosaiqueDialog.6"),
						m.mes.getString("MosaiqueDialog.7"), 0,
						images.length + 1);
				m.p_monitor.setMillisToPopup(1);
				m.p_monitor.setProgress(1);

				Configuration c = new Configuration();
				c.setInfo(infoFile);
				c.setMosaiquesource(kachelDir);
				c.setKachelSize(kachelSize);
				for (int i = 0; i < images.length; i++) {
					m.p_monitor.setProgress(i + 1);
					m.p_monitor.setNote(m.mes.getString("MosaiqueDialog.7")
							+ images[i].getName() + " (" + (i + 1) + "/"
							+ (images.length + 1) + ")");

					c.setSource(images[i]);
					c.setDest(new File(directory + "/" + images[i].getName()));
					System.out.println("Create Mosaik: " + c.getSource()
							+ " -> " + c.getDest());
					Mosaique m = new Mosaique(c);
					try {
						m.process();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}

				m.status.setStatusOff();
				m.p_monitor.close();
			}
		};
		t.start();
	}
}
