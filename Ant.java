package ACO;

public class Ant implements Comparable{
//This class represents an ant. 
	
//Attributes
	

//The path array represents the ant's solution. Each index represents an item and the value at that index
//is the bin into which the item was placed
int path[];

//The fitness attribute is the fitness of the ant's solution (heaviest-lightest). The 
//fitness is negative if the ant's solution is not valid (valid when every item has been placed into a bin)
double fitness; 


Ant(int items) {
/*Initializes a new ant
 * Input: items-> the number of items to place
 * Output: an ant with a path consisting of all 0s and an initial fitness of -1.
 */
	path= new int[items];
	fitness=-1;
}

public void addTo(int nextBin, int i) {
	/*
	/* Input: nextBin-> the bin the item is to be added to
	 * 	      i -> the index of the item to be added
	 * 
	 * Output: item at index i is put into bin nextBin 
	 * 
	 */
	
	path[i]=nextBin;
	
}

public double getFitness() {
	/* 
	 * Returns the fitness of the ant's current solution
	 */
	return fitness;
}

public int getBin(int i) {
	/*Returns the bin into which item i was placed*/
	
	return path[i];
}

public void updateFitness(int b, boolean BPP1) {
	/*
	 * Evaluates the fitness of the ant's current solution
	 * 
	 * Input: b -> the number of bins
	 *        BPP1-> true if problem is BPP1, false if BPP2
	 *        
	 *Output: Updates the fitness of the ant's solution
	 */
	
	//Array that will hold the weight of each bin (bin 1 is at index 0 etc)
	double[] temp_bins=new double[b];
	
	//Find the total weight of each bin
	for(int i=0; i<path.length; i++) {
		
		if(BPP1==true) {
		temp_bins[path[i]]=temp_bins[path[i]] +(i+1);
		}
		
		else {
			double weight=((double) (i+1)*(i+1))/2;
			temp_bins[path[i]]=temp_bins[path[i]] +weight;
		}
		
	}
	
	//Find the heaviest and lightest bins
	double heaviest=Integer.MIN_VALUE;
	double lightest=Integer.MAX_VALUE; 
	for(int i=0; i< temp_bins.length; i++) {

		if(temp_bins[i] > heaviest) {
			heaviest=temp_bins[i];
		}
		
		if(temp_bins[i] < lightest) {
			lightest=temp_bins[i];

		}
		
	}//end for
	
	//Calculate fitness
	fitness=(double) (heaviest-lightest); 
}


@Override
public int compareTo(Object otherObject) {
	/*
	 * Compares two ants based on the fitness of their solution
	 * Input: otherObject -> another ant
	 * Output: Returns 1 if the other ant comes before this ant in sorting order (as the goal is to
	 *         minimize fitness, sorting is done in ascending order. The other ant has a smaller fitness)
	 *         Returns -1 if the other ant comes after this ant (other ant has a larger fitness)
	 *         Returns 0 if ants have the same fitness
	 *         Exits if otherObject is not an object of the class Ant
	 */
	
	if(getClass()!=otherObject.getClass()) {
		System.out.println("Class not the same.");
		System.exit(0);
	}
	
	Ant other=(Ant) otherObject;
	
	if(this.fitness > other.getFitness()) {
		//Fitness is greater so other ant "comes before" this ant since fitness (heaviest-lightest) should be minimized
		return 1;
	}
	
	if(this.fitness < other.getFitness()) {
		//Fitness is less than other, so other ant "comes after" this ant 
		return -1;
	}
	
	//Fitnesses are equal
	return 0;
}

public boolean equals(Object otherObject) {
	/*
	 * Returns true if otherObject has the same fitness as this ant
	 */
	
	if(otherObject==null) return false;
	
	else if(getClass() != otherObject.getClass()) return false;
	
	else {
		
		Ant other=(Ant) otherObject;
		return(this.fitness==other.getFitness());
		
		
	}
		
}

public String toString() {
	/*
	 * Returns a string representation of the ant
	 */
	String strRep="";
	for(int i=0; i<path.length; i++) {
		strRep+="item: " + (i+1) + " in bin: " + (path[i]+1) + "\n";
	}
	strRep+="Total fitness is " +fitness + "\n";
	strRep+="\n";
	
	return strRep;
		
}

public void print() {
	/*
	 * Prints a representation of the ant
	 */
	System.out.println(toString());
}


	
}
