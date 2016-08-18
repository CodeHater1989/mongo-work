package testCode;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.primitives.Ints;

import java.util.List;

public class Test7 {
    public static void main(String[] args) throws Throwable {
        FluentIterable<Integer> integers = FluentIterable.from(Ints.asList(1, 2, 3, 4, 5));

        List<Integer> ints = integers.transform(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return input * 2;
            }
        }).toList();

        System.out.println(ints);
    }
}
