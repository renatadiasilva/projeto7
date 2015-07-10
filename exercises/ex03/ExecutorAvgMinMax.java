package exercises.ex03;

import java.io.IOException;
import java.util.Random;

public class ExecutorAvgMinMax {

	final private static int MAX = 100000000;
	private static float[] vector;

	public static void main(String[] args) throws IOException {

		//input validation
		if (args.length == 0) {
			System.out.println("Error -1: The program must have an argument (vector dimension)");
			System.exit(-1);			
		}

		int dim = 1;
		try {
			dim = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("Error -2: The program argument must be a number (positive integer)");
			System.exit(-2);
		}

		if (dim <= 0) {
			System.out.println("Error -3: The program argument must be a positive integer");
			System.exit(-3);
		}

		//create vector
		System.out.println("Creating vector and generating random numbers...");
		try {
			vector = new float[dim]; 
		} catch (OutOfMemoryError e) {
			System.out.println("Error -4: Too big dimension ("+dim+")");
			System.exit(-4);
		}

		//initialize vector
		System.out.println("Vector dimension: "+dim);
		Random rand = new Random();
		for (int i = 0; i < dim; i++) {
			vector[i] = rand.nextFloat()*MAX; //random number between 0 and MAX
		}

		// SEQUENTIAL VERSION

		System.out.println("\nComputations time (without concurrency): "+MySequencial.runSeq(vector)+" "
				+ "miliseconds");

		// SEQUENTIAL VERSION

		System.out.println("\nComputations time (without concurrency): "+MySequencial.runSeq(vector)+" "
				+ "miliseconds");

		// PARALLEL VERSION

		System.out.println("\nComputations time (without concurrency): "+MyParallel.runPar(vector)+" "
				+ "miliseconds");		

	}

}