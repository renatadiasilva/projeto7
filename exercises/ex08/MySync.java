package exercises.ex08;

import java.io.PrintStream;

public class MySync
{

    public MySync(int capacity)
    {
        this.capacity = capacity;
        calledNumber = 1;
        myTicket = 1;
    }

    public synchronized void enter()
        throws InterruptedException
    {
        System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" with order ").append(myTicket).append(" arrived").toString());
        int order = myTicket;
        if(myTicket + 1 > capacity)
            myTicket = 1;
        else
            myTicket++;
        while(calledNumber != order) 
            wait();
        System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" entered critical region and will be doing critical").append(" stuff for 5 seconds").toString());
    }

    public synchronized void leave()
        throws InterruptedException
    {
        System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" leaved the critical region.").toString());
        if(calledNumber + 1 > capacity)
            calledNumber = 1;
        else
            calledNumber++;
        notifyAll();
    }

    private int capacity;
    private int calledNumber;
    private int myTicket;
}