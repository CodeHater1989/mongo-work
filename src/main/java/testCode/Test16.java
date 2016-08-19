package testCode;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test16 {
    public static void main(String[] args) throws Exception {
        Timestamp ts = new Timestamp(1);
        Date d = new Date(1);

        System.out.println(ts);
        System.out.println(d);
        System.out.println(d.equals(ts));
        System.out.println(ts.equals(d));
    }
}
