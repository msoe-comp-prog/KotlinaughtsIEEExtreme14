import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kiel Dowdle, Nicholas Johnson
 *
 * Score 18/18 (100%)
 */
public class Main {

    static List<Integer> emptyRule = new ArrayList<>();
    static List<Integer> liveRule = new ArrayList<>();

    static int[][] currentBoard;
    static int[][] nextBoard;
    static int size;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] line = reader.readLine().split(";");

        String emptyRuleString = line[0];
        String liveRuleString = line[1];

        for (int i = 0; i < 5; i++) {
            if (emptyRuleString.charAt(i) == '1') {
                emptyRule.add(i);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (liveRuleString.charAt(i) == '1') {
                liveRule.add(i);
            }
        }

        line = reader.readLine().split(" ");

        size = Integer.parseInt(line[0]);
        int updates = Integer.parseInt(line[1]);

        currentBoard = new int[size][size];
        nextBoard = new int[size][size];



        for (int i = 0; i < size; i++) {
            currentBoard[i] = reader.readLine().chars().map(x -> x - '0').toArray();
            nextBoard[i] = Arrays.copyOf(currentBoard[i], size);
        }

        for (int counter = 0; counter < updates; counter++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    update(i, j);
                }
                //System.out.println(Arrays.toString(nextBoard[i]));
            }
            //System.out.println();
            for (int i =0; i < size; i++) {
                currentBoard[i] = Arrays.copyOf(nextBoard[i], size);
            }
        }



        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(currentBoard[i][j]);
            }
            sb.append('\n');
        }

        System.out.println(sb.toString());

    }

    private static void update(int i, int j) {
        int n = aliveNeighbors(i, j);

        if (currentBoard[i][j] == 0) {
            if (emptyRule.contains(n)) {
                nextBoard[i][j] = 1;
            }
        } else {
            if (!liveRule.contains(n)) {
                nextBoard[i][j] = 0;
            }
        }
    }

    private static int aliveNeighbors(int i, int j) {
        int n = 0;
        
        if (currentBoard[(i + 1) % size][j] == 1) n++;
        if (currentBoard[(i - 1 + size) % size][j] == 1) n++;
        if (currentBoard[i][(j + 1) % size] == 1) n++;
        if (currentBoard[i][(j - 1 + size) % size] == 1) n++;

        return n;
    }
}

