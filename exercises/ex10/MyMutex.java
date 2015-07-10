package exercises.ex10;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MyMutex extends AbstractQueuedSynchronizer {

	private static final long serialVersionUID = -2596517622609630822L;

	protected boolean tryAcquire(int acquires) {
		// if there is threads on the queue, enter the queue
		if (hasQueuedPredecessors()) {
			return false;
		// if not, check if critical region is free
		} else if (compareAndSetState(0, acquires))
			return true;
		
		// not free!
		return false;
	}

	protected boolean tryRelease(int releases) {
		// release critical region
		setState(releases);
		return true;
	}

}