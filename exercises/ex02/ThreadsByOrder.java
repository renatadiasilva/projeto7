package exercises.ex02;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.Semaphore;

// Referenced classes of package exercises.ex02:
//            MyOrderedThread

public class ThreadsByOrder
{

    public ThreadsByOrder()
    {
    }

    public static void main(String args[])
    {
        List threads = new ArrayList();
        for(int i = 0; i < NTHREADS; i++)
        {
            sems[i] = new Semaphore(1, true);
            try
            {
                if(i + 1 != 7)
                    sems[i].acquire();
            }
            catch(InterruptedException e)
            {
                System.out.println("Interrupting main thread");
            }
        }

        for(int i = 0; i < NTHREADS; i++)
        {
            Thread mot = new Thread();
            if((i + 1) % 2 == 0)
                mot = new Thread(new MyOrderedThread((new StringBuilder("Thread-")).append(i + 1).toString(), sems[(8 + (i - 3)) % 8], sems[i]));
            else
                mot = new Thread(new MyOrderedThread((new StringBuilder("Thread-")).append(i + 1).toString(), sems[i + 1], sems[i]));
            mot.start();
            threads.add(mot);
        }

        for(Iterator iterator = threads.iterator(); iterator.hasNext();)
        {
            Thread t = (Thread)iterator.next();
            try
            {
                t.join();
            }
            catch(InterruptedException e)
            {
                System.out.println("Interrupting main thread");
                e.printStackTrace();
            }
        }

        System.out.println("Main thread finishing after all tasks are done");
    }

    private static int NTHREADS;
    private static Semaphore sems[];

    static 
    {
        NTHREADS = 8;
        sems = new Semaphore[NTHREADS];
    }
}