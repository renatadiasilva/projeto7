package exercises.ex05;

import java.util.concurrent.*;

public class PingPong {

    private static int NTHREADS = 2;
    
    public static void main(String args[]) {
    	
        ExecutorService es = Executors.newFixedThreadPool(NTHREADS);
        Exchanger<Boolean> ex = new Exchanger<Boolean>();
        
        //launch threads
        es.execute(new MyExchanger(ex, "pong", Boolean.valueOf(false)));
        es.execute(new MyExchanger(ex, "ping", Boolean.valueOf(true)));
        
        es.shutdown();
    }

}