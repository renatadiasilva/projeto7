package exercises.ex10;

import java.util.Random;

// Referenced classes of package exercises.ex08:
//            MySync

public class MyThread implements Runnable {

	private final int MAXSEC = 10;
	private MyMutex mym;

	public MyThread(MyMutex mym) {
		this.mym = mym;
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println("Thread "+Thread.currentThread().getName()+" arrived");				
				mym.acquire(1);
				System.out.println("Thread "+Thread.currentThread().getName()+" entered the critical region"+
						" and will be doing critical stuff during a few seconds");
				// enter critical region
				Thread.sleep(5000);
				mym.release(0);
				// leave critical region
				System.out.println("Thread "+Thread.currentThread().getName()+" leaved the critical region");

				//non critical region
				Random rand = new Random();
				int sec = rand.nextInt(MAXSEC);
				Thread.sleep(sec*1000);
			}
		} catch(InterruptedException e) {
			System.out.println("Interrupting thread "+Thread.currentThread().getName());
			e.printStackTrace();
		}
	}
}

