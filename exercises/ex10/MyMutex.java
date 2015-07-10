package exercises.ex10;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MyMutex extends AbstractQueuedSynchronizer {

	private static final long serialVersionUID = -2596517622609630822L;

	@Override
	protected boolean tryAcquire(int acquires) {
		if (hasQueuedPredecessors()) {
			return false;
		} else if (compareAndSetState(0, acquires))
			return true;
		return false;
	}

	@Override
	protected boolean tryRelease(int releases) {
		setState(releases);
		return true;
	}

}