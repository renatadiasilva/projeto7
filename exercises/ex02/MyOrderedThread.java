package exercises.ex02;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class MyOrderedThread
    implements Runnable
{

    public MyOrderedThread(String tname, Semaphore s1, Semaphore s2)
    {
        rand = new Random();
        System.out.println((new StringBuilder("Creating ")).append(tname).toString());
        myThreadName = tname;
        sem1 = s1;
        sem2 = s2;
    }

    public void run()
    {
        try
        {
            sem1.acquire();
            System.out.println((new StringBuilder("Running ")).append(myThreadName).toString());
            int sec = rand.nextInt(10);
            System.out.println((new StringBuilder("I am thread ")).append(myThreadName).append(" and I'm doing nothing during ").append(sec).append(" seconds...").toString());
            Thread.sleep(sec * 1000);
            System.out.println((new StringBuilder("Exiting ")).append(myThreadName).toString());
            sem2.release();
        }
        catch(InterruptedException e)
        {
            System.out.println((new StringBuilder("Error: Interrupting ")).append(myThreadName).toString());
        }
    }

    static final int MAX_SEC = 10;
    private Random rand;
    private Semaphore sem1;
    private Semaphore sem2;
    private String myThreadName;
}