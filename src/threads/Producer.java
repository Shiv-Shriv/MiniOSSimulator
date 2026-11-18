package threads;

public class Producer implements Runnable {

    private SharedBuffer buffer;

    public Producer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {
            for (int i = 1; i <= 10; i++) {
                buffer.produce(i);
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}