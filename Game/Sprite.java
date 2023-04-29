package Game;

import javax.swing.*;
import java.awt.*;

class Sprite {

	private double xloc, yloc;

	private double xdir, ydir;

	private ImageIcon image;

	private boolean show = true;

	private String filename = "";

	Sprite() {
		image = null;
		xloc = 0;
		yloc = 0;
		xdir = 0;
		ydir = 0;
	}

	public Sprite(String filename, int xloc, int yloc) {
		setImage(filename);
		this.xloc = xloc;
		this.yloc = yloc;
	}

	public Sprite(int xloc, int yloc) {
		this.xloc = xloc;
		this.yloc = yloc;
	}

	Sprite(String filename) {
		setImage(filename);
	}

	void setImage(String filename) {
		this.filename = filename;

		try {
			this.image = new ImageIcon(getClass().getResource(filename));
		} catch (Exception e) {
			image = null;
		}
	}

	int getXLoc() {
		return (int) xloc;
	}

	void setXLoc(int xloc) {
		this.xloc = xloc;
	}

	int getYLoc() {
		return (int) yloc;
	}

	void setYLoc(int yloc) {
		this.yloc = yloc;
	}

	public double getXDir() {
		return xdir;
	}

	void setXDir(double xdir) {
		this.xdir = xdir;
	}

	public double getYDir() {
		return ydir;
	}

	void setYDir(double ydir) {
		this.ydir = ydir;
	}

	String getFileName() {
		return filename;
	}

	void move() {
		xloc += xdir;
		yloc += ydir;
	}

	int getWidth() {
		if (image == null)
			return 20;
		else
			return image.getIconWidth();
	}

	int getHeight() {
		if (image == null)
			return 20;
		else
			return image.getIconHeight();
	}

	void paint(Graphics g, JPanel panel) {
		if (show) {
			if (image == null)
				g.drawRect((int) xloc, (int) yloc, 50, 50);
			else
				image.paintIcon(panel, g, (int) xloc, (int) yloc);
		}
	}
	
	
}
