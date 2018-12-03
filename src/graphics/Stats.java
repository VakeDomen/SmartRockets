package graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.Simulation;

public class Stats {

	private final int RIGHT_OFFSET = 150;
	private final int TOP_OFFSET = 30;
	private final int ROW_HEIGHT = 15;
	
	
	private int x;
	private int y;
	
	Simulation sim;
	
	public Stats(Simulation sim) {
		this.sim = sim;
		this.x = sim.getStage().getWidth() - RIGHT_OFFSET;
		this.y = TOP_OFFSET;
	}
	
	
	
	public void draw(Graphics2D g2d) {
		Color c = g2d.getColor();
		
		g2d.setColor(Color.GREEN);
		g2d.drawString("SIMMULATION: " + sim.getSimmulationNum(), x, y);
		g2d.drawString("GENERATION: " + sim.getGeneration(), x, y + ROW_HEIGHT);
		g2d.drawString("SUCCESS RATE: " + sim.getSuccessRate(), x, y + ROW_HEIGHT*2);
		
		g2d.setColor(c);
	}

}
