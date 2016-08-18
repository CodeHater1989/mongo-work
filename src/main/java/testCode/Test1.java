package testCode;

import java.util.stream.IntStream;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test1 {
    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        spendTime();
        long end = System.currentTimeMillis();

        System.out.println((end - start) + "ms");
    }

    public static void spendTime() {
        IntStream.range(0, 5_0000_0000).forEach(index -> {});
    }
}
