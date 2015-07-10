package exercises.ex08;

import java.util.Random;

public class MyThread implements Runnable {

	static final int MAXSEC = 10;
	private MySync sync;

	public MyThread(MySync sync) {
		this.sync = sync;
	}

	public void run() {
		try {
			while (true) {
				sync.enter();
				// enter critical region (simulate work)
				Thread.sleep(5000);
				sync.leave();
				// leave critical region
				
				// non critical region
				Random rand = new Random();
				int sec = rand.nextInt(MAXSEC);
				Thread.sleep(sec * 1000); 
			}
		} catch(InterruptedException e) {
			System.out.println("Interrupting thread "+Thread.currentThread().getName());
			e.printStackTrace();
		}
	}

}