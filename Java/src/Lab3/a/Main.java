package Lab3.a;

public class Main {
    public static boolean running = true;
    private static final Integer n = 5;
    private static final Integer N = 100;
    public static void main(String[] args) {
        new TaskA(N,n);

    }

    public static void stop() {
        running = false;
    }
}


