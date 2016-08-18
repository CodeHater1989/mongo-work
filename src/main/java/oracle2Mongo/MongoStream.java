package oracle2Mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wanglei on 16/8/18.
 */
@AllArgsConstructor
@Data
public class MongoStream {
    private RowProcess      process;
    private ProcessCallback callback;

    public void processDataStream() {
        MongoClient   mongoClient = new MongoClient("10.117.130.122", 27000);
//        MongoClient   mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("wl_insert");

        MongoCollection mongoCollection = db.getCollection("bill");

        Iterator<Document> iterator= mongoCollection.find().iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            Map<String, Object> row = new HashMap<>();
            for (Map.Entry<String, Object> entry : document.entrySet()) {
                row.put(entry.getKey(), entry.getValue());
            }

            process.processOneRow(row);
        }

        process.streamArrivalTerminal();
    }
}
