package exercises.ex09;

import java.util.PriorityQueue;

public class MySyncPriority {

    private boolean free;
    private long myTicket;
    private long calledNumber;
    private PriorityQueue<MyPriorityTask> pq;

    public MySyncPriority(PriorityQueue<MyPriorityTask> pq) {
        this.pq = pq;
        this.myTicket = 1L;
        this.calledNumber = 1L;
        this.free = true;
    }

    public synchronized void enter(int priority) throws InterruptedException {
    	// get order
        System.out.println("Thread "+Thread.currentThread().getName()
        			+" with order "+myTicket+" and priority "
        			+priority+" arrived");
        long order = myTicket;
        myTicket++;
        // using long to prevent overflow (at least to delay it...)
        
        // if the queue is empty and the critical region is free, go ahead!!
        if(pq.isEmpty() && free) calledNumber = order;
        // otherwise enter the queue
        else pq.offer(new MyPriorityTask(order, priority));
        
        // check if critical region is free (ad no thread is in the queue)
        while(calledNumber != order) 
            wait();
        
        // enter critical region
        System.out.println("Thread "+Thread.currentThread().getName()
    			+" entered critical region and will be doing critical"
    			+" stuff for a few seconds");
        free = false;
    }

    public synchronized void leave() throws InterruptedException {
    	// thread leaves critical region
        System.out.println("Thread "+Thread.currentThread().getName()
    			+" leaved the critical region");
        
        // let the other threads know the critical region is free
        free = true;
        if(!pq.isEmpty())
            calledNumber = pq.poll().getOrder(); // call next thread
        notifyAll();
    }

}