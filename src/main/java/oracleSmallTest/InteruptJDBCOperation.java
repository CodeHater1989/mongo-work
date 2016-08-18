package oracleSmallTest;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanglei on 16/8/5.
 */
public class InteruptJDBCOperation {
    public static void main(String[] args) throws Exception {
        PreparedStatement[] ps = new PreparedStatement[1];
        Thread t = new Thread(() -> {
            try {
                String sql = "SELECT * from JOBS";
                OracleDataSource dataSource = new OracleDataSource();
                dataSource.setURL("jdbc:oracle:thin:system/orcl@10.117.197.48:1521:orcl");
                Connection connection = dataSource.getConnection();
                ps[0] = connection.prepareStatement(sql);

                ps[0].execute();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("报错了!");
            }
        });

        t.start();
        TimeUnit.SECONDS.sleep(3);
        t.interrupt();
//        ps[0].cancel();
    }
}
