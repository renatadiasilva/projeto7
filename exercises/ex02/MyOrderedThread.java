package exercises.ex02;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class MyOrderedThread implements Runnable {

    private static final int MAX_SEC = 10;
    
    private Semaphore sem1;
    private Semaphore sem2;
    private String myThreadName;

    public MyOrderedThread(String tname, Semaphore s1, Semaphore s2) {
        myThreadName = tname;
        sem1 = s1;
        sem2 = s2;
    }

    public void run() {
        try
        {
        	// check if sem1 is 'green' (previous thread has completed its task)
            sem1.acquire();
            
            // doing some 'work'
            System.out.println("Running "+myThreadName);
            Random rand = new Random();
            int sec = rand.nextInt(MAX_SEC);
            System.out.println("I am thread "+myThreadName+" and I'm doing nothing during "+sec+" seconds...");
            Thread.sleep(sec * 1000);
            
            // work done
            System.out.println("Exiting "+myThreadName);
        	// turn sem2 to 'green'  (to allow next thread to complete its task)
            sem2.release();
        }
        catch(InterruptedException e) {
            System.out.println("Error: Interrupting "+myThreadName);
        }
    }

}