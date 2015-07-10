package exercises.ex03;

public class MySequencial {

	public static double runSeq(float[] vector) {

		long t0 = System.nanoTime(); 

		System.out.println("\n--------------------- Starting sequential version ---------------------\n");
		System.out.println("\n*** Vector Statistics ***\n");
		
		// compute maximum value
		float max = vector[0]; 
		// tralhas
		for (int i = 1; i < vector.length; i++) 
			if (vector[i] > max) max = vector[i];
		
		System.out.format("Maximum value: %6.2f\n", max);
		
		// compute minimum value
		float min = vector[0]; 
		for (int i = 1; i < vector.length; i++)
			if (vector[i] < min) min = vector[i];
		
		System.out.format("Minimum value: %6.2f\n", min);

		// compute minimum value
		float sum = 0.0f; 
		for (int i = 0; i < vector.length; i++) sum += vector[i];
		float avg = sum/vector.length;
		System.out.format("Avegare value: %6.2f\n", avg);

		long t1 = System.nanoTime(); 
		return (t1 - t0)/1000000.0; //in milliseconds
	}

}
