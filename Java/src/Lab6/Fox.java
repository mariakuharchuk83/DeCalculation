package Lab6;

public class Fox {
    private static int[][] a;
    private static int[][] b;
    private static int process_amount;
    private static int[][] res;

    public static int[][] multiply(int[][] a, int[][] b, int process_amount){
        Fox.a = a;
        Fox.b = b;
        Fox.process_amount = process_amount;

        res = new int[a.length][a.length];

        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length; j++){
                res[i][j] = 0;
            }
        }

        Thread[] threads = new Thread[process_amount];
        for(int i = 0; i < threads.length; i++){
            threads[i] = new Thread(new Calculate(i));
        }

        for (Thread task : threads) {
            task.start();

        }

        for (Thread task : threads) {
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    private record Calculate(int part_index) implements Runnable {

        @Override
            public void run() {

                int pivot = (int) Math.ceil(a.length / (double) process_amount);
                for (int row = part_index * pivot; row < (part_index + 1) * pivot && row < a.length; row++) {
                    int counter = 0;
                    int b_i = row;
                    int a_j = row;
                    while (counter < a.length) {
                        for (int i = 0; i < a.length; i++) {
                            res[row][i] += a[row][a_j] * b[b_i][i];
                        }

                        b_i = (b_i + 1) % a.length;
                        a_j = (a_j + 1) % a.length;
                        counter++;
                    }
                }
            }
        }

}
