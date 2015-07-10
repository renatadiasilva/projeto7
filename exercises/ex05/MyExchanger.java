package exercises.ex05;

import java.io.PrintStream;
import java.util.concurrent.Exchanger;

public class MyExchanger
    implements Runnable
{

    public MyExchanger(Exchanger exchanger, String s, Boolean myturn)
    {
        this.exchanger = exchanger;
        message = s;
        this.myturn = myturn.booleanValue();
    }

    public void run()
    {
        do
            try
            {
                if(myturn)
                {
                    System.out.println(message);
                    Thread.sleep(2000L);
                }
                myturn = ((Boolean)exchanger.exchange(Boolean.valueOf(myturn))).booleanValue();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        while(true);
    }

    private Exchanger exchanger;
    private String message;
    private boolean myturn;
}