package testCode;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test3 {
    public static void main(String[] args) throws Exception {
        List<String> parameters = ManagementFactory.getRuntimeMXBean().getInputArguments();

        System.out.println(Joiner.on("\n").join(parameters));
//        System.out.println(Joiner.on(":").join(parameters));
    }
}
