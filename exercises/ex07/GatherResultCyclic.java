package exercises.ex07;

import java.util.Random;
import java.util.concurrent.*;

public class GatherResultCyclic {

	static final int NTHREADS = 4, MAXVAL = 10;

	public static void main(String args[]) {
		CyclicBarrier cb = new CyclicBarrier(NTHREADS+1);
		ArrayBlockingQueue<Integer> 
		queue = new ArrayBlockingQueue<Integer>(NTHREADS);
		ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);

		// launch threads
		Random rand = new Random();
		for(int i = 0; i < NTHREADS; i++) {
			int n = rand.nextInt(MAXVAL) + (i + 1);
			executor.execute(new MyThreadCB(cb, queue, n));
		}

		// wait a few seconds to start
		try {
			Thread.sleep(5000L);
		} catch(InterruptedException e1) {
			System.out.println("Interruption of thread main");
			e1.printStackTrace();
			System.exit(-1);
		}

		int counter = 1;

		// infinite loop
		while (true) {
			// compute result using values of the queue
			int result = 0;
			boolean sign = rand.nextBoolean();
			for(int i = 0; i < NTHREADS; i++) {
				// take value off the queue
				int n = 0;
				try {
					n = queue.take();
				} catch(InterruptedException e) {
					System.out.println("Interruption of thread main");
					e.printStackTrace();
					System.exit(-1);
				}

				// randomly choose to add or to substract
				if (sign) {
					System.out.println("Main thread is subtracting "+n+
							" (taken from the queue)"+" from the final result");
					n = -n;
				} else  System.out.println("Main thread is adding      "+n+
						" (taken from the queue)"+" to   the final result");

				// alternates sign
				sign = !sign;
				result += n;
			}

			// all done for current round
			System.out.println("\nAll done in main thread (result "+
						counter+" = "+result+")\n");
			
			// all threads moving to another round
			try {
				cb.await();
				System.out.println("Thread main is moving to another round\n");
			} catch(Exception e) {
				System.out.println("Interruption of thread main");
				e.printStackTrace();
				System.exit(-1);
			}
			counter++;
		}

	}

}
