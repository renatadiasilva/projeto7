package exercises.ex08;

public class MySync {

    private int capacity;
    private int calledNumber;
    private int myTicket;

    public MySync(int capacity) {
        this.capacity = capacity;
        calledNumber = 1;
        myTicket = 1;
    }

    public synchronized void enter() throws InterruptedException {
    	// get order
        System.out.println("Thread "+Thread.currentThread().getName()
        			+" with order "+myTicket+" arrived");
        int order = myTicket;
        
    	// to avoid overflow (cyclic order numbers)
        if(myTicket + 1 > capacity) myTicket = 1;
        else myTicket++;
        
        // check if critical region is free
        while(calledNumber != order) 
            wait();
        
       	// thread enter critical region
        System.out.println("Thread "+Thread.currentThread().getName()
        			+" entered critical region and will be doing critical"
        			+" stuff for a few seconds");
    }

    public synchronized void leave() throws InterruptedException {
       	// thread leaves critical region
        System.out.println("Thread "+Thread.currentThread().getName()
        			+" leaved the critical region");
       	
        // to avoid overflow (cyclic order numbers)
        if (calledNumber + 1 > capacity) calledNumber = 1;
        else calledNumber++;
        
       	// let the others know that critical region is free
        notifyAll();
    }

}