package Lab3.a;

public class TaskA {
    private POT pot;

    public TaskA(Integer N, Integer n){
        this.pot = new POT(N, 0);

        Bee[] beesThreads = new Bee[n];
        for (int i = 0; i < n; i++) {
            beesThreads[i]=new Bee();
            beesThreads[i].setName("bee "+(i+1));
            beesThreads[i].start();
        }

        new Bear().start();
    }

    private class POT {
        private final int volume;
        private int currentVolume;

        public POT(int volume, int currentVolume){
            this.volume = volume;
            this.currentVolume = currentVolume;
        }

        public void fill() {
            if (currentVolume < volume) {
                currentVolume++;
            } else {
                throw new RuntimeException("currentVolume > volume");
            }
        }

        public void empty() {
            currentVolume = 0;
        }
    }


    private class Bee extends Thread {
        Bee(){}

        @Override
        public void run() {
            while (Main.running) {
                synchronized (pot){
                    if (pot.currentVolume >= pot.volume) {
                        try {
                            pot.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        pot.fill();
                        pot.notifyAll();
                        System.out.println("Bee #" + Thread.currentThread().getName() + " produced honey: " + pot.currentVolume);
                    }
                }
            }
        }
    }

    private class Bear extends Thread {
        public Bear(){}

        @Override
        public void run() {
            while (Main.running) {
                synchronized (pot) {
                    if (pot.currentVolume < pot.volume) {
                        try {
                            pot.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        pot.empty();
                        pot.notifyAll();
                        System.out.println("Bear ate all honey!!!");
                    }
                }
            }
        }
    }
}
