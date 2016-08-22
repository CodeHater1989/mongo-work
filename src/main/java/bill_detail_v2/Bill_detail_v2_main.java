package bill_detail_v2;

import bill_v2.MongoStream;
import org.bson.Document;

import java.io.IOException;

/**
 * Created by wanglei on 16/8/18.
 */
public class Bill_detail_v2_main {
    public static void beginProcess() throws IOException {
        MongoStream stream = new MongoStream(new BillDetailv2MongoProcess(), () -> {},
//                new Document().append("PID", "H0004201311290107").append("ETL_Patient_IDStr", "1234"),
                new Document(),
                new Document().append("PID", 1).append("ETL_Patient_IDStr", 1));

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
