package ACO;
import java.text.DecimalFormat;

public class Matrix {
//This class represents a matrix and contains numerous functions with which to manipulate it

	//Private class variables
	private int m; //number of rows in adjacency matrix (bins)
	private int n; //number of columns in adjacency matrix (items)
	private double[][] array; //adjacency matrix (represents construction graph)
	
	
	//ACO HELPER FUNCTIONS
	
	public void randomPheromoneInitialization() {
	/* Initializes construction graph with random pheromone values between 0 and 1
	 * Input: this.array
	 * Output: this.array filled with random values between 0 and 1
	 * 
	 */
		
	for(int r=0; r<m; r++) {
		for(int c=0; c<n; c++) {
			
			array[r][c]= Math.random();
			
		}
	}
		
		
		
	}
	
	//CONSTRUCTORS
	
	Matrix(int m, int n) {
		//Creates an empty adjacency matrix of size m x n
		this.m=m;
		this.n=n;
		array=new double[this.m][this.n];
	}

	//GETTERS
	
	int getN() {
		//Returns n (number of cols/items)
		return this.n; 
	}
	
	int getM() {
		//Returns m (number of rows/bins)
		return this.m; 
	}
	
	double get(int r, int c) {
		//Returns value of adjacency matrix at (r,c) (the amount of pheromone 
		//associated with putting item c in bin r)
		return this.array[r][c]; 
	}
	
	//SETTER
	void set(int r, int c, double x) { 
		//Sets the value of the adjacency matrix at (r,c) to x
		array[r][c]=x;
	}
	
	
	//MATHEMATICAL OPERATIONS
	
	Matrix multiply(double x) {
		/*Multiplies this matrix by a scalar
		 * Input: a number x, operating on this.array
		 * Output: a new matrix, which is the product of this.array*x.
		 * 
		 * 
		 */
		
		
		Matrix result=new Matrix(this.m,this.n);
		
		//Loop through and multiply every element and multiply by x
		for(int i=0; i<this.m;i++){
			for(int j=0; j<this.n;j++) {
				result.set(i, j, x*this.array[i][j]);
			}
		}
		
		return result;
		
	}
	
	//OTHER FUNCTIONS
	
	public String toString() {
		//Returns a string representation of the matrix with each row on a different line
		
		int thisRow = getM();
		int thisCol = getN();

		//New string
		String matrixString = "";
		int i = 0, j = 0;

		DecimalFormat decimalFormat = new DecimalFormat("##.00");

		for (i = 0; i < thisRow; i++) {
			for (j = 0; j < thisCol; j++) {
				if (j == thisCol - 1) {
			
					matrixString += decimalFormat.format((this.array[i][j]));
				} else {
					matrixString += decimalFormat.format((this.array[i][j])) + ",";
				}
			}
			matrixString += "\n";
		}
		

		return matrixString;
	}
	
	
	void print() {
		//Prints the matrix to console
		for(int i1=0; i1<m; i1++) {
			for(int j=0; j<n; j++) {
				System.out.printf(" %,.3f ", this.array[i1][j]);
			}
			
			System.out.println();
		}
	
	}
	
		
		
	}	//class
	

	 
	
	
	




