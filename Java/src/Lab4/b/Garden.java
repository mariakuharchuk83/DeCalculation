package Lab4.b;

import java.util.Random;

public class Garden {
    // "✓" - super
    // "~" - good
    // "×" - bad
    private final String[][] condition;


    public Garden(int sizeX, int sizeY) {
        condition = new String[sizeX][sizeY];
        for (var section : condition) {
            for (int y = 0; y < sizeY(); y++) {
                section[y] = "✓";
            }
        }
    }

    public int sizeX() {
        return condition.length;
    }

    public int sizeY() {
        return condition[0].length;
    }

    public void evolution() {
        for (var section : condition) {
            for (int y = 0; y < sizeY(); y++) {
                if(Math.random() > 0.6){
                    if(section[y] == "✓"){
                        section[y] = "~";
                    } else{
                        section[y] = "×";
                    }
                }
            }
        }
    }

    public void fix() {
        for (var section : condition) {
            for (int y = 0; y < sizeY(); y++) {
                if (section[y] == "×") {
                    section[y] = "✓";
                }
            }
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int y = 0; y < sizeY(); y++) {
            for (int x = 0; x < sizeX(); x++) {
                str.append(" ").append(condition[x][y]).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}
