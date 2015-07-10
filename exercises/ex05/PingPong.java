package exercises.ex05;

import java.util.concurrent.*;

// Referenced classes of package exercises.ex05:
//            MyExchanger

public class PingPong
{

    public PingPong()
    {
    }

    public static void main(String args[])
    {
        ExecutorService es = Executors.newFixedThreadPool(NTHREADS);
        Exchanger ex = new Exchanger();
        es.execute(new MyExchanger(ex, "pong", Boolean.valueOf(false)));
        es.execute(new MyExchanger(ex, "ping", Boolean.valueOf(true)));
        es.shutdown();
    }

    private static int NTHREADS = 2;

}