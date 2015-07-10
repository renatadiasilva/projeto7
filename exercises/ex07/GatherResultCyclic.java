// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GatherResultCyclic.java

package exercises.ex07;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.*;

// Referenced classes of package exercises.ex07:
//            MyThreadCB

public class GatherResultCyclic
{

    public GatherResultCyclic()
    {
    }

    public static void main(String args[])
    {
        CyclicBarrier cb;
        ArrayBlockingQueue queue;
        Random rand;
        int counter;
        ExecutorService executor = Executors.newFixedThreadPool(4);
        cb = new CyclicBarrier(5);
        queue = new ArrayBlockingQueue(4);
        rand = new Random();
        for(int i = 0; i < 4; i++)
        {
            int n = rand.nextInt(10) + i + 1;
            executor.execute(new MyThreadCB(cb, queue, n));
        }

        try
        {
            Thread.sleep(5000L);
        }
        catch(InterruptedException e1)
        {
            System.out.println("Interruption of thread main");
            e1.printStackTrace();
            System.exit(-1);
        }
        counter = 1;
_L2:
        int result = 0;
        boolean sign = rand.nextBoolean();
        for(int i = 0; i < 4; i++)
        {
            int n = 0;
            try
            {
                n = ((Integer)queue.take()).intValue();
            }
            catch(InterruptedException e)
            {
                System.out.println("Interruption of thread main");
                e.printStackTrace();
                System.exit(-1);
            }
            if(sign)
            {
                System.out.println((new StringBuilder("Main thread is subtracting ")).append(n).append(" (taken from the queue)").append(" from the result ").append(counter).toString());
                n = -n;
            } else
            {
                System.out.println((new StringBuilder("Main thread is adding ")).append(n).append(" (taken from the queue)").append(" to the result ").append(counter).toString());
            }
            sign = !sign;
            result += n;
        }

        System.out.println((new StringBuilder("\nAll done in main thread (result ")).append(counter).append(" = ").append(result).append(")\n").toString());
        try
        {
            cb.await();
            System.out.println("Thread main is moving to another round\n");
        }
        catch(Exception e)
        {
            System.out.println("Interruption of thread main");
            e.printStackTrace();
            System.exit(-1);
        }
        counter++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    static final int NTHREADS = 4;
    static final int MAXVAL = 10;
}
