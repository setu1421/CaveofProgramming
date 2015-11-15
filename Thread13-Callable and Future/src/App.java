/*
 * A Future represents the result of an asynchronous computation. 
 * Methods are provided to check if the computation is complete, 
 * to wait for its completion, and to retrieve the result of the computation. 
 * The result can only be retrieved using method get when the computation has completed, 
 * blocking if necessary until it is ready. Cancellation is performed by the cancel method. 
 * Additional methods are provided to determine if the task completed normally or was cancelled. 
 * Once a computation has completed, the computation cannot be cancelled. 
 * If you would like to use a Future for the sake of cancellability but not provide a usable result, 
 * you can declare types of the form Future<?> and return null as a result of the underlying task. 
 */

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();

		/*
		 * Callable is a task that returns a result and may throw an exception.
		 * Implementors define a single method with no arguments called call.
		 * The Callable interface is similar to Runnable, in that both are
		 * designed for classes whose instances are potentially executed by
		 * another thread. A Runnable, however, does not return a result and
		 * cannot throw a checked exception.
		 */

		Future<Integer> future = executor.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {

				Random rnd = new Random();
				int duration = rnd.nextInt(4000);
				System.out.println("Starting");
				Thread.sleep(duration);
				System.out.println("Finished");

				return duration;
			}

		});
		executor.shutdown();

		try {
			// Waits if necessary for the computation to complete, and then
			// retrieves its result.
			System.out.println("Result is:" + future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println(e);
		}
	}
}
