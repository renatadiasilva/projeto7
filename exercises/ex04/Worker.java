package exercises.ex04;

import java.util.concurrent.ArrayBlockingQueue;

public class Worker implements Runnable {

    private ArrayBlockingQueue<Integer> queue;
    private String name;

    public Worker(ArrayBlockingQueue<Integer> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    public void run() {
    	
    	boolean stop = false;
        while (!stop)
            try {
            	// take value from queue
                int val = queue.take();
                if(val != -1)
                    System.out.println("Worker "+name
                    		+" took number from queue and computed its square-root: "
                    		+"sqrt("+val+")="+Math.sqrt(val));
                else // if val = -1 stop thread
                    stop = true;
            } catch(InterruptedException e) {
                System.out.println("Error: interrupting thread "+
                		Thread.currentThread().getName());
                e.printStackTrace();
            }

        System.out.println("Worker "+name+" stopped");
    }

}