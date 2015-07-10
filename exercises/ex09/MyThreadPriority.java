package exercises.ex09;

import java.io.PrintStream;
import java.util.Random;

// Referenced classes of package exercises.ex09:
//            MySyncPriority

public class MyThreadPriority
    implements Runnable
{

    MyThreadPriority(MySyncPriority sync, int max)
    {
        this.sync = sync;
        maxPriority = max;
    }

    public void run()
    {
        do
            try
            {
                do
                {
                    Random rand = new Random();
                    sync.enter(rand.nextInt(maxPriority));
                    Thread.sleep(5000L);
                    sync.leave();
                    int sec = rand.nextInt(10);
                    Thread.sleep(sec * 1000);
                } while(true);
            }
            catch(InterruptedException e)
            {
                System.out.println((new StringBuilder("Interrupting thread ")).append(Thread.currentThread().getName()).toString());
                e.printStackTrace();
            }
        while(true);
    }

    static final int MAXSEC = 10;
    private MySyncPriority sync;
    private int maxPriority;
}