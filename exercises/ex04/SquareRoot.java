package exercises.ex04;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class SquareRoot {
	
    private static int NWORKERS = 5, CAPACITY = 3;
    private static int MAXNUMBERS = 25, MAXVALUE = 1000, MAXSEC = 10;

	public static void main(String args[]) {
		
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(CAPACITY);
        List<Thread> workers = new ArrayList<Thread>();
        
        // launch threads
        for(int i = 0; i < NWORKERS; i++) {
            Thread w = new Thread(new Worker(q, "mythread-"+(i + 1)));
            w.start();
            
            // to remain later
            workers.add(w);
        }

        // total number of generated numbers (choose randomly)
        Random rand = new Random();
        int total = rand.nextInt(MAXNUMBERS) + 1;
        System.out.println("Master thread will generate "+total+" numbers!");
        
        // generate all numbers
        for(int i = 0; i < total; i++) {
        	
        	// generate number i and put it in the queue
        	int n = rand.nextInt(MAXVALUE);
            try {
                q.put(n);
            } catch(InterruptedException e) {
                System.out.println("Error -1: error putting number in the queue");
                e.printStackTrace();
                System.exit(-1);
            }
            System.out.println("Master thread is putting the number "+n+
            		" in the queue ("+(total - (i + 1))+" to go)");

            // wait a few seconds before generate next number
            int sec = rand.nextInt(MAXSEC);
            if(i != total - 1) {
                System.out.println("Master thread is waiting "+sec+
                		" seconds before generating new number");
                try {
                    Thread.sleep(sec * 1000);
                } catch(InterruptedException e) {
                    System.out.println("Error -2: Interrupting master thread");
                    e.printStackTrace();
                    System.exit(-2);
                }
            }
        }

        // put number -1 in the queue to stop all the other threads
        // one for each thread
        for(int i = 0; i < NWORKERS; i++)  {
            System.out.println("Master thread is putting the number -1 "
            		+ "in the queue to stop workers ("+(NWORKERS - (i + 1))+
            		" to go)");
            try {
                q.put(-1);
            } catch(InterruptedException e) {
                System.out.println("Error -3: Interrupting master thread");
                e.printStackTrace();
                System.exit(-3);
            }
        }

        // wait for all threads to finish
        for (Thread w: workers) {
            try {
                w.join();
            } catch(InterruptedException e) {
                System.out.println("Interrupting main thread");
                e.printStackTrace();
            }
        }

        // all done
        System.out.println("All done!");
    }

}