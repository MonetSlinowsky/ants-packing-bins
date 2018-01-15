# ants-packing-bins
An implementation of Ant Colony Optimization for the bin packing problem

This code produces solutions to two different bin packing problems.

Goal: 'pack' items of a certain weight into a fixed number of bins so that bins weigh an equal amount (the difference between the heaviest and lightest bin is minimized)

Input: 500 items.
        BPP1: the weight of item i is i and there are 10 bins to pack
        BPP2: weight of item i is (i^2)/2 and there are 50 bins to pack

Output: The weight of the heaviest bin - weight of the lightest bin in the best solution

Solution: The nature-inspired Ant Colony Optimization Algorithm
In the real world, as an ant travels to find food, it lays pheromone on its path. This pheromone evaporates overtime. However, ants that find the shortest path to the food will reinforce their path more quickly and thus the pheromone on shorter paths will be stronger than pheromone on longer paths. Other ants follow the pheromone towards food. Eventually, all ants will converge to one path (usually the shortest path)

This algorithm uses the concept above by putting the bin packing problem onto a construction graph that includes every possible solution. This is encoded with an adjacency matrix where bins are rows and items are columns. The value at[r+1][c+1] is the pheromone level attracting an ant to put item (c+1) in bin (r+1). 

Each iteration, every ant in the population (set at 10 currently) builds a random solution to the problem (with probability determined by the amount of pheromone on the construction graph). The best solutions are reinforced with the most pheromone. At the end of the iteration, the all pheromone is evaporated by an evaporation rate of 0.6 (to encourage exploration of the graph). 

After 10, 000 fitness evaluations, the program returns the best solution constructed. 

ACO_main: implements the algorithm
Ant: the class used to represent an 'ant' building a solution
Matrix: helper class used to implement the pheromone matrix 
