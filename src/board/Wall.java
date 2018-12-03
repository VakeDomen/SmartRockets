package board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class Wall {
	private final int WALL_THICKNESS = 20;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	
	public Wall(int x, int y, int q, float lengthRatio, String alignment) {
		switch ( alignment ) {
			case "v":
				vertical(x, y, q, lengthRatio);
				break;
			case "h":
				horizontal(x, y, q, lengthRatio);
				break;
		}
	}
	
	
	
	
	private void vertical(int x, int y, int q, float lengthRatio) {
		this.x = x - WALL_THICKNESS/2;
		this.y = y;
		
		this.height = (int)Math.round(q*lengthRatio);
		this.width = WALL_THICKNESS;
		
	}

	private void horizontal(int x, int y, int q, float lengthRatio) {
		this.x = x;
		this.y = y - WALL_THICKNESS/2;
		
		this.width = (int)Math.round(q*lengthRatio);
		this.height = WALL_THICKNESS;
	}
	
	
	public void draw(Graphics2D g2d) {
		Color c = g2d.getColor();
		
		g2d.setColor(Color.GREEN);
		g2d.fillRect(x, y, width, height);
		
		g2d.setColor(c);
	}




	public boolean checkContaining(Vector<Double> position) {
		if(position.get(0) > x && position.get(0) < x + width && position.get(1) > y && position.get(1) < y+height ) return true;
		return false;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getX() { return x; }
	public int getY() { return y; }
	
	
	
}
