package exercises.ex07;

import java.io.PrintStream;
import java.util.concurrent.*;

public class MyThreadCB
    implements Runnable
{

    public MyThreadCB(CyclicBarrier cb, ArrayBlockingQueue queue, int lim)
    {
        this.cb = cb;
        this.queue = queue;
        this.lim = lim;
    }

    public void run()
    {
        try
        {
            while(true) 
            {
                int sum = 0;
                System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" is now going to compute the").append(" sum from 1 to ").append(lim).toString());
                for(int i = 1; i <= lim; i++)
                {
                    Thread.sleep(1000L);
                    System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" is adding ").append(i).append(" to partial sum").toString());
                    sum += i;
                }

                System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" finished with sum = ").append(sum).append(" (and puts it in the queue)").toString());
                queue.put(Integer.valueOf(sum));
                System.out.println((new StringBuilder(String.valueOf(Thread.currentThread().getName()))).append(" waiting at barrier for the other threads").toString());
                cb.await();
                System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" is moving to another round\n").toString());
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        catch(BrokenBarrierException e)
        {
            e.printStackTrace();
        }
    }

    private CyclicBarrier cb;
    private ArrayBlockingQueue queue;
    private int lim;
}