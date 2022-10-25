package modul1;


public class Main {
    public static boolean running = true;
    static final int busAmount = 6;
    static final int busStopAmount = 3;
    static final int maxBusOnStopAmount = 2;
    public static void main(String[] args) {
        Route route = new Route(busStopAmount, maxBusOnStopAmount);

        Bus[] busThreads = new Bus[busAmount];
        for (int i = 0; i < 5; i++) {
            busThreads[i]=new Bus(route);
            busThreads[i].setName("#BUS"+(i+1));
            busThreads[i].start();
        }
    }
    public static void stop() {
        running = false;
    }
}
