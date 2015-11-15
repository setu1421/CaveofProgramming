import java.util.concurrent.Semaphore;

//This is a singleton pattern

public class Connection {

	private static Connection instance = new Connection();
	private int connections = 0;

	/*
	 * A counting semaphore. Conceptually, a semaphore maintains a set of
	 * permits. Each acquire() blocks if necessary until a permit is available,
	 * and then takes it. Each release() adds a permit, potentially releasing a
	 * blocking acquirer. However, no actual permit objects are used; the
	 * Semaphore just keeps a count of the number available and acts
	 * accordingly. Semaphores are often used to restrict the number of threads
	 * than can access some (physical or logical) resource.
	 */
	Semaphore sem = new Semaphore(10);

	private Connection() {

	}

	public static Connection getInstance() {
		return instance;
	}

	public void connect() {

		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/*
		 * The Thead.sleep in doConnect can throw exceptions that's why
		 * release() is in finally block
		 */
		try {
			doConnect();
		} finally {
			sem.release();
		}
	}

	public void doConnect() {
		synchronized (this) {
			connections++;
			System.out.println("Current Connections:" + connections);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		synchronized (this) {
			connections--;
		}
	}

}

/*
 * Links: 1.http://tutorials.jenkov.com/java-concurrency/semaphores.html
 * 2.http://www.javaworld.com/article/2077413/learn-java/semaphore.html
 */

/*
 * Here is a simple Semaphore implementation:

public class Semaphore {
  private boolean signal = false;

  public synchronized void take() {
    this.signal = true;
    this.notify();
  }

  public synchronized void release() throws InterruptedException{
    while(!this.signal) wait();
    this.signal = false;
  }

}
The take() method sends a signal which is stored internally in the Semaphore. 
The release() method waits for a signal. When received the signal flag is cleared again, 
and the release() method exited.

Using a semaphore like this you can avoid missed signals. You will call take() instead of notify() and 
release() instead of wait(). If the call to take() happens before the call to release() the thread 
calling release() will still know that take() was called, because the signal is stored internally 
in the signal variable. This is not the case with wait() and notify().
 * */
