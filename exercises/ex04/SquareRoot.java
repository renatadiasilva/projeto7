package exercises.ex04;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

// Referenced classes of package exercises.ex04:
//            Worker

public class SquareRoot
{

    public SquareRoot()
    {
    }

    public static void main(String args[])
    {
        ArrayBlockingQueue q = new ArrayBlockingQueue(CAPACITY);
        List workers = new ArrayList();
        for(int i = 0; i < NWORKERS; i++)
        {
            Thread w = new Thread(new Worker(q, (new StringBuilder("mythread-")).append(i + 1).toString()));
            w.start();
            workers.add(w);
        }

        Random rand = new Random();
        int total = rand.nextInt(MAXNUMBERS) + 1;
        System.out.println((new StringBuilder("Master thread will generate ")).append(total).append(" numbers!").toString());
        for(int i = 0; i < total; i++)
        {
            int n = rand.nextInt(MAXVALUE);
            try
            {
                q.put(Integer.valueOf(n));
            }
            catch(InterruptedException e)
            {
                System.out.println("Error -1: error putting number in the queue");
                e.printStackTrace();
                System.exit(-1);
            }
            System.out.println((new StringBuilder("Master thread is putting the number ")).append(n).append(" in the queue (").append(total - (i + 1)).append(" to go)").toString());
            int sec = rand.nextInt(MAXSEC);
            if(i != total - 1)
            {
                System.out.println((new StringBuilder("Master thread is waiting ")).append(sec).append(" seconds before generating new number").toString());
                try
                {
                    Thread.sleep(sec * 1000);
                }
                catch(InterruptedException e)
                {
                    System.out.println("Error -2: Interrupting master thread");
                    e.printStackTrace();
                    System.exit(-2);
                }
            }
        }

        for(int i = 0; i < NWORKERS; i++)
        {
            System.out.println((new StringBuilder("Master thread is putting the number -1 in the queue to stop workers (")).append(NWORKERS - (i + 1)).append(" to go)").toString());
            try
            {
                q.put(Integer.valueOf(-1));
            }
            catch(InterruptedException e)
            {
                System.out.println("Error -3: Interrupting master thread");
                e.printStackTrace();
                System.exit(-3);
            }
        }

        for(Iterator iterator = workers.iterator(); iterator.hasNext();)
        {
            Thread w = (Thread)iterator.next();
            try
            {
                w.join();
            }
            catch(InterruptedException e)
            {
                System.out.println("Interrupting main thread");
                e.printStackTrace();
            }
        }

        System.out.println("All done!");
    }

    private static int NWORKERS = 5;
    private static int CAPACITY = 3;
    private static int MAXNUMBERS = 25;
    private static int MAXVALUE = 1000;
    private static int MAXSEC = 10;

}