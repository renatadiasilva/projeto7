package exercises.ex09;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControlCRPriority {

    private static final int NTHREADS = 8;
    private static final int CAPACITY = 10;
    private static final int MAX_PRIORITY = NTHREADS/2;

    public static void main(String args[]) {
    	
        ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
        Comparator<MyPriorityTask> comparator = new MyPriorityComparator();
        PriorityQueue<MyPriorityTask> 
        	pq = new PriorityQueue<MyPriorityTask>(CAPACITY, comparator);
        MySyncPriority mysync = new MySyncPriority(pq);
        
        // launch threads
        for(int i = 0; i < NTHREADS; i++)
            executor.execute(new MyThreadPriority(mysync, MAX_PRIORITY));

    }

}