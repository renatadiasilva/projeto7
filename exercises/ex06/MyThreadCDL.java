package exercises.ex06;

import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class MyThreadCDL
    implements Runnable
{

    public MyThreadCDL(CountDownLatch start, CountDownLatch cdl, ArrayBlockingQueue queue, int lim, char version)
    {
        this.start = start;
        this.cdl = cdl;
        this.queue = queue;
        this.lim = lim;
        this.version = version;
    }

    public void run()
    {
        try
        {
            if(version == 'b')
            {
                System.out.println((new StringBuilder("Thread ")).append(Thread.currentThread().getName()).append(" awaiting to start.").toString());
                start.await();
            }
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
            cdl.countDown();
        }
        catch(InterruptedException e)
        {
            System.out.println((new StringBuilder("Interrupting thread ")).append(Thread.currentThread().getName()).toString());
            e.printStackTrace();
        }
    }

    private CountDownLatch start;
    private CountDownLatch cdl;
    private ArrayBlockingQueue queue;
    private int lim;
    private char version;
}