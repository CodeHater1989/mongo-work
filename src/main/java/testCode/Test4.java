package testCode;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;

public class Test4 {
    private Object o;

    private void printArgsSpecial(Object... objArr) {
        System.out.println(Arrays.toString(objArr));
    }

    public static void printArgsStatic(Object... objArr) {
        System.out.println(Arrays.toString(objArr));
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // 寻找static方法，方法为
        // static printArgsStatic([Ljava/lang/Object)V
        MethodHandle mh = lookup.findStatic(Test4.class, "printArgsStatic", MethodType.methodType(void.class, Object[].class));

        // 转换方法签名后执行
        mh.asType(MethodType.methodType(void.class, Integer.class, String.class, Double.class)).invoke(1, "a", 3.14);

        // 寻找非static方法
        // printArgsSpecial([Ljava/lang/Object)V
        MethodHandle mh2 = lookup.findSpecial(Test4.class, "printArgsSpecial", MethodType.methodType(void.class, Object[].class), Test4.class);

        // 转换方法签名后执行
        // 由于是非static，所以需要增加一个方法参数，指明在哪个实例上调用
        mh2.asType(MethodType.methodType(void.class, Test4.class, Integer.class, String.class, Double.class)).invoke(new Test4(), 1, "a", 3.14);

        // 换种方法执行
        // 这里使用bindTo而不是直接invoke。两者转为字节码的话没任何区别
        // 都是对象引用入栈，参数入栈，最后执行invokespecial
        mh2.asType(MethodType.methodType(void.class, Test4.class, Integer.class, String.class, Double.class)).bindTo(new Test4()).invoke(1, "a", 3.14);
    }
}