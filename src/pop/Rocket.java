package pop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import board.Goal;
import board.Stage;
import board.Wall;
import helpers.VectorHelper;

public class Rocket {

	private final int SPEED_CAP = 5;
	private final int ROCKET_SIZE = 5;
	
	
	private DNA dna;
	private Vector<Double> position;
	private Vector<Double> previousPosition;
	private Vector<Double> speed;
	private double fitness;
	private boolean collision = false;
	private boolean evolved = false;
	
	public Rocket(int genLength) {
		this.speed = new Vector<>();
		this.speed.add(0.);
		this.speed.add(0.);
		this.position = this.speed;
		dna = new DNA(genLength);
	}

	
	public Rocket(DNA dna) {
		this.dna = dna;
		this.speed = new Vector<>();
		this.speed.add(0.);
		this.speed.add(0.);
		this.position = this.speed;
	}
	
	
	
	
	Color col = Color.YELLOW;
	
	
	public void draw(Graphics2D g2d) {
		Color c = g2d.getColor();
		g2d.setColor(col);
		g2d.fillOval((int)Math.round(position.get(0) - ROCKET_SIZE/2), (int)Math.round( position.get(1) - ROCKET_SIZE/2 ), ROCKET_SIZE, ROCKET_SIZE);
		
		g2d.setColor(c);
	}
	
	
	
	


	public void update(int i) {
		previousPosition = position;
		speed = VectorHelper.resizeToUnit(VectorHelper.plus(speed, dna.getGene(i)), SPEED_CAP);
		position = VectorHelper.plus(position, speed);
	}





	public boolean checkCollision(Wall[] walls) {
		for( Wall w : walls) {
			if(w.checkContaining(this.position)) return true;
		}
		return false;
	}
	public boolean checkCollision(Goal goal) {
		return goal.checkContaining(this.position);
	}




	public double evaluate(Stage stage) {
		double distToGoal = stage.getGoal().getDist(this.position);
		int barriersToGoal = countBarriers(stage);
		return fitnessFunction(distToGoal, barriersToGoal);
	}





	
	
	private int countBarriers(Stage stage) {
		return new VectorHelper().barriers(VectorHelper.doubleToInt(previousPosition), stage.getGoal(), stage.getWalls());
	}





	private double fitnessFunction(double distToGoal, int barriersToGoal) {
		double multiplier = 1.0;
		if(collision) multiplier = 0.5;
		if(evolved) multiplier = 3.0;
		return 1.0/distToGoal * 5.0/(barriersToGoal+1) * multiplier;
		
	}
	
	


	public DNA breed(Rocket parent) {
		Vector<Vector<Double>> genes = new Vector<>();
		for( int i = 0 ; i < this.dna.getGenes().size() ; i++ ) {
			if ( i % 2 == 0 ) genes.add(this.dna.getGene(i));
			else genes.add(parent.getDna().getGene(i));
		}
		
		return new DNA(genes);
	}



	
	
	
	
	
	

	public void setFitness(double evaluation) { this.fitness = evaluation; }
	public double getFitness() { return this.fitness; }
	public void setEvolved(boolean b) { this.evolved = b; }
	public boolean getEvolved() { return this.evolved; }
	public DNA getDna() { return this.dna; }	
	public void setPosition(Vector<Double> position) { this.position = position; }
	public boolean getCollision() { return collision; }
	public void setCollision(boolean c) { collision=c; }




	
}
