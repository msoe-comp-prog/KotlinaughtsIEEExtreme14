//kiel

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class mosaicDecoration {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] line = reader.readLine().split(" ");

        long n = Integer.parseInt(line[0]);
        long b = Integer.parseInt(line[1]);
        long p = Integer.parseInt(line[2]);
        long bstacks = 0;
        long pstacks = 0;

        long totalb = 0;
        long totalp = 0;

        for (int c = 0; c < n; c++) {
            line = reader.readLine().split(" ");

            long tb = Integer.parseInt(line[0]);
            long tp = Integer.parseInt(line[1]);

            totalb += tb;
            totalp += tp;
        }

        bstacks = totalb / 10;

        if (bstacks * 10 < totalb) {
            bstacks++;
        }

        pstacks = totalp / 10;

        if (pstacks * 10 < totalp) {
            pstacks++;
        }

        System.out.println(bstacks * b + pstacks * p);
    }
}
