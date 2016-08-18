package testCode;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Data;

import java.util.*;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test13 {
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        new Test13().testIterator();
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        new Test13().testIterator2();
        System.out.println(System.currentTimeMillis() - begin);
    }

    public void testIterator() {
        TIntObjectMap map = new TIntObjectHashMap();

        for (int i = 0; i < 1000; i++) {
            Game g = new Game();
            g.setName("最终幻想" + i);
            g.setSize(15000 + (i << 3));
            g.setCtDate(new Date());
            map.put(i, g);
        }

        int size = map.size();
        TIntObjectIterator it = map.iterator();
        for (int i = size; i > 0; i--) {
            it.advance();
//            System.out.println(it.key() + "=" + it.value());

            if (it.key() == 3) {
                Game g = new Game();
                g.setName("最终幻想13");
                g.setSize(15000 + (i << 3));
                g.setCtDate(new Date());
                it.setValue(g);
            }
        }

//        System.out.println("=======================================");
//        System.out.println(map.get(3));
    }

    public void testIterator2() {
        Map map = new HashMap();

        for (int i = 0; i < 1000; i++) {
            Game g = new Game();
            g.setName("最终幻想" + i);
            g.setSize(15000 + (i << 3));
            g.setCtDate(new Date());
            map.put(i, g);
        }

        Set set = map.entrySet();
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            Map.Entry e = (Map.Entry) it.next();
//            System.out.println(e.getKey() + "=" + e.getValue());

            if (((Integer) e.getKey()).intValue() == 3) {
                Game g = new Game();
                g.setName("最终幻想13");
                g.setSize(18000);
                g.setCtDate(new Date());
                e.setValue(g);
            }
        }

//        System.out.println("=======================================");
//        System.out.println(map.get(3));
    }

    @Data
    private class Game {
        private String name;
        private int size;
        private Date ctDate;
    }
}
