package exercises.ex09;

import java.util.Random;

public class MyThreadPriority implements Runnable {

	static final int MAXSEC = 10;
	private MySyncPriority sync;
	private int maxPriority;

	public MyThreadPriority(MySyncPriority sync, int max) {
		this.sync = sync;
		this.maxPriority = max;
	}

	public void run() {
		try {
			while (true) {
				// compute random priority and try to enter
				Random rand = new Random();
				sync.enter(rand.nextInt(maxPriority));

				// enter critical region (simulate work)
				Thread.sleep(5000);
				sync.leave();
				// leave critical region
				
				// non critical region
				int sec = rand.nextInt(MAXSEC);
				Thread.sleep(sec * 1000);
			}
		} catch(InterruptedException e) {
			System.out.println("Interrupting thread "+Thread.currentThread().getName());
			e.printStackTrace();
		}
	}

}