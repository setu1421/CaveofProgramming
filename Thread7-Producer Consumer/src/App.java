import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();

	}

	public static void producer() throws InterruptedException {
		Random rnd = new Random();
		while (true) {
			queue.put(rnd.nextInt(100));
		}
	}

	public static void consumer() throws InterruptedException {
		Random rnd = new Random();
		while (true) {
			Thread.sleep(1);
			if (rnd.nextInt(10) == 0) {
				Integer value = queue.take();
				System.out.println("Taken value:" + value + "; Queue size:"
						+ queue.size());
			}
		}
	}

}