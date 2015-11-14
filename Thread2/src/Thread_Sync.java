//Basic Thread Synchronization

import java.util.Scanner;

class Processor extends Thread {
	//volatile prevents thread from caching variables when variables is not changed inside the thread
	//otherwise in many systems the while loop won't terminate though stopped by main thread
	private volatile boolean running = true;

	public void run() {
		while (running) {
			System.out.println("Hello");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	public void shutdown() {
		running = false;
	}
}

public class Thread_Sync {

	public static void main(String[] args) {
		Processor proc1 = new Processor();
		proc1.start();
		System.out.println("Hit Return to stop...");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		proc1.shutdown();
		sc.close();

	}

}
