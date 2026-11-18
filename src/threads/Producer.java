
package threads;

public class Producer implements Runnable {

    private SharedBuffer buffer;
    private int itemCount;
    private int delay;

    public Producer(SharedBuffer buffer, int itemCount, int delay) {
        this.buffer = buffer;
        this.itemCount = itemCount;
        this.delay = delay;
    }

    @Override
    public void run() {

        try {
            for (int i = 1; i <= itemCount; i++) {
                buffer.produce(i);
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
