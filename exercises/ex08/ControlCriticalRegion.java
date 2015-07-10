package exercises.ex08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Referenced classes of package exercises.ex08:
//            MySync, MyThread

public class ControlCriticalRegion
{

    public ControlCriticalRegion()
    {
    }

    public static void main(String args[])
    {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        MySync mysync = new MySync(10);
        for(int i = 0; i < 8; i++)
            executor.execute(new MyThread(mysync));

    }

    private static final int NTHREADS = 8;
    private static final int CAPACITY = 100;
}