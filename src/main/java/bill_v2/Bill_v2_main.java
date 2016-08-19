package bill_v2;

import org.bson.Document;

import java.io.IOException;

/**
 * Created by wanglei on 16/8/18.
 */
public class Bill_v2_main {
    public static void beginProcess() throws IOException {
        MongoStream stream = new MongoStream(new Bill_v2_mongo_process(), () -> {}, new Document(), new Document());

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
