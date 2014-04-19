/*
 * Copyright 2007 Johannes Geppert 
 * 
 * Licensed under the GPL, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * http://www.fsf.org/licensing/licenses/gpl.txt 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on 
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations under the License.
 */
package org.jis.mosaique;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;

import org.jis.Main;
import org.jis.listner.CloseListner;
import org.jis.options.Options;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 *         <p>
 *         The Dialog for creating a Web Gallerie
 *         </p>
 */
public class MosaiqueDialog extends JDialog {
	private JTextField textBausteine;
	private JTextField textInfodatei;
	private JButton buttonBausteine = new JButton();
	private JButton buttonInfodatei = new JButton();

	private JSlider sliderKachelSize;

	private JButton buttonOk = new JButton();
	private JButton buttonExit = new JButton();

	private JPanel layout = new JPanel();
	private JDialog mosaiqueDialog;
	private Main m;
	private Options o;
	private static final long serialVersionUID = -6983342868655569763L;
	private Preview preview;

	/**
	 * @param m a reference to the Main Class.
	 * @throws HeadlessException when run headless
	 */
	public MosaiqueDialog(Main m) throws HeadlessException {
		super(m, m.mes.getString("Menu.17"), true);
		o = Options.getInstance();
		this.mosaiqueDialog = this;
		this.m = m;

		init();
	}

	private void init() throws HeadlessException {

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		GraphicsDevice gd = gs[0];
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		Rectangle bounds = gc.getBounds();
		setSize(550, 550);
		setLocation((bounds.width / 2) - 550 / 2, (bounds.height / 2) - 550 / 2);
		addWindowListener(new CloseListner());

		JLabel labelBausteine = new JLabel(m.mes.getString("MosaiqueDialog.0"));
		JLabel labelInfodatei = new JLabel(m.mes.getString("MosaiqueDialog.1"));
		JLabel labelKachelSize = new JLabel(m.mes.getString("MosaiqueDialog.2"));

		textBausteine = new JTextField(o.getKachelDirMosaik());
		textBausteine.setEditable(false);
		textBausteine.setCaretPosition(0);
		buttonBausteine = new JButton(m.mes.getString("OptionsEdit.4"));
		buttonBausteine.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("icons/folder.png")));
		buttonBausteine.addActionListener(al);

		textInfodatei = new JTextField(o.getInfoFile());
		textInfodatei.setEditable(false);
		textInfodatei.setCaretPosition(0);
		buttonInfodatei = new JButton(m.mes.getString("OptionsEdit.4"));
		buttonInfodatei.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("icons/folder.png")));
		buttonInfodatei.addActionListener(al);

		sliderKachelSize = new JSlider();
		sliderKachelSize.setMinimum(Preview.MIN_KACHEL_SIZE);
		sliderKachelSize.setMaximum(Preview.MAX_KACHEL_SIZE);
		sliderKachelSize.setValue(o.getKachelSize());
		sliderKachelSize.setEnabled(true);
		sliderKachelSize.setPaintTicks(true);
		sliderKachelSize.setPaintLabels(true);
		sliderKachelSize.setLabelTable(sliderKachelSize.createStandardLabels(
				20, Preview.MIN_KACHEL_SIZE));
		sliderKachelSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				o.setKachelSize(sliderKachelSize.getValue());
				preview.setKachelSize(o.getKachelSize());
			}
		});

		preview = new Preview();

		buttonOk = new JButton(m.mes.getString("OptionsEdit.5"));
		buttonOk.setEnabled(false);
		buttonExit = new JButton(m.mes.getString("OptionsEdit.6"));
		buttonOk.addActionListener(al);
		buttonExit.addActionListener(al);

		BorderLayout bl = new BorderLayout();
		bl.setHgap(3);
		bl.setVgap(3);
		this.setLayout(bl);

		// GridLayout gl2 = new GridLayout(3, 3);
		// gl2.setHgap(5);
		// gl2.setVgap(5);
		GridBagLayout gbl1 = new GridBagLayout();
		JPanel select = new JPanel();
		select.setLayout(gbl1);

		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.fill = GridBagConstraints.BOTH;
		gbc1.insets = new Insets(3, 3, 3, 3);

		setConstraints(gbc1, 0, 2, 1, 1, 10, 10);
		gbl1.setConstraints(labelBausteine, gbc1);
		select.add(labelBausteine);

		setConstraints(gbc1, 1, 2, 1, 1, 70, 10);
		gbl1.setConstraints(textBausteine, gbc1);
		select.add(textBausteine);

		setConstraints(gbc1, 2, 2, 1, 1, 20, 10);
		gbl1.setConstraints(buttonBausteine, gbc1);
		select.add(buttonBausteine);

		setConstraints(gbc1, 0, 3, 1, 1, 10, 10);
		gbl1.setConstraints(labelInfodatei, gbc1);
		select.add(labelInfodatei);

		setConstraints(gbc1, 1, 3, 1, 1, 70, 10);
		gbl1.setConstraints(textInfodatei, gbc1);
		select.add(textInfodatei);

		setConstraints(gbc1, 2, 3, 1, 1, 20, 10);
		gbl1.setConstraints(buttonInfodatei, gbc1);
		select.add(buttonInfodatei);

		setConstraints(gbc1, 0, 4, 1, 1, 10, 10);
		gbl1.setConstraints(labelKachelSize, gbc1);
		select.add(labelKachelSize);

		setConstraints(gbc1, 1, 4, 2, 1, 70, 10);
		gbl1.setConstraints(sliderKachelSize, gbc1);
		select.add(sliderKachelSize);

		gbc1.fill = GridBagConstraints.BOTH;
		setConstraints(gbc1, 0, 5, 3, 1, 300, 300);
		gbl1.setConstraints(preview, gbc1);
		select.add(preview);

		/*
		 * GridLayout gl = new GridLayout(4, 3); gl.setHgap(5); gl.setVgap(5);
		 * 
		 * table = new JPanel(); table.setLayout(gl); table.add(l_width);
		 * table.add(c_tab_width); table.add(new JLabel());
		 * 
		 * table.add(l_heigth); table.add(c_tab_heigth); table.add(new
		 * JLabel());
		 * 
		 * table.add(l_bcolor); table.add(p_color); table.add(b_color);
		 * 
		 * table.add(l_t_color); table.add(p_t_color); table.add(b_t_color);
		 */

		GridBagConstraints gbc = new GridBagConstraints();
		preview.setKachelSize(sliderKachelSize.getValue());

		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttons.add(buttonOk);
		buttons.add(buttonExit);

		validateButtons();

		add(select, BorderLayout.NORTH);
		add(layout, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void setConstraints(GridBagConstraints gbc, int gx, int gy, int gw,
			int gh, int wx, int wy) {
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
	}

	ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonExit) {
				mosaiqueDialog.setVisible(false);
			} else if (e.getSource() == buttonOk) {
				makeMosaique();
			} else if (e.getSource() == buttonBausteine) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File f = new File(o.getKachelDirMosaik());
				if (f.exists()) {
					fc.setCurrentDirectory(f);
				} else {
					fc.setCurrentDirectory(FileSystemView.getFileSystemView()
							.getHomeDirectory());
				}
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					textBausteine.setText(fc.getSelectedFile().toString());
				}
			} else if (e.getSource() == buttonInfodatei) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				File f = new File(o.getInfoFile());
				if (f.exists()) {
					fc.setCurrentDirectory(f);
				} else {
					fc.setCurrentDirectory(FileSystemView.getFileSystemView()
							.getHomeDirectory());
				}
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					textInfodatei.setText(fc.getSelectedFile().toString());
				}
			}

			validateButtons();
		}
	};

	private void validateButtons() {
		File outDir = new File(o.getOutput_dir());
		if (outDir.isDirectory()) {
			File bauDir = new File(textBausteine.getText());
			File serFile = new File(textInfodatei.getText());
			if (textBausteine.getText().length() > 0
					&& textInfodatei.getText().length() > 0) {
				buttonOk.setEnabled(bauDir.isDirectory()
						&& (serFile.canWrite() || serFile.canRead() || !serFile
								.exists()));
			} else if (textBausteine.getText().length() > 0) {
				buttonOk.setEnabled(bauDir.isDirectory());
			} else if (textInfodatei.getText().length() > 0) {
				buttonOk.setEnabled(serFile.exists() && serFile.canRead());
			} else {
				buttonOk.setEnabled(false);
			}
		} else {
			buttonOk.setEnabled(false);
		}
	}

	/**
	 * The mosaqiue creation process. This method is run be the ActionListener
	 * when the Ok Button of the dialog is pressed.
	 */
	protected void makeMosaique() {
		o.setKachelDirMosaik(textBausteine.getText());
		o.setInfoFile(textInfodatei.getText());

		File bauDir = new File(o.getKachelDirMosaik());
		File serFile = new File(o.getInfoFile());
		if (textBausteine.getText().length() > 0
				&& textInfodatei.getText().length() > 0 && bauDir.isDirectory()
				&& serFile.exists()) {
			int n = JOptionPane.showConfirmDialog(mosaiqueDialog,
					m.mes.getString("MosaiqueDialog.3"),
					m.mes.getString("MosaiqueDialog.4"),
					JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				System.out.println(serFile + " wird ueberschrieben.");
				serFile.delete();
			} else {
				System.out.println(serFile + " wird nicht ueberschrieben,"
						+ " sondern eingelesen und " + bauDir
						+ " wird ignoriert.");
				bauDir = null;
			}
		}

		System.out.println("output directory: " + o.getOutput_dir());
		System.out.println("kachel directory: " + bauDir);
		System.out.println("serialized file: " + serFile);

		new MosaiqueMaker(m, serFile, bauDir, o.getKachelSize());

		mosaiqueDialog.setVisible(false);
	}
}
