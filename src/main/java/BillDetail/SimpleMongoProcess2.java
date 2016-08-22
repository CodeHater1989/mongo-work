package BillDetail;

import com.google.common.io.Files;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import oracle2Mongo.RowProcess;
import org.bson.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglei on 16/8/22.
 */
public class SimpleMongoProcess2 implements RowProcess{
    private static final int             BATCH_SIZE      = 10_0000;
    private              MongoCollection mongoCollection;
    private              List<Document>  documentsCache  = new ArrayList<>(BATCH_SIZE);
    private              Date            beginDate;
    private              File            logFile;
    private              int             counter         =       0;

    public SimpleMongoProcess2(String collectionName, String logFileName) {
        MongoClient mongoClient = new MongoClient("10.117.130.122", 27000);
//        MongoClient   mongoClient = new MongoClient();
        MongoDatabase db          = mongoClient.getDatabase("wl_insert");

        mongoCollection = db.getCollection(collectionName);

        beginDate       = new Date();
        logFile         = new File(logFileName);

        if (logFile.exists()) {
            logFile.delete();
        }
    }

    private void batchInsert() {
        mongoCollection.insertMany(documentsCache);
        documentsCache.clear();
    }

    @Override
    public void processOneRow(Map<String, Object> rowMap) {
        Document newDocument = new Document();

        documentsCache.add(newDocument);

        if (++counter % BATCH_SIZE == 0) {
            batchInsert();

            try {
                Files.append("", logFile, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void streamArrivalTerminal() {
        batchInsert();
    }
}
