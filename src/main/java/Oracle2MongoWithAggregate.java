import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by wanglei on 16/7/25.
 */
public class Oracle2MongoWithAggregate {
    public static void beginTransfer() throws IOException, URISyntaxException {

        File sqlFile = new File(Oracle2MongoWithAggregate.class.getResource("/detail_coll.sql").toURI());
        String sql = Files.toString(sqlFile, Charsets.UTF_8);
        String url = "jdbc:oracle:thin:bmi_nanj/25thibd5@10.117.130.17:1521:ehong";

        OracleStream stream = new OracleStream(url, sql, new AggregateInsertMongo(), () -> {});

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
