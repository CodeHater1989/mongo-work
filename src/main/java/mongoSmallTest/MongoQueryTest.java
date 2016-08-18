package mongoSmallTest;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wanglei on 16/7/25.
 */
public class MongoQueryTest implements Runnable {
    @Override
    public void run() {
        MongoClient mongoClient = new MongoClient("10.117.130.122", 27000);
        MongoDatabase database = mongoClient.getDatabase("wl_insert");
        MongoCollection<Document> collection = database.getCollection("bill");

        BasicDBObject queryObject = new BasicDBObject();
        queryObject.append("Patient_IDStr", "0000614013");

        Date beginTime = new Date();

        collection.find(queryObject).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });

        System.out.println((new Date().getTime() - beginTime.getTime())/ 1000.0);
    }

    public static void beginTest() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new MongoQueryTest());

        executorService.shutdown();
    }
}
