package exercises.ex06;

import java.util.Random;
import java.util.concurrent.*;

public class GatherResults {
	
    static final int NTHREADS = 4, MAXRAND = 10;
    static char version = 'b';

    public static void main(String args[]) {
    	
        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch latch = new CountDownLatch(4);
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(NTHREADS);
        
        //******************************************
        // choose version:
        // run with argument 'a' for exercise 6.a)
        // run with argument 'b' for exercise 6.b)
        //
        // (default: version b)
        //******************************************
        
        if(args.length != 0 && args[0].charAt(0) == 'a')
            version = 'a';
        
        if(version == 'b')
            System.out.println("******* VERSION B *******\n");
        else
            System.out.println("******* VERSION A *******\n");
        
        
        // launch threads
        Random rand = new Random();
        for(int i = 0; i < NTHREADS; i++) {
            int n = rand.nextInt(MAXRAND) + (i + 1);
            executor.execute(new MyThreadCDL(start, latch, queue, n, version));
        }

        // wait a few seconds to start
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
            System.out.println("Interruption of thread main");
            e1.printStackTrace();
            System.exit(-1);
		}
        if(version == 'b') {
            System.out.println("\nAfter a few seconds, thread main gives permission for "
            		+ "the 4 extra threads to start\n");
            start.countDown();
        } else {
            System.out.println("\nAfter a few seconds, thread main gives permission for "
            		+ "the 4 extra threads to start, but the threads may already started...\n");
        }
        
        // wait for all threads to put number in the queue
        try {
            latch.await();
        } catch(InterruptedException e) {
            System.out.println("Interruption of thread main");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println();
        
        // compute result using values of the queue
        int result = 0;
        boolean sign = rand.nextBoolean();
        for (int i = 0; i < NTHREADS; i++) {

        	// take value off the queue
        	int n = 0;
			try {
				n = queue.take();
			} catch (InterruptedException e) {
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
        
        // all done
        System.out.println("\nAll done (final result = "+result+")");
        
        executor.shutdown();
    }

}