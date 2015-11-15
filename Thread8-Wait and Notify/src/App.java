/*
 * A Queue that additionally supports operations that wait for the queue to become non-empty 
 * when retrieving an element, and wait for space to become available in the queue when storing an element.
 * A BlockingQueue does not accept null elements. Implementations throw NullPointerException on attempts to add, 
 * put or offer a null. A null is used as a sentinel value to indicate failure of poll operations.
 * BlockingQueue implementations are thread-safe. All queuing methods achieve their effects atomically using internal locks or other forms of concurrency control.
 * However, the bulk Collection operations addAll, containsAll, retainAll and removeAll are not necessarily performed atomically unless specified otherwise in an implementation.
 * So it is possible, for example, for addAll(c) to fail (throwing an exception) after adding only some of the elements in c.
 * A BlockingQueue does not intrinsically support any kind of "close" or "shutdown" operation to indicate that no more items will be added.
 * The needs and usage of such features tend to be implementation-dependent. For example, a common tactic is for producers to insert special end-of-stream or poison objects, 
 * that are interpreted accordingly when taken by consumers.  
 */

public class App {

	public static void main(String[] args) throws InterruptedException {

		final Processor processor = new Processor();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.consume();
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

}
