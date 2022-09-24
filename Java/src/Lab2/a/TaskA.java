package Lab2.a;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskA {
    private boolean[][] forest;
    private final AtomicBoolean WinnieIsFound;
    private final AtomicInteger curRow;
    private final Integer forestSize;
    private final Integer beesCount;
    private static final Random random = new Random();
    //private Thread[] threads;
    private class BeeSwarm extends Thread {
        public BeeSwarm() {
        }

        @Override
        public void run() {
            while (!WinnieIsFound.get() && curRow.get() < forestSize) {
                checkRow(curRow.get());
                curRow.set(curRow.get() + 1);
            }
        }
    }

    public TaskA(Integer forestSize, Integer beesCount) {
        this.forestSize = forestSize;
        this.beesCount = beesCount;
        forest = createForest();
        WinnieIsFound = new AtomicBoolean(false);
        curRow = new AtomicInteger(0);
    }

    private boolean[][] createForest(){
        forest = new boolean[forestSize][forestSize];
        int winnieRow = random.nextInt(forestSize);
        int winnieCol = random.nextInt(forestSize);
        forest[winnieRow][winnieCol] = true;
        System.out.println("Vinnie is in: [" + winnieRow + "][" + winnieCol+ "]");
        return forest;
    }

    public void startSearch() {
        BeeSwarm[] beesThreads = new BeeSwarm[beesCount];
        for (int i = 0; i < beesCount; i++) {
            beesThreads[i]=new BeeSwarm();
            beesThreads[i].setName("swarm "+(i+1));
            beesThreads[i].start();
        }
        for(int i = 0; i< beesCount; i++){
            try{
                beesThreads[i].join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    private void checkRow(int row) {
        if (WinnieIsFound.get()) {
            return;
        }
        System.out.println(Thread.currentThread().getName() + " bee swarm  in row " + row);
        for (int i = 0; i < forestSize; i++) {
            if (forest[row][i]) {
                System.out.println(Thread.currentThread().getName() + " Vinnie was founded in row " + row);
                WinnieIsFound.set(true);
                break;
            }
        }
    }
}




