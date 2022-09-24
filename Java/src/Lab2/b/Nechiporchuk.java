package Lab2.b;

import java.util.Queue;

public class Nechiporchuk extends Thread {
    private final Queue<Integer> queue2;

    public Nechiporchuk(Queue<Integer> queue2) {
        this.queue2 = queue2;
    }

    @Override
    public void run() {
        while (Main.running) {
            synchronized (queue2) {
                while (queue2.isEmpty()) {
                    try {
                        queue2.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                Integer item = queue2.poll();
                queue2.notifyAll();
                System.out.println("Nechiporchuk take " + item + " from Petrov");
            }
        }

    }
}
