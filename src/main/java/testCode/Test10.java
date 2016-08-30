package testCode;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test10 {
    public static void main(String[] args) {
        List<String> ss = Lists.newArrayList("1", "1", "3", "4", "5", "1", "2", "3", "4", "5");

        for (int i = 0; i < ss.size(); i++) {
            if (Objects.equals(ss.get(i), "1")) {
//                ss.remove(ss.get(i));
                ss.remove(i);
                i--;
            }
        }

//        Iterator iterator = ss.iterator();
//        while (iterator.hasNext()) {
//            if (Objects.equals(iterator.next(), "1")) {
//                iterator.remove();
//            }
//        }

        System.out.println(ss);

//        for (String s : ss) {
//            if (Objects.equals(s, "1")) {
//                ss.remove(s);
//            }
//        }
    }
}
