package exercises.ex06;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class MyThreadCDL implements Runnable {

    private CountDownLatch start;
    private CountDownLatch cdl;
    private ArrayBlockingQueue<Integer> queue;
    private int lim;
    private char version;

    public MyThreadCDL(CountDownLatch start, CountDownLatch cdl, 
    		ArrayBlockingQueue<Integer> queue, int lim, char version) {
        this.start = start;
        this.cdl = cdl;
        this.queue = queue;
        this.lim = lim;
        this.version = version;
    }

    public void run() {
        try {
        	// if version b, waits for permission to start
            if (version == 'b')  {
                System.out.println("Thread "+Thread.currentThread().getName()+
                		" awaiting to start.");
                start.await();
            }
            
            // computation
            int sum = 0;
            System.out.println("Thread "+Thread.currentThread().getName()+
            			" is now going to compute the"+" sum from 1 to "+lim);
            
            for(int i = 1; i <= lim; i++) {
                Thread.sleep(1000);
                System.out.println("Thread "+Thread.currentThread().getName()+
                		" is adding "+i+" to partial sum");
                sum += i;
            }

            // put sum in the queue
            System.out.println("Thread "+Thread.currentThread().getName()+
            		" finished with sum = "+sum+" (and puts it in the queue)");
            queue.put(sum);
            
            // after finishing decrease latch
            cdl.countDown();
        } catch(InterruptedException e) {
            System.out.println("Interrupting thread "+Thread.currentThread().getName());
            e.printStackTrace();
        }
    }

}