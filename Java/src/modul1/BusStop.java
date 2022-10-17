package modul1;

import java.util.concurrent.Semaphore;

public class BusStop {
    public int currentBusAmount;
    public String busStopName;

    public BusStop(int currentBusAmount, String name){
        this.currentBusAmount = currentBusAmount;
        this.busStopName = name;
    }
}