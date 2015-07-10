package exercises.ex03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlayRandomVector {
	private static final int NUM_THREADS = 2;
	private static final double MAXDOUBLE=10.0;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//Read size vector of Double 
		if (args.length < 1) {
			System.out.println("args.length<1");
			return;
		}	

		int n = Integer.parseInt(args[0]);
		System.out.println("vector size = "+n);
		
		final ArrayList<Double> vector = new ArrayList<Double>();
		for (int i=0; i<n; i++) {
			vector.add( MAXDOUBLE*Math.random() );
		}
		
		System.out.println("\n*** Beginning Computation time (paralell version) ***");
		long start_time = System.nanoTime();

		ExecutorService es = Executors.newFixedThreadPool(NUM_THREADS);
		
		Future<Double> vectorMax = es.submit(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				return Collections.max(vector);
			}
		});

		Future<Double> vectorMin = es.submit(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				return Collections.min(vector);
			}
		});

		System.out.println(" Vector Average: "+ vectorAverage(vector) );
		System.out.println(" Vector Minimal: "+ vectorMin.get() );
		System.out.println(" Vector Maximal: "+ vectorMax.get() );
		
		es.shutdown();
		long end_time = System.nanoTime();
		
		double difference = (end_time - start_time)/1e6;
		System.out.println(" Computation time (paralell version): "+difference+"ms");
		

		System.out.println("\n*** Beginning Computation time (sequencial version) ***");
		start_time = System.nanoTime();
		
		seqProgram(vector);
		
		end_time = System.nanoTime();
		difference = (end_time - start_time)/1e6;
		System.out.println(" Computation time (sequencial version): "+difference+"ms");
		
	}
	
	private static double vectorAverage(ArrayList<Double> vector) {
		if (vector.size()<1)
			return 0.0;
		double sum=0.0;
		for (double d:vector)
			sum+=d;
		return sum/vector.size();
	}
	
	private static void seqProgram( ArrayList<Double> vector) {
		System.out.println(" Vector Average: "+ vectorAverage(vector) );
		System.out.println(" Vector Minimal: "+ Collections.min(vector) );
		System.out.println(" Vector Maximal: "+ Collections.max(vector) );
	}
}