package bill_detail_v2;

import bill_v2.Bill_v2_mongo_process;
import bill_v2.Billv2MongoStream;

import java.io.IOException;

/**
 * Created by wanglei on 16/8/18.
 */
public class Bill_detail_v2_main {
    public static void beginProcess() throws IOException {
        Billv2MongoStream stream = new Billv2MongoStream(new Bill_v2_mongo_process(), () -> {});

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
