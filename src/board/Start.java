package board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class Start {
	private final int START_SIZE = 10;

	private int x;
	private int y;
	
	public Start(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g2d) {
		Color c = g2d.getColor();
		g2d.setColor(Color.BLUE);
		
		g2d.fillOval(x - START_SIZE/2, y - START_SIZE/2, START_SIZE, START_SIZE);
		
		g2d.setColor(c);
	}

	public Vector<Integer> getPositionVector() {
		Vector<Integer> ret = new Vector<>();
		ret.add(x);
		ret.add(y);
		return ret;
	}
}
