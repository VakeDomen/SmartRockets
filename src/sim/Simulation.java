package sim;


import java.awt.Graphics2D;
import java.util.ArrayList;

import board.Stage;
import helpers.VectorHelper;
import pop.Population;

public class Simulation{
	//------------------------------ constants ------------------------------
	
	//length of the reocket's DNA - ammount of pushing vectors
	private final int GENOME_LENGTH = 400;
	//generation at which the simulation is terminated and 
	//considered unsuccessful if not yet evolved 
	private final int GENERATIONS_CAP = 1000;
	//how many rockets should reach the goal for the simulation
	//to be considered successful (in %)
	private final double SUCCESS_RATE_MINIMUM = 0.75;
	//odds for the rocket to be partially mutated after generation 
	private final double PARTIAL_MUTATION_ODDS = 0.3;
	//odds for the rocket to be fully mutated after generation
	private final double FULL_MUTATION_ODDS = 0.08;
	//ammount of threads simulating the generation
	private final int THREADS = 5;
	
	//---------------------------locks---------------------------------------
	
	private Object arrLock = new Object();
	private Object midSimLock = new Object();
	private Object generationLock = new Object();
	private Object successRateLock = new Object();
	private Object simulationNumLock = new Object();
		
	
	//------------------------ multithreading variables -------------------
	
	//index indicates the ammount of threads that have submited the 
	//simulated data
	private int index = 0;
	//array in which threads submit simulation data each generation
	private Population[] simulatingThreads = new Population[THREADS];	
	//array in which threads submit population data after full simulation 
	private Population[] finishedThreads = new Population[THREADS];
	
	
	//----------------------simulation variables--------------------------
	
	//total size of popoulation simulated; value comes from the constructor 
	private int populationSize;
	//the stage object represents the board of the simulation; value for
	//the stage number comes from the constructor
	private Stage stage;
	//population that is being simulated
	private Population pop;
	//current generation in the simulation cycle
	private int generation = 0;
	//current success rate of rockets hitting the goal object (in %)
	private double successRate = 0.0;
	//number of simulations ran 
	private int simulationNum = 0;
	
	
	//-------------------- constructor and init functions -----------------
	
	
	public Simulation(int stage, int populationSize) {
		this.populationSize = populationSize;
		init(stage);
	}
	
	public void init(int st) {
		initStage(st);
		initPopulation();
	}
	
	
	private void initStage(int st) {
		stage = new Stage(st);
		
	}
	private void initPopulation() {
		pop = new Population(populationSize, GENOME_LENGTH);
		pop.setStartingPosition(VectorHelper.intToDouble(stage.getStartPosition()));
	}
	
	//----------------------------- draw -------------------------------------
	
	public void draw(Graphics2D g2d) {
		
		stage.draw(g2d);
		pop.draw(g2d);	
	
	}
	
	
	//----------------------------- main simulation cycle ---------------------
	
	public int simulate() {
		while( generation <= GENERATIONS_CAP) {
			incrementGeneration();
			
			simulateGenerationThreaded(THREADS);
			 			
			if( checkSuccessRate() > SUCCESS_RATE_MINIMUM ) break;
			
			evaluate();
			breed();
			mutate();
			killParents();
			
			
		}
		return getGeneration();
	}
	
	
	
	//---------------------------- simulation methods ----------------------------
	
	private void simulateGenerationThreaded(int threads) {
		Runnable[] threadArr = new Runnable[THREADS];
		//split
		for(int i = 0 ; i < threads ; i++) {
			threadArr[i] = new Thread(new SimChunk(pop.split(i, threads), stage, GENOME_LENGTH, this, i));
		}
		//run
		for(Runnable t : threadArr) ((Thread) t).start();
		
		//gather
		try {
			for(Runnable t : threadArr)((Thread) t).join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//merge
		pop = mergePopulation(finishedThreads);
		index = 0;
		
		
	}
	
	protected void simPop(Population split) {
		for (int i = 0; i < GENOME_LENGTH; i++) {
			
			boolean simulating = split.update(i);
			split.handleCollisions(stage.getWalls(), stage.getGoal());
			
			
			if(!simulating) break;
			
		}
	}
	
	public void submitSimmulation(Population pop) {
		finishedThreads[index] = pop;
		index++;
	}


	public void submitGeneration(int index, Population pop) {
		synchronized( midSimLock ) { 
			simulatingThreads[index] = pop; 
			pop = mergePopulation(simulatingThreads);
		}
	}

	
	private Population mergePopulation(Population[] pops){
		Population  tmp = null;
		for( int i = 0 ; i < pops.length ; i++ ) {
			synchronized( arrLock ) {
				if(pops[i]!=null) {
					if( i == 0 ) tmp = pops[0];
					else tmp = tmp.merge(pops[i]);
				}
				
			}
		}
		return tmp;
	}
	
	

	
	
	private void killParents() {
		
		pop.killParents();
		pop.setStartingPosition(VectorHelper.intToDouble(stage.getStartPosition()));
		
	}
	
	private void mutate() {
		
		pop.partialMutation( PARTIAL_MUTATION_ODDS );
		pop.fullMutation( FULL_MUTATION_ODDS );
		
	}
	
	
	
	private void breed() {
		
		pop.poolGenes();
		pop.breed();
		
	}
	
	
	
	private void evaluate() {
		pop.evaluate(stage);
	}
	
	
	
	
	

	
	//------------------------------ getters and setters ---------------------
		
	public void resetSimmulation() {
		setGeneration(0);
		setSuccessRate(0);
		pop = new Population(populationSize, GENOME_LENGTH);
	}
	
	public Stage getStage() { return stage; }
	
	
	public void setPopulationSize(int popSize) {
		this.populationSize = popSize;
	}
	private double checkSuccessRate() {
		double rate = pop.getEvolved()*1.0 / populationSize*1.0;
		synchronized( successRateLock ) { successRate = rate; }
		return rate;
	}
	public double getSuccessRate() { 
		synchronized( successRateLock ) { return successRate; }
	}
	public void setSuccessRate(double sr) { 
		synchronized( successRateLock ) { this.successRate = sr; }
	}
	private void incrementGeneration() {
		synchronized( generationLock ) { generation++; }
	}
	public int getGeneration() {
		synchronized( generationLock ) { return generation; }
	}
	public void setGeneration(int gen) {
		synchronized( generationLock ) { this.generation = gen; }
	}
	public void setSimmulationNum(int n) {
		synchronized( simulationNumLock ) { this.simulationNum = n; }
	}
	public int getSimmulationNum() { 
		synchronized( simulationNumLock ) { return this.simulationNum; }
	}
	public void incrementSimmulationNum() {
		synchronized( simulationNumLock ) { this.simulationNum++; }
	}




	


}
