//Thread Synchronization(Synchronized word)
/*
Two things:

First, it is not possible for two invocations of synchronized methods on the same object to interleave. 
When one thread is executing a synchronized method for an object, all other threads that invoke synchronized methods 
for the same object block (suspend execution) until the first thread is done with the object.

Second, when a synchronized method exits, it automatically establishes a happens-before relationship with 
any subsequent invocation of a synchronized method for the same object. This guarantees that changes to the 
state of the object are visible to all threads.

Synchronized methods enable a simple strategy for preventing thread interference and memory consistency errors: 
if an object is visible to more than one thread, all reads or writes to that object's variables are done through 
ynchronized methods. (An important exception: final fields, which cannot be modified after the object is constructed, 
can be safely read through non-synchronized methods, once the object is constructed).

*/

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
