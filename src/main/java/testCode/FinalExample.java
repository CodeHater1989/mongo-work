package testCode;

/**
 * Created by wanglei on 16/8/3.
 */
public class FinalExample {
    int i;
    final int j;
    static FinalExample finalExample;

    public FinalExample() {
        this.i = 1;
        this.j = 2;
    }

    public static void write() {
        finalExample = new FinalExample();
    }

    public static void read() {
        if (null != finalExample) {

            int i = finalExample.i;
            int j = finalExample.j;

            if (finalExample.i != 1) {
                System.out.println("i: " + i);
            }

            if (finalExample.j != 2) {
                System.out.println("j: " + j);
            }
        }
    }
}
