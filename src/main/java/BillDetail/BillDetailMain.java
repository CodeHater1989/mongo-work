package BillDetail;

import Bill.SimpleMongoProcess2;
import com.google.common.io.ByteStreams;
import oracle2Mongo.OracleStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by wanglei on 16/8/22.
 */
public class BillDetailMain {
    public static void processData() throws IOException {
        String sql = new String(
                ByteStreams.toByteArray(BillDetailMain.class.getResourceAsStream("/bill_detail_oracle.sql")),
                StandardCharsets.UTF_8);

        String url = "jdbc:oracle:thin:bmi_nanj/25thibd5@10.117.130.17:1521:ehong";

        OracleStream stream = new OracleStream(url, sql, new SimpleMongoProcess2("bill_detail", "bill_detail.txt"), () -> {});

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
