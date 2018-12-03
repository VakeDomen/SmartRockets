package pop;

import java.util.Random;
import java.util.Vector;

import helpers.VectorHelper;

public class DNA {

	private Vector<Vector<Double>> genes = new Vector<>();
	
	public DNA(int genLength) {
		for(int i = 0 ; i < genLength ; i++) {
			genes.add(VectorHelper.randomVector(2));
		}
	}
	
	public DNA(Vector<Vector<Double>> genes) {
		this.genes = genes;
	}
	
	
	
	
	
	
	public void partialMutation() {
		genes.set(new Random().nextInt(genes.size()) , VectorHelper.randomVector(2));
	}
	public void fullMutation() {
		int genLength = genes.size();
		genes.clear();
		for(int i = 0 ; i < genLength ; i++) {
			genes.add(VectorHelper.randomVector(2));
		}
	}
	
	
	
	
	
	
	
	
	public Vector<Double> getGene(int index) { return genes.get(index); }
	public Vector<Vector<Double>> getGenes(){ return this.genes; }


}
