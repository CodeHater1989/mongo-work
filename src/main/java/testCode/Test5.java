package testCode;

public class Test5 {
    public static void main(String[] args) throws Throwable {
//        useConstantCallSite();
        Runnable r = () -> System.out.println("Hello!");
    }

//    public static void useConstantCallSite() throws Throwable {
//        MethodHandles.Lookup lookup = MethodHandles.lookup();
//        MethodType type = MethodType.methodType(String.class, int.class, int.class); // 参数类型, 返回值类型
//        MethodHandle mh = lookup.findVirtual(String.class, "substring", type);  // 类和方法签名
//
//        ConstantCallSite constantCallSite = new ConstantCallSite(mh);
//        MethodHandle invoker = constantCallSite.dynamicInvoker();
//
//        String result = (String) invoker.invoke("Hello", 2, 3);
//        System.out.println(result);
//    }
}