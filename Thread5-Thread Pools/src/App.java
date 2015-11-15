import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {

	private int id;

	public Processor(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Staring:" + id);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Completed:" + id);
	}

}

public class App {

	public static void main(String[] args) {
		// Created a Thread pool
		ExecutorService executor = Executors.newFixedThreadPool(1);

		// The tasks are submitted
		for (int i = 1; i <= 5; i++) {
			executor.submit(new Processor(i));
		}
		/*
		 * Initiates an orderly shutdown in which previously submitted tasks are
		 * executed, but no new tasks will be accepted. Invocation has no
		 * additional effect if already shut down.
		 */
		executor.shutdown();

		System.out.println("All Tasks are Submitted");

		/*
		 * Blocks until all tasks have completed execution after a shutdown
		 * request, or the timeout occurs, or the current thread is interrupted,
		 * whichever happens first.
		 */

		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		System.out.println("All tasks are Completed");

	}

}
