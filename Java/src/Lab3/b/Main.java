package Lab3.b;

public class Main {
    public static boolean running = true;
    private static final Integer n = 5;
    public static void main(String[] args) {
        new TaskB(n);
    }
    public static void stop() {
        running = false;
    }
}
