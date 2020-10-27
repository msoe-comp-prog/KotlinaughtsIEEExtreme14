import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Kiel Dowdle
 *
 * Score 9/9 (100%)
 */
public class HotelWiring {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        long cases = Integer.parseInt(reader.readLine());

        for (int c = 0; c < cases; c++) {
            //nmk

            String[] line = reader.readLine().split(" ");

            int floors = Integer.parseInt(line[0]);
            long rooms = Integer.parseInt(line[1]);
            long switches = Integer.parseInt(line[2]);

            long[] correct = new long[floors];


            for (int f = 0; f < floors; f++) {
                correct[f] = Integer.parseInt(reader.readLine());
            }

            Arrays.sort(correct);

            long withPower = 0;

            int currentIndex = 0;

            for (int i = 0; i < switches; i++) {
                withPower += rooms - correct[i];
                currentIndex++;
            }
            for (int i = currentIndex; i < correct.length; i++) {
                withPower += correct[i];
            }


            System.out.println(withPower);

        }
    }
}
