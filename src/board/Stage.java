package board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class Stage {
	
	
	
	private int width;
	private int height;
	private String name;
	private Wall [] walls;
	private Start start;
	private Goal goal;
	
	
	
	
	
	
	
	
	
	
	
	
	public Stage (int stage) {
		init(stage);
	}

	
	
	
	private void init(int s) {
		System.out.println(s);
		switch (s) {
			case 1:
				stageOne();
				break;
			case 2:
				stageTwo();
				break;
		}
	}
	
	
	
	
	
	
	public void draw(Graphics2D g2d) {
		drawBackground(g2d);
		goal.draw(g2d);
		start.draw(g2d);
		for(Wall w : walls) w.draw(g2d);
	}

	private void drawBackground(Graphics2D g2d) {
		Color c = g2d.getColor();
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(c);
	}
	
	
	
	
	public String getName() { return name; }
	public int getHeight() {return height; }
	public int getWidth() {return width; }
	public Vector<Integer> getStartPosition(){ return start.getPositionVector(); }
	public Vector<Integer> getGoalPosition(){ return goal.getPositionVector(); }
	public Wall[] getWalls() { return walls; }
	public Goal getGoal() { return this.goal; }
	
	
	
	
	
	
	
	private void stageOne() {	
		width = 1000;
		height = 800;
		name = "Test stage one";
		walls = new Wall[6];
		//okvir
		walls[0] = new Wall(0, 0 , width, 1f, "h");
		walls[1] = new Wall(0, height, width, 1f, "h");
		walls[2] = new Wall(0, 0, height, 1f, "v");
		walls[3] = new Wall(width, 0, height, 1f, "v");
		
		walls[4] = new Wall(0, height/3, width, 0.6f , "h");
		walls[5] = new Wall((int)Math.round(width*0.4), height*2/3, width, 0.6f , "h");
		start = new Start(width/2, height - height/10);
		goal = new Goal(width/2, height/6);
		System.out.println("stage 1");
	}
	
	private void stageTwo() {	
		width = 1000;
		height = 800;
		name = "Test stage two";
		walls = new Wall[5];
		//okvir
		walls[0] = new Wall(0, 0 , width, 1f, "h");
		walls[1] = new Wall(0, height, width, 1f, "h");
		walls[2] = new Wall(0, 0, height, 1f, "v");
		walls[3] = new Wall(width, 0, height, 1f, "v");
		
		walls[4] = new Wall( (int)Math.round(width/10), (int)Math.round(height/2), width, 0.8f, "h");
		
		start = new Start(width/2, height - height/10);
		goal = new Goal(width/2, height/6);
		System.out.println("stage 2");
	}
	
}
