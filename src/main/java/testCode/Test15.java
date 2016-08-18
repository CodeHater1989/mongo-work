package testCode;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test15 {
    public static void main(String[] args) throws InterruptedException {
        IntStream.range(1, 10_0000).forEach(index -> {
            System.out.println(index);
            new Fiber<Object>() {
                @Override
                protected Object run() throws SuspendExecution, InterruptedException {
                    Fiber.currentFiber().sleep(15, TimeUnit.SECONDS);
                    System.out.println(index + "======");
                    return new Object();
                }
            }.start();
        });

        TimeUnit.SECONDS.sleep(1000);
    }
}
