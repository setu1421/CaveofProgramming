//Threading--Multiple locks

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

	// Declaring multiple locks
	private Object lock1 = new Object();
	private Object lock2 = new Object();

	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();

	private Random rnd = new Random();

	public void stageOne() {

		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list1.add(rnd.nextInt(100));
		}

	}

	public synchronized void stageTwo() {

		synchronized (lock2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list2.add(rnd.nextInt(100));
		}

	}

	public void process() {
		for (int i = 0; i < 100; i++) {
			stageOne();
			stageTwo();
		}
	}

	public void main() {
		System.out.println("Starting...");
		long start = System.currentTimeMillis();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		System.out.println("Time:" + (end - start));
		System.out.println("List1 size:" + list1.size() + "\nList2 size:"
				+ list2.size());

	}
}

/*


*/