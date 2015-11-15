/*
 *  CountDownLatch in Java is a type of synchronizer which allows one Thread to wait for one or more Threads before it starts processing.

    CountDownLatch works on latch principle, thread will wait until gate is open. One thread waits for n number of threads specified while creating CountDownLatch.

    e.g. final CountDownLatch latch = new CountDownLatch(3);

    Here we set the counter to 3.

    Any thread, usually main thread of application, which calls CountDownLatch.await() will wait until count reaches zero or it's interrupted by another Thread. All other threads are required to do count down by calling CountDownLatch.countDown() once they are completed or ready to the job. as soon as count reaches zero, the Thread awaiting starts running.

    Here the count is get decremented by CountDownLatch.countDown() method.

    The Thread which calls the await() method will wait until the initial count reaches to zero.

    To make count zero other threads need to call the countDown() method. Once the count become zero the thread which invoked the await() method will resume (start its execution).

    The disadvantage of CountDownLatch is that it's not reusable: once the count become zero it is no longer usable.
 */

/*
 * Classical example of using CountDownLatch in Java is any server side core Java application 
 * which uses services architecture, where multiple services are provided by multiple threads and 
 * application can not start processing until all services have started successfully.
 * */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processor implements Runnable {
	private CountDownLatch latch;

	public Processor(CountDownLatch latch) {
		this.latch = latch;
	}

	public void run() {
		System.out.println("Started.");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		latch.countDown();
	}
}

public class App {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(3);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int i = 1; i <= 3; i++) {
			executor.submit(new Processor(latch));
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Completed.");

	}

}
