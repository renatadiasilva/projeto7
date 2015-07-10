package exercises.ex08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControlCriticalRegion {
	
    private static final int NTHREADS = 8;
    private static final int CAPACITY = 10000;

    public static void main(String args[]) {
    	
        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
        MySync mysync = new MySync(CAPACITY);
        
        // launch threads
        for(int i = 0; i < NTHREADS; i++)
            executor.execute(new MyThread(mysync));

    }

}