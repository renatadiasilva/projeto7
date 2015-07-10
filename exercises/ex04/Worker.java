package exercises.ex04;

import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;

public class Worker
    implements Runnable
{

    public Worker(ArrayBlockingQueue queue, String name)
    {
        this.queue = queue;
        this.name = name;
    }

    public void run()
    {
        for(boolean stop = false; !stop;)
            try
            {
                int val = ((Integer)queue.take()).intValue();
                if(val != -1)
                    System.out.println((new StringBuilder("Worker ")).append(name).append(" took number from queue and computed its square-root: ").append("sqrt(").append(val).append(")=").append(Math.sqrt(val)).toString());
                else
                    stop = true;
            }
            catch(InterruptedException e)
            {
                System.out.println((new StringBuilder("Error: interrupting thread ")).append(Thread.currentThread().getName()).toString());
                e.printStackTrace();
            }

        System.out.println((new StringBuilder("Worker ")).append(name).append(" stopped").toString());
    }

    private ArrayBlockingQueue queue;
    private String name;
}