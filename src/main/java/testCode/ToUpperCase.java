package testCode;

import java.lang.invoke.*;

public class ToUpperCase {
    public static CallSite bootstrap(MethodHandles.Lookup lookup, String methodName, MethodType type, Object caller)
            throws NoSuchMethodException, IllegalAccessException {
        MethodHandle mh = lookup.findVirtual(String.class, methodName, type).bindTo(caller);
        return new ConstantCallSite(mh);
    }
}
