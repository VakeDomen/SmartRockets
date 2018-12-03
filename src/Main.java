import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import graphics.Gfx;
import sim.Simulation;

public class Main {
	
	
	private static final int SIMMULATION_COUNT = 250;
	
	public static BufferedWriter w;
	
	private static double avrGeneration = 0.;
	private static double avrTime = 0.;
	
	
	
	public static void main(String[] args) {	
		int stage = 1;
		int simSize = 100;
		
		try {
			w = new BufferedWriter(new FileWriter("Log.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		Simulation s = new Simulation(stage, simSize);
		
		Gfx g = new Gfx(s);
		g.init();
		
		
		//log("simmulation count: " + SIMMULATION_COUNT);
		
		/*
		 *  POPULATION SIZE : 100
		 */
		
		for( int i = 0 ; i < SIMMULATION_COUNT ; i++) {
			double st = System.currentTimeMillis(); 
			
			s.incrementSimmulationNum();
			
			
			int end = s.simulate();
			
			
			double et = System.currentTimeMillis();
			calcStat(end, et-st, i);
			
			s.resetSimmulation();
			
			
		}
		//log("avr gen: " + avrGeneration + " avr time: " + avrTime);
		
		
		
		/*
		 *  POPULATION SIZE : 500
		 */
		simSize=500;
		s.setPopulationSize(simSize);
		
		
		for( int i = 0 ; i < SIMMULATION_COUNT ; i++) {
			double st = System.currentTimeMillis(); 
			
			s.incrementSimmulationNum();
			
			
			int end = s.simulate();
			
			
			double et = System.currentTimeMillis();
			calcStat(end, et-st, i);
			
			s.resetSimmulation();
			
			
		}
		//log("avr gen: " + avrGeneration + " avr time: " + avrTime);
		
		/*
		 *  POPULATION SIZE : 2500
		 */
		simSize = 2500;
		s.setPopulationSize(simSize);
		
		for( int i = 0 ; i < SIMMULATION_COUNT ; i++) {
			double st = System.currentTimeMillis(); 
			
			s.incrementSimmulationNum();
			
			
			int end = s.simulate();
			
			
			double et = System.currentTimeMillis();
			calcStat(end, et-st, i);
			
			s.resetSimmulation();
			
			
		}
		//log("avr gen: " + avrGeneration + " avr time: " + avrTime);
		
		
		
		
		
		
		
		
		
		
		
	}


	
	private static void calcStat(int gen, double time, int counter) {
		if(counter > 0) {
			avrGeneration = (avrGeneration * counter + gen)/(counter+1);
			avrTime = (avrTime * counter + time)/(counter+1);
			System.out.println("avr gen: " + avrGeneration + " \t\t| avr time: " + avrTime);
		}else {
			avrGeneration = gen;
			avrTime = time;
			System.out.println("avr gen: " + avrGeneration + " \t\t| avr time: " + avrTime);
		}
		
		
		
		
	}



	private static void log(String s) {
		try {
			w.write(s+"\n");
			w.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
