package oracle2Mongo;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by wanglei on 16/8/18.
 */
public class Bill_v2_main {
    public static void beginProcess() throws IOException {
        String sql = new String(
                ByteStreams.toByteArray(Bill_v2_main.class.getResourceAsStream("/oracle_bill_v2.sql")),
                StandardCharsets.UTF_8);

        String url = "jdbc:oracle:thin:bmi_nanj/25thibd5@10.117.130.17:1521:ehong";

        OracleStream stream = new OracleStream(url, sql, new Bill_v2_mongo(), () -> {});

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
