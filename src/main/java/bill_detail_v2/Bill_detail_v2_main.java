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
                new Document(), new Document().append("ETL_Patient_IDStr", 1).append("PID", 1));

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
