package bill_v2;

import java.io.IOException;

/**
 * Created by wanglei on 16/8/18.
 */
public class Bill_v2_main {
    public static void beginProcess() throws IOException {
        Billv2MongoStream stream = new Billv2MongoStream(new Bill_v2_mongo_process(), () -> {});

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
