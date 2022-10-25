package Lab6;

public class Tape {
    private static int[][] a;
    private static int[][] b;
    private static int process_amount;
    private static int[][] res;

    public static int[][] multiply(int[][] a, int[][] b, int process_amount){
        Tape.a = a;
        Tape.b = b;
        Tape.process_amount = process_amount;

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

        for(int i = 0; i < threads.length; i++){
            threads[i].start();

        }

        for(int i = 0; i < threads.length; i++){
            try {
                threads[i].join();
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
                    int index = row;
                    while (counter < a.length) {
                        int cell = 0;
                        for (int i = 0; i < a.length; i++) {
                            cell += a[row][i] * b[i][index];
                        }

                        res[row][index] = cell;
                        counter++;
                        index = (index + 1) % a.length;
                    }
                }
            }
        }

}
