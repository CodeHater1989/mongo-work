package testCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test2 {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        List<String> list = new ArrayList<>();
        IntStream.iterate(0, i -> i + 1).limit(1000_000_000).forEach(i -> list.add(i + ""));

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}
