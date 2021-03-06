package exercises.ex03;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyParallel {

	final private static int NTHREADS = 2;

	public static double runPar(float[] vector) {

		ExecutorService es = Executors.newFixedThreadPool(NTHREADS);

		//initial time
		long t0 = System.nanoTime(); 

		System.out.println("\n--------------------- Starting parallel version ---------------------\n");
		System.out.println("\n*** Vector Statistics ***\n");

		// submit thread for max
		Future<Float> tMax = es.submit(new Callable<Float>() {
			@Override
			public Float call() throws Exception {
				float max = vector[0]; 

				for (int i = 1; i < vector.length; i++) {
					if (vector[i] > max) max = vector[i];
				}
				return max;
			}
		});

		// submit thread for min
		Future<Float> tMin = es.submit(new Callable<Float>() {
			@Override
			public Float call() throws Exception {
				float min = vector[0]; 
				for (int i = 1; i < vector.length; i++) {
					if (vector[i] < min) min = vector[i];
				}
				return min;
			}
		});

		// get max
		try {
			System.out.format("Maximum value: %6.2f\n", tMax.get());
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Interruptiong thread "+Thread.currentThread().getName());
			e.printStackTrace();
			System.exit(-1);
		}
		
		// get min
		try {
			System.out.format("Minimum value: %6.2f\n", tMin.get());
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Interruptiong thread "+Thread.currentThread().getName());
			e.printStackTrace();
			System.exit(-1);
		}

		//compute average
		float sum = 0.0f; 
		for (int i = 0; i < vector.length; i++) sum += vector[i];
		System.out.format("Avegare value: %6.2f\n", sum/vector.length);

		es.shutdown();

		long t1 = System.nanoTime(); 

		//computations time
		return (t1 - t0)/1000000.0; //in milliseconds

	}

}