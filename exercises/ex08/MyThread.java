package exercises.ex08;

import java.io.PrintStream;
import java.util.Random;

// Referenced classes of package exercises.ex08:
//            MySync

public class MyThread
    implements Runnable
{

    MyThread(MySync sync)
    {
        this.sync = sync;
    }

    public void run()
    {
        do
            try
            {
                do
                {
                    sync.enter();
                    Thread.sleep(5000L);
                    sync.leave();
                    Random rand = new Random();
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
    private MySync sync;
}