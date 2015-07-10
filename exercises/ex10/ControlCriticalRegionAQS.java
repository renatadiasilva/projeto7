package exercises.ex10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControlCriticalRegionAQS {

	private static final int NTHREADS = 8;
	
	public static void main(String args[]) {
		
        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
        MyMutex mutex = new MyMutex();
        
        //launch threads
        for(int i = 0; i < NTHREADS; i++)
            executor.execute(new MyThread(mutex));

    }
	
}
