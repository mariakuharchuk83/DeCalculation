package Lab5.b.Task.StringHandler;

import Lab5.b.main.Main;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class StringWorker implements Runnable {
    private StringBuilder str;
    private CyclicBarrier barrier;
    private int abCounter;

    public StringWorker(int length) {
        this.abCounter = 0;

        this.str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double rand = Math.random();
            str.append((rand < 0.25) ? "A" : ((rand < 0.5) ? "B" : ((rand < 0.75) ? "C" : "D")));
        }
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public int getCounter() {
        return abCounter;
    }

    public String getString() {
        return str.toString();
    }

    @Override
    public void run() {
        try {
            while (Main.running) {
                abCounter = 0;

                for (int i = 0; i < str.length(); i++) {
                    if (Math.random() < 0.5) {
                        str.setCharAt(i, switch (str.charAt(i)) {
                            case 'A' -> 'C';
                            case 'C' -> 'A';
                            case 'B' -> 'D';
                            case 'D' -> 'B';
                            default -> ' ';
                        });
                    }
                    if (str.charAt(i) == 'A' || str.charAt(i) == 'B') {
                        abCounter++;
                    }
                }
                barrier.await();
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}