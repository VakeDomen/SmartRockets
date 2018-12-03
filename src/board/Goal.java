package board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import helpers.VectorHelper;

public class Goal {
	
	private final int GOAL_SIZE = 30;

	private int x;
	private int y;
	
	public Goal(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g2d) {
		Color c = g2d.getColor();
		g2d.setColor(Color.RED);
		
		g2d.fillOval(x-GOAL_SIZE/2, y - GOAL_SIZE/2, GOAL_SIZE, GOAL_SIZE);
		
		g2d.setColor(c);
	}
	
	
	
	public Vector<Integer> getPositionVector() {
		Vector<Integer> ret = new Vector<>();
		ret.add(x);
		ret.add(y);
		return ret;
	}

	public double getDist(Vector<Double> position) {
		return Math.sqrt( Math.pow( (this.x - position.get(0)) , 2) + Math.pow( (this.y - position.get(1)) , 2) );
	}

	public boolean checkContaining(Vector<Double> position) {
		return GOAL_SIZE/2 >= VectorHelper.dstanceBetweenPoints(position, VectorHelper.intToDouble(getPositionVector()));
	}

}
