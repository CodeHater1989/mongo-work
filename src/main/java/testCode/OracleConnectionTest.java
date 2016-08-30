package testCode;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by wanglei on 16/7/25.
 */
public class OracleConnectionTest {

    public static void main(String[] args) throws Exception {
        OracleDataSource dataSource = new OracleDataSource();
//        dataSource.setURL("jdbc:oracle:thin:bmi_nanj/25thibd5@10.117.130.17:1521:ehong");
        dataSource.setURL("jdbc:oracle:thin:hr/hr@10.117.197.48:1521:orcl");
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE y SET y = 12 WHERE y = ?");
//        PreparedStatement ps = connection.prepareStatement("INSERT INTO y VALUES (?)");

//        ps.setObject(1, "a", JDBCType.NVARCHAR);
//        ps.setNString(1, "a");
//        ps.setObject(1, "a", Types.CHAR);
//        ps.setObject(1, "yyyy");
//        ps.setString(1, "fffff");
//        TypeHandler typeHandler = new TypeHandlerRegistry().getTypeHandler(JdbcType.LONGVARCHAR);
//        typeHandler.setParameter(ps, 1, "fffff", JdbcType.LONGVARCHAR);
        ps.setObject(1, Integer.valueOf(1));
//        ps.setInt(1, 1);
        int count = ps.executeUpdate();
        System.out.println(count);
    }
}
