package bill_detail_v2;

import Utils.TypeHandler;
import com.mongodb.BasicDBList;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import oracle2Mongo.RowProcess;
import org.bson.Document;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglei on 16/8/18.
 */
public class BillDetailv2MongoProcess implements RowProcess {

    public BillDetailv2MongoProcess() {
    }

    @Override
    public void processOneRow(Map<String, Object> rowMap) {
    }

    private void insertBatch() {
    }

    @Override
    public void streamArrivalTerminal() {
        insertBatch();
    }
}
