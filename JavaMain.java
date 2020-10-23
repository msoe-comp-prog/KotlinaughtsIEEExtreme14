import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaMain {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

    }


    private static final int SPACE_INT = ' ';
    private static final int ZERO_INT = '0';
    private static final int NL_INT = '\n';

    @SuppressWarnings("DuplicatedCode")
    private static int readInt() throws IOException {
        int ret = br.read();
        while (ret <= SPACE_INT) {
            ret = br.read();
        }
        final boolean neg = ret == '-';
        if (neg) {
            ret = br.read();
        }
        ret -= ZERO_INT;
        int read = br.read();
        while (read >= ZERO_INT) {
            ret *= 10;
            ret += read - ZERO_INT;
            read = br.read();
        }
        while (read <= SPACE_INT && read != -1 && read != NL_INT) {
            br.mark(1);
            read = br.read();
        }
        if (read > SPACE_INT) {
            br.reset();
        }
        return neg ? -ret : ret;
    }

    @SuppressWarnings("DuplicatedCode")
    private static long readLong() throws IOException {
        long ret = br.read();
        while (ret <= SPACE_INT) {
            ret = br.read();
        }
        final boolean neg = ret == '-';
        if (neg) {
            ret = br.read();
        }
        ret -= ZERO_INT;
        int read = br.read();
        while (read >= ZERO_INT) {
            ret *= 10;
            ret += read - ZERO_INT;
            read = br.read();
        }
        while (read <= SPACE_INT && read != -1 && read != NL_INT) {
            br.mark(1);
            read = br.read();
        }
        if (read > SPACE_INT) {
            br.reset();
        }
        return neg ? -ret : ret;
    }

    @SuppressWarnings("DuplicatedCode")
    private static String readWord() throws IOException {
        int ret = br.read();
        while (ret <= SPACE_INT && ret != -1) {
            ret = br.read();
        }
        if (ret == -1) {
            return "";
        }
        char[] cb = new char[32];
        int idx = 0;
        while (ret > SPACE_INT) {
            if (idx == cb.length) {
                char[] ncb = new char[cb.length * 2];
                System.arraycopy(cb, 0, ncb, 0, cb.length);
                cb = ncb;
            }
            cb[idx++] = (char)ret;
            ret = br.read();
        }
        while (ret <= SPACE_INT && ret != -1 && ret != NL_INT) {
            br.mark(1);
            ret = br.read();
        }
        if (ret > SPACE_INT) {
            br.reset();
        }
        return new String(cb, 0, idx);
    }
}
