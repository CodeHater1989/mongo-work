package bill_v2;

import Utils.DocumentHandler;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import lombok.Data;
import oracle2Mongo.ProcessCallback;
import oracle2Mongo.RowProcess;
import org.bson.Document;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by wanglei on 16/8/18.
 */
@AllArgsConstructor
@Data
public class MongoStream {
    private RowProcess process;
    private ProcessCallback callback;

    public void processDataStream() {
        MongoClient   mongoClient = new MongoClient("10.117.130.122", 27000);
//        MongoClient   mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("wl_insert");

        MongoCollection mongoCollection = db.getCollection("bill_detail");

        Iterator<Document> iterator= mongoCollection.find().iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            Map<String, Object> row = DocumentHandler.flatDocument(document);

            process.processOneRow(row);
        }

        process.streamArrivalTerminal();
    }
}
