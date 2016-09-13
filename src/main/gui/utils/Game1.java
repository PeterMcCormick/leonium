package main.gui.utils;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game1 extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Display f = new Display();

	public Game1() {
		Game1.f.setSize(1000, 750);
		Game1.f.setResizable(false);
		Game1.f.setVisible(true);
		Game1.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game1.f.setTitle("Online First Person Shooter");

		ImageIcon image = new ImageIcon("C:/Users/Chris/Pictures/Camera Roll/WIN_20160406_113806.JPG");
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(10, 10, 400, 400);
		imageLabel.setVisible(true);
		add(imageLabel);
	}
}

class Display extends JFrame {
}
