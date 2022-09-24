package Lab3.b;

import java.util.concurrent.LinkedBlockingDeque;

public class TaskB {
    private static final LinkedBlockingDeque<Integer> clients = new LinkedBlockingDeque<>();

    TaskB(int n){
        Barber barber = new Barber();
        barber.start();

        Client[] clientThreads = new Client[n];
        for (int i = 0; i < n; i++) {
            clientThreads[i]=new Client();
            clientThreads[i].setName("client "+(i+1));
            clientThreads[i].start();
        }

        for (int i = 0; i < n; i++) {
            try {
                clientThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            barber.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private class Barber extends Thread{
        public Barber(){}

        @Override
        public void run() {
            while (Main.running) {
                justSleep();
                System.out.println("Client is waiting");
                clients.add(1);
                justSleep();
            }
        }

        private static void justSleep() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class Client extends Thread {
        public Client(){}
        @Override
        public void run() {
            while (Main.running) {
                if (clients.isEmpty())
                    System.out.println("Barber is sleeping");
                try {
                    clients.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Barber works with " +  Thread.currentThread().getName());
            }
        }
    }



}