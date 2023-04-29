package Game;

import javax.swing.*;

public class Window extends JFrame {
	private final int HEIGHT = 800;
	private final int WIDTH = 800;
	private Display display;
	Window(boolean pause) {

		setTitle("Crossy Road");

		setSize(WIDTH, HEIGHT);

		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display = new Display(pause);
		setResizable(false);
		add(display);

		setVisible(true);
	}

	public static void main(String[] args) {
		final boolean pause = true;

		new Window(pause);
	}
}