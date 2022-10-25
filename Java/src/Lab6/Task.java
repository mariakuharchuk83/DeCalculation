package Lab6;

import java.util.Arrays;
import java.util.Random;

public class Task {

    private static final int[] SIZES = {10, 100, 500, 1000, 1500, 2000, 2500, 3000};
    private Random random;
    private static int[][] a;
    private static int[][] b;

    public Task(){
        random = new Random();
        long start;
        long end;

        for (int size : SIZES) {
            a = new int[size][size];
            b = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    a[i][j] = random.nextInt(10);
                    b[i][j] = random.nextInt(10);
                }
            }

            long[] results = new long[7];
            start = System.currentTimeMillis();
            int[][] simple = simpleMultiplying(a, b);
            end = System.currentTimeMillis();
            results[0] = end - start;
            start = System.currentTimeMillis();
            int[][] tape2 = Tape.multiply(a, b, 2);
            end = System.currentTimeMillis();
            results[1] = end - start;
            start = System.currentTimeMillis();
            int[][] tape4 = Tape.multiply(a, b, 4);
            end = System.currentTimeMillis();
            results[2] = end - start;
            start = System.currentTimeMillis();
            int[][] fox2 = Fox.multiply(a, b, 2);
            end = System.currentTimeMillis();
            results[3] = end - start;
            start = System.currentTimeMillis();
            int[][] fox4 = Fox.multiply(a, b, 4);
            end = System.currentTimeMillis();
            results[4] = end - start;
            start = System.currentTimeMillis();
            int[][] cannon2 = Cannon.multiply(a, b, 2);
            end = System.currentTimeMillis();
            results[5] = end - start;
            start = System.currentTimeMillis();
            int[][] cannon4 = Cannon.multiply(a, b, 4);
            end = System.currentTimeMillis();
            results[6] = end - start;

            System.out.println("Tape: " + Arrays.deepEquals(simple, tape2)
                    + ", Fox: " + Arrays.deepEquals(simple, fox2)
                    + ", Cannon: " + Arrays.deepEquals(simple, cannon2));

            System.out.print(size);
            for (long r : results) {
                System.out.print("\t\t" + r + " ms");
            }
            System.out.println();

        }

    }



        public static int[][] simpleMultiplying(int[][] a, int[][] b){
            int[][] res = new int[a.length][a.length];

            for(int i = 0; i < a.length; i++){
                for(int j = 0; j < a.length; j++){
                    res[i][j] = 0;
                    for(int k = 0; k < a.length; k++){
                        res[i][j] += a[i][k] * b[k][j];
                    }
                }
            }

            return res;
        }


}
