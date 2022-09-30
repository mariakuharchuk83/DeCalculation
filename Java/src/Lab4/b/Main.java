package Lab4.b;


import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static boolean running = true;
    public static void main(String[] args) {
        var garden = new Garden(10,10);
        var lock = new ReentrantReadWriteLock();

        new Thread(new Nature(garden, lock), "Nature").start();
        new Thread(new Gardener(garden, lock), "Gardener").start();
        new Thread(new FileEditor(garden, lock), "File").start();
        new Thread(new ConsoleEditor(garden, lock), "Console").start();

    }
}
