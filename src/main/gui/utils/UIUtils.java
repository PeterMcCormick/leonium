package main.gui.utils;

import java.awt.Color;

import javax.swing.JPanel;

public class UIUtils {
	public static void main(String[] args) {
		new Game1();
	}

	public void test() {
	}

	public static JPanel jPanel(int x, int y) {
		return jPanel(x, y, null);
	}

	public static JPanel jPanel(int x, int y, Color color) {
		JPanel panel = new JPanel();
		panel.setSize(500, 640);
		panel.setBackground(color);
		return panel;
	}

}
