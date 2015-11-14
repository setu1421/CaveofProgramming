//Thread Synchronization(Synchronized word)
public class App {
	private int count = 0;

	public static void main(String[] args) {
		App app = new App();
		app.doWork();

	}
	//It uses Intrinsic lock

	public synchronized void increment() {
		count++;
	}

	private void doWork() {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++)
					increment();

			}

		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++)
					increment();

			}

		});

		t1.start();
		t2.start();

		try {
			t1.join(); // wait for thread 1 to die
			t2.join();// wait for thread 2 to die

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Count:" + count);

	}

}

/*
 * the count may show 200 first time but after some runs it will show erratic
 * behavior.Because the count++ is like count=count+1 so if two thread acquire
 * the variable at 50 then they will increment to 51 which should be 50.So
 * synchronized is used.It uses intrinsic lock.So one thread can get the lock on
 * the variable at one time.And no need to use volatile because synchronized
 * makes the variable visible to all the threads.
 */
