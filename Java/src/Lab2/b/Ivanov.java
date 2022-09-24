package Lab2.b;

import java.util.Queue;

public class Ivanov extends Thread{
    private int detailsNum;
    private int takenItems;
    private final Queue<Integer> queue1;

    public Ivanov(Queue<Integer> queue1, int detailsNum){
        this.queue1 = queue1;
        this.detailsNum = detailsNum;
        this.takenItems = 0;
    }

    @Override
    public void run(){
        boolean finished = false;
        while (Main.running && !finished) {
            synchronized (queue1) {
                while (queue1.size() >= Main.capacity) {
                    try {
                        queue1.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                Integer item = takenItems++;
                queue1.add(item);
                queue1.notifyAll();
                System.out.println("Ivanov take " + item);

                if (item >= detailsNum-1) {
                    finished = true;
                }
            }
        }
    }
}