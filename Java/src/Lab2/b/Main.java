package Lab2.b;

import java.util.LinkedList;
import java.util.Queue;
public class Main {
    public static int capacity = 5;
    public static boolean running = true;
    private static Queue<Integer> queue1;
    private static Queue<Integer> queue2;



    public static void main(String[] args) {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
        int detailsNum = 50;

        new Ivanov(queue1, detailsNum).start();
        new Petrov(queue1,queue2).start();
        new Nechiporchuk(queue2).start();
    }

    public static void stop() {
        running = false;
    }
}
