package modul1;

public class Bus extends Thread {

    private final Route route;

    public Bus(Route route){
        this.route = route;
    }

    @Override
    public void run() {
        while(Main.running){
            try {
                route.drive();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}