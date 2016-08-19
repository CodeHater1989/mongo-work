package oracle2Mongo;

import com.google.common.io.ByteStreams;
import bill_v2.Bill_v2_main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * Created by wanglei on 16/7/25.
 */
public class Oracle2MongoWithAggregate {
    public static void beginTransfer() throws IOException, URISyntaxException {
        String sql = new String(
                ByteStreams.toByteArray(Bill_v2_main.class.getResourceAsStream("/detail_coll.sql")),
                StandardCharsets.UTF_8);

        String url = "jdbc:oracle:thin:bmi_nanj/25thibd5@10.117.130.17:1521:ehong";

        OracleStream stream = new OracleStream(url, sql, new AggregateInsertMongo(), () -> {});

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
