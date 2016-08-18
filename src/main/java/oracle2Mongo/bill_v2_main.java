package oracle2Mongo;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by wanglei on 16/8/18.
 */
public class Bill_v2_main {
    public static void beginProcess() throws IOException {
        MongoStream stream = new MongoStream(new Bill_v2_mongo(), () -> {});

        new Thread(() -> {stream.processDataStream();}).start();
    }
}
