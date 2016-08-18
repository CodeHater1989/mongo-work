package testCode;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class TestMain {

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Foo instance = new Foo();
        //搜索函数句柄
        MethodType methodType = MethodType.methodType(void.class);
        MethodHandle methodHandle = lookup.findVirtual(Foo.class, "doSomething", methodType);
        methodHandle.invoke(instance);

        //解除反射检测,使用invokedynamic命令直接执行
        Method method = Foo.class.getDeclaredMethod("doSomething");
        methodHandle = lookup.unreflect(method);
        int testTimes = 1_0000_0000;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            methodHandle.invokeExact(instance);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("句柄调用:" + (t2 - t1));
        t1 = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            method.invoke(instance);
        }
        t2 = System.currentTimeMillis();
        System.out.println("反射调用:" + (t2 - t1));

        t1 = System.currentTimeMillis();
        for (int i = 0; i < testTimes; i++) {
            instance.doSomething();
        }
        t2 = System.currentTimeMillis();
        System.out.println("直接调用:" + (t2 - t1));

    }

}
