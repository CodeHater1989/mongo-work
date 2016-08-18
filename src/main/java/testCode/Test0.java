package testCode;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test0 {
    public static void main(String[] args) {
        Greeting greeting = new GreetingInChinese();
        Greeting[] greetingProxy = new Greeting[1];
        greetingProxy[0] = (Greeting) Proxy.newProxyInstance(
                greeting.getClass().getClassLoader(),
                greeting.getClass().getInterfaces(),
                (proxy, method, argsArray) -> {
                    System.out.println(greetingProxy[0] == proxy);
                    method.invoke(greeting, argsArray);
                    return null;
                });

        greetingProxy[0].sayHello();

        Greeting cgGreeting = (Greeting) Enhancer.create(GreetingInChinese.class, (MethodInterceptor) (target, method, argsArray, proxy) -> {
            System.out.println("cglib生成的代理。");
            proxy.invokeSuper(target, argsArray);
            return null;
        });

        System.out.println(cgGreeting.getClass().getName());
        cgGreeting.sayHello();
    }
}
