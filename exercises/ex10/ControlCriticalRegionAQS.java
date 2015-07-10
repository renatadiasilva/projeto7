package exercises.ex10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControlCriticalRegionAQS {

//	Use the AbstractQueuedSynchronizer class to implement the enter() and leave()
//	methods of exercise 8 (i.e., with no priorities). HINT: use the hasQueuedPredecessors()
//	method.

	private static final int NTHREADS = 8;
	
	public static void main(String args[]) {
		
        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
        MyMutex mutex = new MyMutex();
        
        //launch threads
        for(int i = 0; i < NTHREADS; i++)
            executor.execute(new MyThread(mutex));

    }
	
}
