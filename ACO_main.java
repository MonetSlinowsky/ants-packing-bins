package ACO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ACO_main {

	
	public static Ant ACO(Matrix phmGraph, int p, double e, int b, int items, boolean BPP1) {
	/*This function runs the ACO algorithm
	 * Inputs:
	 * Matrix phmGraph: a two dimensional matrix of size bins x items. 
	 *           Represents the construction graph of the BPP problem. The value at
	 *           [r+1][c+1] is the pheromone level attracting an ant to put item (c+1)
	 *           in bin (r+1)
	 *
	 * int p: the number of ants in the population
	 * double e: the evaporation rate of the pheromone
	 * int b: number of bins used in the problem
	 * int items: the number of items to be packed
	 * boolean BPP1: true if the problem being solved is BPP 1 where the weight of item i is i.
	 * 	             false if the problem being solved is BPP 2 where the weight of item i is
	 *               (i^2)/2
	 * 
	 * Output: After 10, 000 fitness evaluations, returns the best Ant of the current iteration (Ant
	 *         is a class with two attributes; an array representing the ant's solution (index i represents
	 *         an item, the value at i is the bin into which item i was placed) and the fitness 
	 *         of the ant's solution 
	 */
		
		//Initialize the arraylist population to hold Ant objects.
		ArrayList<Ant> population=new ArrayList<Ant>();
		
		//Counts the number of fitness evaluations
		int count=0;
		
		//Tracks the global optimum 
		double best=Integer.MAX_VALUE;
			
		//Algorithm begins here
		while(true) {	
			
			//For every ant
			for(int i=0; i<p; i++) {
				
				//Create the ant 
				Ant temp= new Ant(items);

				//Build the solution 
				
				//For every item, choose the bin it will go into
				for(int c=0; c<items; c++) {
			
						//Select next edge to traverse (random, biased by pheromone levels 
						//on the construction graph phmGraph). This is equivalent to selecting the bin 
						//item c is put into
					
						int nextBin=selectNext(c, phmGraph);
					
						//Add the bin to the ant's path. It has just traversed node (nextBin, c) on the adjacency
						//matrix. This is equivalent to adding item c to the bin nextBin
						temp.addTo(nextBin, c);
						
				
				}//for item
			
				//Evaluate fitness of the ant's and increase evaluation count by one. 
				temp.updateFitness(b, BPP1);
				count++;
				
			
				//Add the ant's solution to the population of solutions
				population.add(temp);
			
			}//for ant
			
			//At this point, p ants each have a solution to the BPP 
			
			//Update the pheromone on the graph
			
			//While there is an ant in the population that has not yet 'deposited'
			//pheromone on the graph
			Iterator<Ant> pop_it=population.iterator();
			while(pop_it.hasNext()) {
			
				//Retreive the ant
				Ant temp=pop_it.next();
				
				//Calculate the pheromone update; the amount of pheromone it will deposit on
				//each edge it traversed
				double phmUpdate;
				phmUpdate= 100/(temp.getFitness());
			
				//Add phmUpdate to every edge traversed by iterating through the ant's path
				for(int c=0; c<items; c++) {
					
					phmGraph.set(temp.getBin(c), c, phmGraph.get(temp.getBin(c), c) + phmUpdate);
				
				}//for each node of ant's path
			
			}//for ant
			
			//Evaporate pheromone on the construction graph
			phmGraph=phmGraph.multiply(e);

			
		//Check if 10, 000 solutions reached. If yes, return best ant (of current population). 
		//Otherwise, continue
		if(count >= 10000) {
			
			population.sort((Ant a1, Ant a2) -> a1.compareTo(a2));
			System.out.println("Global optimum was: " +best);
			return population.get(0);
			
		}
		
		//Keep track of global optimum
		//This step is not neccessary to finding a solution 
		//and adds some time complexity to the algorithm
		//However the global optimum was used in the analysis so this bit of code is left
		//to illustrate how the global optimum was tracked 
		population.sort((Ant a1, Ant a2) -> a1.compareTo(a2));
		
		if(population.get(0).getFitness() < best) {
			best=population.get(0).getFitness();
		}
		
		
		//Clear population; ants will generate all new solutions next iteration
		population.clear();

		}//end while; end of iteration

	}//end ACO function
	
	
	public static int selectNext(int curItem, Matrix m) {
		/*
		 * Using a roulette wheel-type mechanism, this function selects the next edge to traverse
		 * (i.e. bin in which to put the current item) with a probability proportional to the 
		 * amount of pheromone on that edge.
		 * 
		 * Inputs:
		 * int curItem: the current item being placed (represented by column number in the adjacency matrix)
		 * Matrix m: the adjacency matrix representing the construction graph
		 * 
		 * Output: the row index in the adjacency matrix of the next node to be traversed (column index is curItem)
		 *         Can also think of it as the bin into which the item will be placed 
		 */

		//Retrieve the number of bins 
		int bins=m.getM();
		
		//This array will hold the 'roulette' wheel where each bin is given a segment based on its fitness.
		//The 'segment' is really a portion of the range from 0 to 1 that doesn't overlap with any other bin.
		double[] fitnessArray=new double[bins];
		
		fitnessArray[0]=m.get(0, curItem);
		
		for (int i = 1; i < bins; i++) {
            fitnessArray[i] = fitnessArray[i - 1] + m.get(i, curItem);

        }
		
		//'Spin' wheel. Generate random number and multiply it by the sum of the fitnesses to get 
		//a number in the desired range
		double random = Math.random() * fitnessArray[bins - 1];
		
		
		//Match the random number to a segment of the range from 0 to 1. The bin corresponding to this segment
		//is the bin the item will go in. 
		int binNum = Arrays.binarySearch(fitnessArray, random);
	    
        if (binNum < 0) {

            binNum = Math.abs(binNum + 1);

        }
    
        //Return the bin number (row index)
        
        return binNum;
	}
	
	
	public static void main(String[] args) {
		//This program calls the Ant Colony Optimization algorithmto solve the Bin Packing Problem.
		//It prints the best solution ant population after the algorithm has been run	
		
		//Parameters to be tested
		//Population size (number of ants)
		int p=10;
		
		//Evaporation rate 
		double e=0.6;
		
		//This variable indicates whether BPP1 or BPP2 is being adressed. 
		//True if BPP1 is being solved
		boolean BPP1=false;
		
		//Number of bins 
		final int b;

		//Initialize number of bins according to the problem being solved
		if(BPP1==true) {
			b=10;
		}
		else {
			b=50;
		}


		//Initialize number of items
		final int k=500;
			
		//Create construction graph
		//Note: Matrix is a helper class
		//Row index r+1 gives bin number, column index c+1 gives item number
		//Edges hold the pheromone value associated with inserting item c+1 into bin r+1
		Matrix conGraph=new Matrix(b,k);
		
		//Initialize construction graph with a random amount of pheromone (between 0 and 1)
		conGraph.randomPheromoneInitialization();
		
			
		//Call ACO

		Ant result=ACO(conGraph, p, e, b,k, BPP1);
		
		//Print the output ant
		result.print();
		
		
		}
		
		

}
