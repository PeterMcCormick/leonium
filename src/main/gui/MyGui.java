package main.gui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.gui.utils.UIUtils;

@SuppressWarnings("serial")
public class MyGui extends JFrame {
	String arg2 = null;
	JPanel panel = null;
	ImageIcon icon = null;
	JLabel label = null;

	public MyGui() {
		this(null);
	}

	public MyGui(String arg) {
		label = new JLabel();
		icon = new ImageIcon(arg);
		panel = UIUtils.jPanel(500, 640, Color.BLACK);
		setActiveElementImage(arg);
		panel.add(label);
		getContentPane().add(panel);
		setVisible(true);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	public void setActiveElementImage(String filePath) {
		label.setIcon(new ImageIcon(filePath));
		this.repaint();
	}
	
}