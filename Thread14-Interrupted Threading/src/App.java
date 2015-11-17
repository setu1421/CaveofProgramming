import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class App {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Starting...");

		ExecutorService executor = Executors.newCachedThreadPool();
		/*
		 * Once a computation has completed, the computation cannot be
		 * cancelled. If you would like to use a Future for the sake of
		 * cancellability but not provide a usable result, you can declare types
		 * of the form Future<?> and return null as a result of the underlying
		 * task.
		 */
		Future<?> fu = executor.submit(new Callable<Void>() {

			@Override
			public Void call() throws Exception {

				Random rnd = new Random();
				for (int i = 0; i < 1E8; i++) {
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted!");
						break;
					}
					Math.sin(rnd.nextDouble());

				}
				return null;

			}
		});

		executor.shutdown();
		Thread.sleep(500);
		/*
		 * Attempts to stop all actively executing tasks, halts the processing
		 * of waiting tasks, and returns a list of the tasks that were awaiting
		 * execution. This method does not wait for actively executing tasks to
		 * terminate.
		 */
		executor.shutdownNow();
		/*
		 * Attempts to cancel execution of this task. This attempt will fail if
		 * the task has already completed, has already been cancelled, or could
		 * not be cancelled for some other reason.true if the thread executing
		 * this task should be interrupted; otherwise, in-progress tasks are
		 * allowed to complete
		 */

		fu.cancel(true);

		executor.awaitTermination(1, TimeUnit.DAYS);

		System.out.println("Finished");

	}

}
