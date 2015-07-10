package exercises.ex07;

import java.util.concurrent.*;

public class MyThreadCB implements Runnable {

    private CyclicBarrier cb;
    private ArrayBlockingQueue<Integer> queue;
    private int lim;

    public MyThreadCB(CyclicBarrier cb, ArrayBlockingQueue<Integer> queue, 
    		int lim) {
        this.cb = cb;
        this.queue = queue;
        this.lim = lim;
    }

    public void run()  {
        try {
        	// infinte loop
            while(true) {
            	// computation
                int sum = 0;
                Thread.sleep(3000);
               System.out.println("Thread "+Thread.currentThread().getName()
                		+" is now going to compute the"+" sum from 1 to "+lim);
                
                for(int i = 1; i <= lim; i++) {
                    Thread.sleep(1000);
                    System.out.println("Thread "+Thread.currentThread().getName()
                    		+" is adding "+i+" to partial sum");
                    sum += i;
                }

                // put sum in the queue
                System.out.println("Thread "+Thread.currentThread().getName()+
                		" finished with sum = "+sum+" (and puts it in the queue)");
                queue.put(sum);
                
                // wait for all the threads before moving to another round
                System.out.println("Thread "+Thread.currentThread().getName()+
                		" is waiting at barrier for the other threads");
                cb.await();
                
                System.out.println("Thread "+Thread.currentThread().getName()+
                		" is moving to another round\n");
            }
        } catch(InterruptedException e) {
           System.out.println("Interrupting thread "+Thread.currentThread().getName());
           e.printStackTrace();
        } catch(BrokenBarrierException e) {
            System.out.println("Interrupting thread "+Thread.currentThread().getName());
            e.printStackTrace();
        }
    }

}