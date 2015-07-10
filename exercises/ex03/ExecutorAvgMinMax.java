package exercises.ex03;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorAvgMinMax {

	//	Use the ExecutorService and the Future interfaces to compute the following functions
	//	over a random vector with a varying number of doubles: average, minimum, maximum.
	//	The number of doubles is an argument given to the program from the command line. You
	//	should write the program as follows: the main thread computes the average and launches
	//	two threads to find the minimum and the maximum. In the end, the program should output
	//	the results to the screen. Compare the times against a sequential program that does the
	//	same thing. Prepare a plot that shows the time the computation takes in both versions,
	//	versus the number of doubles in the computation. NOTE: you must include the time to
	//	launch the tasks in the parallel version time.

	final private static int NTHREADS = 2, MAX = 100000000;
	private static double[] vector;

	public static void main(String[] args) {

		ExecutorService es = Executors.newFixedThreadPool(NTHREADS);

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
			vector = new double[dim]; 
		} catch (OutOfMemoryError e) {
			System.out.println("Error -4: Too big dimension ("+dim+")");
			System.exit(-4);
		}

		//initialize vector
		Random rand = new Random();
		for (int i = 0; i < dim; i++) {
			vector[i] = rand.nextDouble()*MAX; //random number between 0 and MAX
			//			System.out.format("v[%d] = %6.2f\n", i, vector[i]);
		}

		// PARALLEL VERSION

		long t0 = System.nanoTime(); 

		System.out.println("\n--------------------- Starting parallel version ---------------------\n");

		//launch thread for max and min
		Future<Double> tMax = es.submit(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				System.out.println("Thread "+Thread.currentThread().getName()+" is computing the maximum value");
				return vectorMax();
			}
		});
		
		Future<Double> tMin = es.submit(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				System.out.println("Thread "+Thread.currentThread().getName()+" is computing the minimum value");
				return vectorMin();
			}
		});

		double vmax = 0.0, vmin = 0.0, vavg = 0.0;
		
		try {
			vmax = tMax.get();
//			System.out.format("Maximum value: %6.2f\n", );
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Interruptiong thread "+Thread.currentThread().getName());
			e.printStackTrace();
			System.exit(-1);
		}
		try {
			vmin = tMin.get();
//			System.out.format("Minimum value: %6.2f\n", tMin.get());
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Interruptiong thread "+Thread.currentThread().getName());
			e.printStackTrace();
			System.exit(-1);
		}

		es.shutdown();

		//Average
		System.out.println("Thread "+Thread.currentThread().getName()+" is computing the average value");
		vavg = vectorAverage();
		
		System.out.println("\n*** Vector Statistics ***\n");
		System.out.format("Maximum value: %6.2f\n", vmax);
		System.out.format("Minimum value: %6.2f\n", vmin);
		System.out.format("Avegare value: %6.2f\n", vavg);

		long t1 = System.nanoTime(); 
		double time = (t1 - t0)/1000000.0; //in milliseconds
		
		System.out.println("\nComputations time (with concurrency): "+time+" miliseconds");

		// SEQUENTIAL VERSION

		t0 = System.nanoTime(); 

		System.out.println("\n--------------------- Starting sequential version ---------------------\n");
		System.out.println("\n*** Vector Statistics ***\n");
		System.out.println("\nThread "+Thread.currentThread().getName()+" is doing all the computation\n");
		System.out.format("Maximum value: %6.2f\n", vectorMax());
		System.out.format("Minimum value: %6.2f\n", vectorMin());
		System.out.format("Avegare value: %6.2f\n", vectorAverage());

		t1 = System.nanoTime(); 
		time = (t1 - t0)/1000000.0; //in milliseconds
		System.out.println("\nComputations time (without concurrency): "+time+" miliseconds");

	}

	private static double vectorMax() {
		double max = vector[0]; 
		for (int i = 1; i < vector.length; i++) {
			if (vector[i] > max) max = vector[i];
		}
		return max;
	}

	private static double vectorMin() {
		double min = vector[0]; 
		for (int i = 1; i < vector.length; i++) {
			if (vector[i] < min) min = vector[i];
		}
		return min;
	}

	private static double vectorAverage() {
		double sum = 0.0; 
		for (int i = 0; i < vector.length; i++) sum += vector[i];
		return sum/vector.length;
	}

}