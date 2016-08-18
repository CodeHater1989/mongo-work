import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by wanglei on 16/7/25.
 */
public class MongoSelectTest {
    public static void beginTest() {
        MongoClient mongoClient = new MongoClient("10.117.130.122", 27000);
        MongoDatabase database = mongoClient.getDatabase("wl_insert");
        MongoCollection<Document> collection = database.getCollection("bill_detail2");

        collection.find().forEach(new Block<Document>() {
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
    }
}
