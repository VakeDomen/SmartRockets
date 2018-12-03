package sim;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import board.Stage;
import helpers.VectorHelper;
import pop.Population;

public class SimChunk implements Runnable {

	
	private Population pop;
	private Stage stage;
	private int genomeLength;
	private Simulation parent;
	private int index;
	
	
	public SimChunk(Population chunk, Stage stage, int genomeLength, Simulation parent, int index) {
		this.pop = chunk;
		this.stage = stage;
		this.genomeLength = genomeLength;
		this.parent = parent;
	}
	
	@Override
	public void run() {
		simmulateGeneration();
		parent.submitSimmulation(pop);
	}



	
	
	
	
	
	private void simmulateGeneration() {
		for (int i = 0; i < genomeLength; i++) {
			
			boolean allColided = pop.update(i);
			pop.handleCollisions(stage.getWalls(), stage.getGoal());
			
			parent.submitGeneration(index, pop);
			if(!allColided) break;
			
			
		}
	}
}
	
	
	
	
	
