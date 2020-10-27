import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Kiel Dowdle
 *
 * Score 9/9 (100%)
 */
public class MosiacDecorationII {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] line = reader.readLine().split(" ");


        //W,H,A,B,M,C.
        long w = Integer.parseInt(line[0]); //wall width
        long h = Integer.parseInt(line[1]); //wall height
        long a = Integer.parseInt(line[2]); //tile width
        long b = Integer.parseInt(line[3]); //tile height
        long m = Integer.parseInt(line[4]); //10 tiles for
        long c = Integer.parseInt(line[5]); //dollars per cut inch

        //answer is the cost

        long wallArea = w * h;
        long tileArea = a * b;

        long stackArea = tileArea * 10;

        long stacks = wallArea / stackArea;

/*        if (stacks * 10 < wallArea) {
            stacks++;
        }*/

        long fullHTiles = w / a;
        long fullVTiles = h / b;
        long fullTiles = fullHTiles * fullVTiles;



        long hcut = w % a;
        long vcut = h % b;

        long cutTiles = 0;

        long cutDist = 0;
        if (hcut != 0) {
            cutDist += h;
            cutTiles += fullVTiles;
        }
        if (vcut != 0) {
            cutDist += w;
            cutTiles += fullHTiles;
        }


        if (hcut == 0 && vcut == 0){
            if (cutDist > 0) {
                cutTiles++;
            }
        }

        long totalTiles = cutTiles + fullTiles;


        long totalStacks = totalTiles / 10;

        if (totalStacks * 10 < totalTiles) {
            totalStacks++;
        }

        long cutCost = cutDist * c;

        System.out.println((totalStacks * m) + cutCost);


    }
}
