package main.gui.utils;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ShowPNG extends JFrame {
	public ShowPNG(String arg) {
		if (arg == null) {
			arg = "C:/Users/Chris/Pictures/Camera Roll/WIN_20160406_113806.JPG";
		}
		String arg2 = "C:/Users/Chris/Pictures/Camera Roll/WIN_20160406_113809.JPG";
		JPanel panel = UIUtils.jPanel(500, 640, Color.BLACK);
		ImageIcon icon = new ImageIcon(arg);
		JLabel label = new JLabel();
		label.setIcon(icon);
		panel.add(label);
		getContentPane().add(panel);
	}

	public static void main(String[] args) {
		ShowPNG sp = new ShowPNG(args.length == 0 ? null : args[0]);
		sp.setVisible(true);
		sp.setExtendedState(sp.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
}