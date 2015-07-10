package exercises.ex09;

import java.io.PrintStream;
import java.util.PriorityQueue;

// Referenced classes of package exercises.ex09:
//            MyPriorityTask

public class MySyncPriority
{

    public MySyncPriority(PriorityQueue pq)
    {
        this.pq = pq;
        myTicket = 1L;
        calledNumber = 1L;
        free = true;
    }

    public synchronized void enter(int priority)
        throws InterruptedException
    {
        System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" with order ").append(myTicket).append(" and priority ").append(priority).append(" arrived").toString());
        long order = myTicket;
        myTicket++;
        if(pq.isEmpty() && free)
            calledNumber = order;
        else
            pq.offer(new MyPriorityTask(order, priority));
        while(calledNumber != order) 
            wait();
        System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" entered critical region and will be doing critical stuff ").append("during a few seconds").toString());
        free = false;
    }

    public synchronized void leave()
        throws InterruptedException
    {
        System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" leaved the critical region.").toString());
        free = true;
        if(!pq.isEmpty())
            calledNumber = ((MyPriorityTask)pq.poll()).getOrder();
        notifyAll();
    }

    private boolean free;
    private long myTicket;
    private long calledNumber;
    private PriorityQueue pq;
}