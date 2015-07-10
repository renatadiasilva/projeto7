package exercises.ex09;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Referenced classes of package exercises.ex09:
//            MyPriorityComparator, MySyncPriority, MyThreadPriority

public class ControlCRPriority
{

    public ControlCRPriority()
    {
    }

    public static void main(String args[])
    {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Comparator comparator = new MyPriorityComparator();
        PriorityQueue pq = new PriorityQueue(10, comparator);
        MySyncPriority mysync = new MySyncPriority(pq);
        for(int i = 0; i < 4; i++)
            executor.execute(new MyThreadPriority(mysync, 2));

    }

    private static final int NTHREADS = 4;
    private static final int CAPACITY = 10;
    private static final int MAX_PRIORITY = 2;
}