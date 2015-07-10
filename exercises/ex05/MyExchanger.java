package exercises.ex05;

import java.util.concurrent.Exchanger;

public class MyExchanger implements Runnable {

    private Exchanger<Boolean> exchanger;
    private String message;
    private boolean myturn;

    public MyExchanger(Exchanger<Boolean> exchanger, String s, 
    		Boolean myturn)  {
        this.exchanger = exchanger;
        this.message = s;
        this.myturn = myturn;
    }

    public void run() {
    	
        while (true)
            try {
            	// writes message if it is my turn
                if(myturn)  {
                    System.out.println(message);
                    Thread.sleep(2000);
                }
                
                // change turn
                myturn = exchanger.exchange(myturn);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
    }

}