package testCode;

import java.lang.invoke.*;

public class Test6 {
    public static void main(String[] args) throws Throwable {
        useMutableCallSite();
    }

    public static void useMutableCallSite() throws Throwable {
        MethodType methodType = MethodType.methodType(int.class, int.class, int.class);
        MutableCallSite callSite = new MutableCallSite(methodType);

        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle max = lookup.findStatic(Math.class, "max", methodType);
        MethodHandle min = lookup.findStatic(Math.class, "min", methodType);

        callSite.setTarget(max);
        MethodHandle invoker = callSite.dynamicInvoker();
        int result = (int) invoker.invoke(3, 5);
        System.out.println(result);

        callSite.setTarget(min);
        invoker = callSite.dynamicInvoker();
        System.out.println(invoker.invoke(3, 5));

        MutableCallSite.syncAll(new MutableCallSite[]{callSite});
    }
}