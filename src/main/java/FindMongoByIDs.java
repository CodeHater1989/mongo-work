import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by wanglei on 16/7/26.
 */
public class FindMongoByIDs {
    private static MongoDatabase database;
    private static Set<String> PIDs;
    private static Set<String> ETL_Patient_IDs;
    private static Set<String> Patient_IDs;
    private static String timeFormat = "HH:mm:ss";

    private static final String PID = "PID";
    private static final String ETL_PATIENT_IDSTR = "ETL_Patient_IDStr";
    private static final String PATIENT_IDSTR = "Patient_IDStr";
    private static final String DETAIL_COLL = "detail_coll";
    private static final String BILL_DETAIL = "bill_detail";
    private static final String BILL = "bill";
    //    private static long OUTPUT_TIME_INTERVAL = 120_000;
    private static long OUTPUT_TIME_INTERVAL = 10_000;

    static {
        MongoClient mongoClient = new MongoClient("10.117.130.122", 27000);
        database = mongoClient.getDatabase("wl_insert");

        List<String> _2IDs = null;
        try {
            _2IDs = IOUtils.readLines(FindMongoByIDs.class.getResourceAsStream("/ids.txt"), StandardCharsets.UTF_8);

            Patient_IDs = new HashSet<>(1_0000);
            Patient_IDs.addAll(IOUtils.readLines(FindMongoByIDs.class.getResourceAsStream("/patient_idstrs.txt"), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ETL_Patient_IDs = _2IDs.stream().map(line -> line.split(",")[0]).collect(Collectors.toSet());
        PIDs = _2IDs.stream().map(line -> line.split(",")[1]).collect(Collectors.toSet());
    }

    public static void queryMongoByIDs(String collectionName, String fieldName) {
        MongoCollection collection = database.getCollection(collectionName);

        String statFileName = Thread.currentThread().getName() + "#" + collectionName + "#" + fieldName + ".txt";
        File statFile = new File(statFileName);

        if (statFile.exists()) statFile.delete();

        Set idList = null;
        switch (fieldName) {
            case PID:
                idList = PIDs;
                break;
            case ETL_PATIENT_IDSTR:
                idList = ETL_Patient_IDs;
                break;
            case PATIENT_IDSTR:
                idList = Patient_IDs;
                break;
            default:
                idList = new HashSet();
        }

        DateTime beginTime = new DateTime();
        long[] counter = new long[]{0};
        StringBuilder queryInfo = new StringBuilder();
        int[] outputTimes = new int[]{0};
        idList.stream().forEach(id -> {
            collection.find(new BasicDBObject().append(fieldName, id)).forEach((Block) document -> {
            });

            ++counter[0];
            long period = System.currentTimeMillis() - beginTime.getMillis();
            if (period > OUTPUT_TIME_INTERVAL * (outputTimes[0] + 1)) {
                outputTimes[0]++;
                long elapsedTime = (new DateTime().getMillis() - beginTime.getMillis()) / 1000;
                double querySpeed = counter[0] / (elapsedTime * 1.0);
                String info = String.format("在线程%10s中用字段%18s遍历查询表%12s, 已遍历%5d条, 用时: %5d秒, 查询速度: %6.2f条/秒\n",
                        Thread.currentThread().getName(), fieldName, collectionName, counter[0], elapsedTime, querySpeed);
                queryInfo.append(info);
                System.out.print(info);
            }
        });
        DateTime endTime = new DateTime();
        long costTime = (endTime.getMillis() - beginTime.getMillis()) / 1000;

        queryInfo.append("开始时间" + beginTime.toString(timeFormat) + ", 结束时间" + endTime.toString(timeFormat) + "。执行共用时: " + costTime);
        try {
            Files.append(queryInfo.toString(), statFile, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test() {
//        IntStream.range(0, 5).forEach(action -> new Thread(() -> queryMongoByIDs(DETAIL_COLL, PID)).start());
//        IntStream.range(0, 5).forEach(action -> new Thread(() -> queryMongoByIDs(DETAIL_COLL, ETL_PATIENT_IDSTR)).start());
//        IntStream.range(0, 5).forEach(action -> new Thread(() -> queryMongoByIDs(BILL_DETAIL, PID)).start());
//        IntStream.range(0, 5).forEach(action -> new Thread(() -> queryMongoByIDs(BILL_DETAIL, ETL_PATIENT_IDSTR)).start());
        IntStream.range(0, 1).forEach(action -> new Thread(() -> queryMongoByIDs(BILL, PATIENT_IDSTR)).start());
    }
}
