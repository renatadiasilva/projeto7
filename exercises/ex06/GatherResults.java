package exercises.ex06;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.*;

// Referenced classes of package exercises.ex06:
//            MyThreadCDL

public class GatherResults
{

    public GatherResults()
    {
    }

    public static void main(String args[])
        throws InterruptedException
    {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch latch = new CountDownLatch(4);
        ArrayBlockingQueue queue = new ArrayBlockingQueue(4);
        if(args.length != 0 && args[0].charAt(0) == 'a')
            version = 'a';
        if(version == 'b')
            System.out.println("******* VERSION B *******\n");
        else
            System.out.println("******* VERSION A *******\n");
        Random rand = new Random();
        for(int i = 0; i < 4; i++)
        {
            int n = rand.nextInt(10) + i + 1;
            executor.execute(new MyThreadCDL(start, latch, queue, n, version));
        }

        Thread.sleep(5000L);
        if(version == 'b')
        {
            System.out.println("\nAfter a few seconds, thread main gives premission for the 4 extra threads to start\n");
            start.countDown();
        } else
        {
            System.out.println("\nAfter a few seconds, thread main gives premission for the 4 extra threads to start, but the threads may already started...\n");
        }
        try
        {
            latch.await();
        }
        catch(InterruptedException e)
        {
            System.out.println("Interruption of thread main");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println();
        int result = 0;
        boolean sign = rand.nextBoolean();
        for (int i = 0; i < NTHREADS; i++)
        {
        	if (queue.isEmpty()) {
        		System.out.println("Queue empty!!");
        		System.exit(-1);
        	}
            int n = ((Integer)queue.take()).intValue();
            if(sign)
            {
                System.out.println((new StringBuilder("Main thread is subtracting ")).append(n).append(" (taken from the queue)").append(" from the final result").toString());
                n = -n;
            } else
            {
                System.out.println((new StringBuilder("Main thread is adding      ")).append(n).append(" (taken from the queue)").append(" to   the final result").toString());
            }
            sign = !sign;
            result += n;
        }
        System.out.println((new StringBuilder("\nAll done (final result = ")).append(result).append(")").toString());
        executor.shutdown();
    }

    static final int NTHREADS = 4;
    static char version = 'b';

}