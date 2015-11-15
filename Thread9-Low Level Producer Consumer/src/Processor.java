import java.util.LinkedList;
import java.util.Random;

public class Processor {

	private LinkedList<Integer> list = new LinkedList<Integer>();
	private final int LIMIT = 0;
	private Object lock = new Object();

	public void produce() throws InterruptedException {

		int value = 0;
		while (true) {
			synchronized (lock) {
				while (list.size() == 10) {
					lock.wait();
				}
				list.add(value++);
				lock.notify();
			}

		}

	}

	public void consume() throws InterruptedException {
		Random rnd = new Random();
		while (true) {
			synchronized (lock) {
				while (list.size() == 0) {
					lock.wait();
				}
				System.out.print("List size:" + list.size());
				int value = list.removeFirst();
				System.out.println("; value is:" + value);
				lock.notify();
			}
			Thread.sleep(rnd.nextInt(1000));

		}

	}
}

// Notes

/*
 * if we write wait() instead of lock.wait() Answer:IllegalMonitorException is
 * thrown to indicate that a thread has attempted to wait on an object's monitor
 * or to notify other threads waiting on an object's monitor without owning the
 * specified monitor. This line again and again says, IllegalMonitorException
 * comes when one of the 2 situation occurs....
 * 
 * 1> wait on an object's monitor without owning the specified monitor.
 * 
 * 2> notify other threads waiting on an object's monitor without owning the
 * specified monitor.
 * 
 * synchronized (object)
 * 
 * object.wait()
 * 
 * If both object are same... then no illegalMonitorException can come.
 */
