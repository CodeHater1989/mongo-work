package oracleSmallTest;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by wanglei on 16/8/5.
 */
public class LastOne {
    public static void main(String[] args) throws Exception {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL("jdbc:oracle:thin:hr/hr@10.117.197.48:1521:orcl");

        String sql = "SELECT * from JOBS";
        Connection connection = dataSource.getConnection();
        Statement ps = connection .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = ps.executeQuery(sql);

        int counter = 0;
        while (rs.next()) {
            counter++;

            if (rs.isLast()) {
                System.out.println("表中共" + counter + "条数据");
            }
        }
    }
}
