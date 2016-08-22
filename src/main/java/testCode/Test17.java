package testCode;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test17 {
    public static void main(String[] args) throws Exception {
        Multimap<String, Object> m = ArrayListMultimap.create();
        m.put("1", 1);
        m.put("1", 1);
        m.put("1", 1);

        System.out.println(m);
    }
}
