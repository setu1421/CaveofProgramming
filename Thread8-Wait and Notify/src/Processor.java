import java.util.Scanner;

public class Processor {

	public void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("Producer Process Running...");

			/*
			 * Causes the current thread to wait until another thread invokes
			 * the notify() method or the notifyAll() method for this object. In
			 * other words, this method behaves exactly as if it simply performs
			 * the call wait(0). The current thread must own this object's
			 * monitor. The thread releases ownership of this monitor and waits
			 * until another thread notifies threads waiting on this object's
			 * monitor to wake up either through a call to the notify method or
			 * the notifyAll method. The thread then waits until it can
			 * re-obtain ownership of the monitor and resumes execution
			 */

			wait();
			System.out.println("Resumed...");
		}
	}

	public void consume() throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		Thread.sleep(2000);
		synchronized (this) {
			System.out.println("waiting for return key..");
			scanner.nextLine();
			System.out.println("Return key pressed.");
			/*
			 * Wakes up a single thread that is waiting on this object's
			 * monitor. If any threads are waiting on this object, one of them
			 * is chosen to be awakened. The choice is arbitrary and occurs at
			 * the discretion of the implementation.A thread waits on an
			 * object's monitor by calling one of the wait methods. The awakened
			 * thread will not be able to proceed until the current thread
			 * relinquishes the lock on this object. The awakened thread will
			 * compete in the usual manner with any other threads that might be
			 * actively competing to synchronize on this object; for example,
			 * the awakened thread enjoys no reliable privilege or disadvantage
			 * in being the next thread to lock this object. This method should
			 * only be called by a thread that is the owner of this object's
			 * monitor. A thread becomes the owner of the object's monitor in
			 * one of three ways: By executing a synchronized instance method of
			 * that object.By executing the body of a synchronized statement
			 * that synchronizes
			 */
			notify();
			// Thread.sleep(5000);//This thread will relinquish the lock not
			// after calling notify but after sleeping 5s and then wait() can
			// wake.
		}

	}
}
