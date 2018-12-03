package pop;

import java.awt.Graphics2D;
import java.util.Random;
import java.util.Vector;

import board.Goal;
import board.Stage;
import board.Wall;
import helpers.VectorHelper;

public class Population {
	

	private Rocket[] rockets;
	private Vector<Rocket> genePool = new Vector<>();
	private Rocket[] children;
	
	
	
	public Population(int popSize, int genLength) {
		
		rockets = new Rocket[popSize];
		for(int i = 0 ; i < popSize ; i++) rockets[i] = new Rocket(genLength);
		
	}
	public Population(Rocket[] rockets) {
		this.rockets = rockets;
	}

	public void setStartingPosition(Vector<Double> position) {
		for(Rocket r : rockets) r.setPosition(position); 
	}

	public void draw(Graphics2D g2d) {
		for(Rocket r : rockets) r.draw(g2d);
	}

	public boolean update(int i) {
		boolean update = false;
		for( Rocket r : rockets) {
			if( !r.getCollision() ) {
				r.update(i);
				update = true;
			}
			
		}
		return update;
	}

	
	public void handleCollisions(Wall[] walls, Goal goal) {
		for( Rocket r : rockets ) {
			if( r.checkCollision(walls) ) {
				r.setCollision(true); 
			}
			if( r.checkCollision(goal) ) {
				r.setCollision(true); 
				r.setEvolved(true);
			}
		}
	}

	public void evaluate(Stage stage) {
		double maxFitness=0;
		for( Rocket r : rockets) {
			double fitness = r.evaluate(stage);
			r.setFitness(fitness);
			if ( fitness > maxFitness) maxFitness = fitness;
		}
		//normailze fitness
		for( Rocket r: rockets) r.setFitness(r.getFitness()/maxFitness);
	}
	public void poolGenes() {
		genePool.clear();
		for( Rocket r : rockets) {
			int breedingValue = (int) Math.ceil(r.getFitness()*10);
			for( int i = 0 ; i < breedingValue ; i++) genePool.add(r); 
		}
	}
	public void breed() {
		Rocket[] children = new Rocket[rockets.length];
		for( int i = 0 ; i < children.length ; i++ ) {
			children[i] = new Rocket(randomParent().breed(randomParent()));
		}
		this.children = children;
	}
	private Rocket randomParent() {
		return genePool.get(new Random().nextInt(genePool.size()));
	}

	public void partialMutation(double odds) {
		for( Rocket r : children ) {
			if(VectorHelper.randBool(odds)) r.getDna().partialMutation();
		}
	}

	public void fullMutation(double odds) {
		for( Rocket r : children ) {
			if(VectorHelper.randBool(odds)) r.getDna().fullMutation();
		}
	}

	public void killParents() {
		rockets = children;
	}

	public int getEvolved() {
		int counter = 0;
		for ( Rocket r : rockets) {
			if (r.getEvolved()) counter++;
		}
		return counter;
	}
	public int getPopSize () { return this.rockets.length; } 
	public Rocket[] getRockets() { return this.rockets; }
	
	public Population split(int chunk, int threads) {
		Rocket[] tmp = new Rocket[rockets.length/threads];
		for(int i = 0 ; i < (rockets.length/threads) ; i++) {
			tmp[i] = rockets[i+chunk*rockets.length/threads];
		}
		return new Population(tmp);
	}
	public Population merge (Population p ) {
		Rocket[] tmp = new Rocket[this.rockets.length + p.getPopSize()];
		for(int i = 0 ; i < this.rockets.length + p.getPopSize() ; i++) {
			if(i <this.rockets.length) tmp[i] = rockets[i];
			else tmp[i] = p.getRockets()[i-this.rockets.length];
		}
		return new Population(tmp);
	}
	
}
