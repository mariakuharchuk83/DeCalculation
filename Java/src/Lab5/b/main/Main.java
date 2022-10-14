package Lab5.b.main;

import Lab5.b.Task.StringHandler.StringWorker;
import Lab5.b.Task.Task;

import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static boolean running = true;
    public static void main(String[] args) {
        var stringWorkers = new StringWorker[]{
                new StringWorker(5),
                new StringWorker(5),
                new StringWorker(5),
                new StringWorker(5)
        };

        new Task(stringWorkers);
    }
    public static void stop() {
        running = false;
    }
}