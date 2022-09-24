package Lab2.b;
import java.util.Queue;

public class Petrov extends Thread {
    private final Queue<Integer> queue1;

    private final Queue<Integer> queue2;

    public Petrov(Queue<Integer> queue1, Queue<Integer> queue2) {
        this.queue1 = queue1;
        this.queue2 = queue2;
    }

    @Override
    public void run() {
        while (Main.running) {
            Integer item;
            synchronized (queue1) {
                while (queue1.isEmpty()) {
                    try {
                        queue1.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                item = queue1.poll();
                queue1.notifyAll();
                System.out.println("Petrov take " + item + " from Ivanov");
            }

            synchronized (queue2) {
                while (queue2.size() >= Main.capacity) {
                    try {
                        queue2.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                queue2.add(item);
                queue2.notifyAll();
            }
        }
    }
}
