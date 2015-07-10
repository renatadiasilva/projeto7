package exercises.ex01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloWorld {

    private static int NTHREADS = 2;
    private static int NTASKS = 8;

    public static void main(String args[])
    {
        ExecutorService es = Executors.newFixedThreadPool(NTHREADS);
        
        //launch task/threads
        for(int i = 1; i <= NTASKS; i++)
            es.execute(new Task(i));

        es.shutdown();
    }

}