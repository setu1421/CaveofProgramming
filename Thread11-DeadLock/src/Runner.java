import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

	private Account acc1 = new Account();
	private Account acc2 = new Account();

	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	private void acquireLocks(Lock lock1, Lock lock2) {

		while (true) {
			boolean gotlock1 = false;
			boolean gotlock2 = false;

			try {
				/*
				 * Acquires the lock only if it is free at the time of
				 * invocation. Acquires the lock if it is available and returns
				 * immediately with the value true. If the lock is not available
				 * then this method will return immediately with the value
				 * false.
				 */
				gotlock1 = lock1.tryLock();
				gotlock2 = lock2.tryLock();
			} finally {
				if (gotlock1 && gotlock2) {
					return;
				}

				if (gotlock1) {
					lock1.unlock();
				}

				if (gotlock2) {
					lock2.unlock();
				}
			}
		}
	}

	public void firstThread() throws InterruptedException {
		Random rnd = new Random();
		for (int i = 0; i < 100; i++) {
			acquireLocks(lock1, lock2);
			try {
				Account.transfer(acc1, acc2, rnd.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void secondThread() throws InterruptedException {
		Random rnd = new Random();
		for (int i = 0; i < 100; i++) {
			acquireLocks(lock2, lock1);
			try {
				Account.transfer(acc2, acc1, rnd.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void finished() {
		System.out.println("Acccount1 Balance:" + acc1.getBalance());
		System.out.println("Acccount2 Balance:" + acc2.getBalance());
		System.out.println("Total Balance:"
				+ (acc1.getBalance() + acc2.getBalance()));
	}
}
