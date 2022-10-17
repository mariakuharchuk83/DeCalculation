package modul1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Route {
    private final Semaphore semaphore;
    private final BusStop[] busStops;

    Route(int countOfBusStops, int maxCountOfBusesOnBusStop) {
        this.semaphore = new Semaphore(countOfBusStops);
        this.busStops = new BusStop[countOfBusStops];
        for (int i = 0; i < countOfBusStops; i++) {
            this.busStops[i] = new BusStop(maxCountOfBusesOnBusStop, "#BusStop" + (i+1));
        }
    }

    public void drive() throws InterruptedException {
        String busNumber = Thread.currentThread().getName();
        if (semaphore.tryAcquire(4000, TimeUnit.MILLISECONDS)) {
            for (int i = 0; i < busStops.length; i++) {
                if (busStops[i].currentBusAmount > 0) {
                    busStops[i].currentBusAmount -= 1;
                    System.out.printf(busNumber + " arrived to " + busStops[i].busStopName + "\n");
                    Thread.sleep(3000);
                    busStops[i].currentBusAmount += 1;
                    System.out.printf(busNumber + " left the " + busStops[i].busStopName + "\n");
                } else {
                    System.out.printf(busNumber + " couldn't get on the " + busStops[i].busStopName + "\n");
                }
            }
            semaphore.release();
        }
    }

}
