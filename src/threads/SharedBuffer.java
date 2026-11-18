
package threads;

import java.util.LinkedList;
import java.util.Queue;

public class SharedBuffer {

    private Queue<Integer> buffer = new LinkedList<>();
    private int capacity;

    private java.util.function.Consumer<String> logger;

    public SharedBuffer(int capacity,
                        java.util.function.Consumer<String> logger) {
        this.capacity = capacity;
        this.logger = logger;
    }

    public synchronized void produce(int item) throws InterruptedException {

        while (buffer.size() == capacity) {
            wait();
        }

        buffer.add(item);
        logger.accept("Produced: " + item);

        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {

        while (buffer.isEmpty()) {
            wait();
        }

        int item = buffer.poll();
        logger.accept("Consumed: " + item);

        notifyAll();

        return item;
    }
}
