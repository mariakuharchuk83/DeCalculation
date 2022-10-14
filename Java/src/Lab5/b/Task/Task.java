package Lab5.b.Task;

import Lab5.b.Task.StringHandler.StringWorker;
import Lab5.b.main.Main;

import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

public class Task {
    private Thread[] threads;
    public Task(StringWorker[] stringWorkers){
        this.threads = Arrays.stream(stringWorkers).map(Thread::new).toArray(Thread[]::new);

        var barrier = new CyclicBarrier(4, () -> {
            try {
                Thread.sleep(200);
                System.out.println();

                int[] counters = Arrays.stream(stringWorkers).mapToInt(StringWorker::getCounter).toArray();
                for (int i = 0; i < stringWorkers.length; i++) {
                    System.out.println("StringWorker - " + i + ": " + stringWorkers[i].getString() + ", has A,B count: " + counters[i]);
                }

                if (((counters[0] == counters[1]) || (counters[2] == counters[3])) && ((counters[0] == counters[2]) || (counters[1] == counters[3]))) {
                    Main.stop();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        for (var stringWorker : stringWorkers) {
            stringWorker.setBarrier(barrier);
        }

        for (var thread : threads) {
            thread.start();
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
