package exercises.ex02;

import java.util.*;
import java.util.concurrent.Semaphore;

public class ThreadsByOrder {

    private static int NTHREADS = 8;

    public static void main(String args[]) {
    	
        List<Thread> threads = new ArrayList<Thread>();
        Semaphore sems[] = new Semaphore[NTHREADS];
        
        // initialize semaphores
        for(int i = 0; i < sems.length; i++) {
            sems[i] = new Semaphore(1, true);
            
            // turn semaphore 7 into 'green' to allow thread 2 to be the first
            try {
                if(i + 1 != 7) sems[i].acquire();
            } catch(InterruptedException e) {
                System.out.println("Interrupting main thread");
            }
        }

        // launch threads (in a specific order: 2-1-4-3-6-5-8-7)
        for(int i = 0; i < NTHREADS; i++) {
            Thread mot = new Thread();
            
            // different semaphores change with respect to oddity
            if((i + 1) % 2 == 0) // even
                mot = new Thread(new MyOrderedThread("mythread-"+(i + 1), sems[(8 + (i - 3)) % 8], sems[i]));
            else // odd
                mot = new Thread(new MyOrderedThread("mythread-"+(i + 1), sems[i + 1], sems[i]));
            
            mot.start();
            
            // to remember later
            threads.add(mot);
        }

        // wait for all threads to finish
        for(Thread t: threads) {
            try {
                t.join();
            } catch(InterruptedException e) {
                System.out.println("Interrupting main thread");
                e.printStackTrace();
            }
        }

        // all done
        System.out.println("Main thread finishing after all tasks are done");
    }

}