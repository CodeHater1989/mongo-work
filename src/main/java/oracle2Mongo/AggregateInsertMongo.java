package oracle2Mongo;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglei on 16/7/25.
 */
public class AggregateInsertMongo implements RowProcess {

    private        int                               counter;
    private        MongoCollection                   mongoCollection;
    private        Date                              beginDate;
    private        File                              logFile;
    private static int                               BATCH_SIZE      = 1_0000;
    private        Multimap<MongoKey, BasicDBObject> mingxiMap       = HashMultimap.create();
//    private        Map<MongoKey, Integer>            mingxiCounter   = new HashMap<>();

    public AggregateInsertMongo() {
//        MongoClient   mongoClient = new MongoClient("10.117.130.122", 27000);
        MongoClient   mongoClient = new MongoClient();
        MongoDatabase db          = mongoClient.getDatabase("wl_insert");

        mongoCollection = db.getCollection("detail_coll");
        beginDate       = new Date();
        logFile         = new File("log.txt");

        if (logFile.exists()) {
            logFile.delete();
        }
    }

    @Override
    public void processOneRow(Map<String, Object> rowMap) {

        BasicDBObject appendObject = new BasicDBObject()
                .append("ITEM_TYPE", rowMap.get("ITEM_TYPE"))
                .append("ITEM_TICKS_TIME", rowMap.get("ITEM_TICKS_TIME"))
                .append("ITEM_TICKS_DATE", rowMap.get("ITEM_TICKS_DATE"))
                .append("ITEM_ID", rowMap.get("ITEM_ID"))
                .append("ITEM_ID_13", rowMap.get("ITEM_ID_13"))
                .append("ITEM_ID_STD", rowMap.get("ITEM_ID_STD"))
                .append("ITEM_CLASS_SMALLEST", rowMap.get("ITEM_CLASS_SMALLEST"))
                .append("ITEM_NAME", rowMap.get("ITEM_NAME"))
                .append("NUMBERS", rowMap.get("NUMBERS"))
                .append("PRICE", rowMap.get("PRICE"))
                .append("COSTS", rowMap.get("COSTS"))
                .append("DEPT_ID", rowMap.get("DEPT_ID"))
                .append("DEPTNAME", rowMap.get("DEPTNAME"))
                .append("USAGE_UNIT", rowMap.get("USAGE_UNIT"))
                .append("Specification", rowMap.get("Specification"))
                .append("USAGE_DAYS", rowMap.get("USAGE_DAYS"))
                .append("USAGE", rowMap.get("USAGE"))
                .append("FREQUENCY_INTERVAL", rowMap.get("FREQUENCY_INTERVAL"))
                .append("USE_METHOD", rowMap.get("USE_METHOD"))
                .append("TheUseType", rowMap.get("TheUseType"))
                .append("SELF_AMOUNT", rowMap.get("SELF_AMOUNT"))
                .append("ELIGIBLE_AMOUNT", rowMap.get("ELIGIBLE_AMOUNT"))
                .append("UsageAmount", rowMap.get("UsageAmount"))
                .append("ITEM_TICKS_TIME_Next", rowMap.get("ITEM_TICKS_TIME_Next"))
                .append("RD_MedicalType",
                        new BasicDBObject()
                                .append("theFirstClinicalType", rowMap.get("theFirstClinicalType"))
                                .append("theSecondType", rowMap.get("theSecondType")))
                .append("RD_DiseaseCode", rowMap.get("RD_DiseaseCode"))
                .append("RD_HospitalID", rowMap.get("RD_HospitalID"))
                .append("RD_HospitalType", rowMap.get("RD_HospitalType"))
                .append("RD_FIRST_DATE", new Long(-1l))
                .append("PhysicianLevel", rowMap.get("PhysicianLevel"))
                .append("PHYSICIAN_ID", rowMap.get("PHYSICIAN_ID"))
                .append("PHYSICIAN_NAME", rowMap.get("PHYSICIAN_NAME"))
                .append("REAL_NUM", rowMap.get("REAL_NUM"))
                .append("REAL_MONEY", rowMap.get("REAL_MONEY"))
                .append("ALLOW_HISTORY", rowMap.get("ALLOW_HISTORY"))
                .append("ApprovalNumber", rowMap.get("ApprovalNumber"))
                .append("ETL_ClaimID", rowMap.get("ETL_ClaimID"))
                .append("PhysicianAP", rowMap.get("PhysicianAP"))
                .append("CostCategory", rowMap.get("CostCategory"))
                .append("AKF003", rowMap.get("AKF003"))
                .append("BKA609", rowMap.get("BKA609"))
                .append("ZZZ_Flag", rowMap.get("ZZZ_Flag"))
                .append("PrescriptionNo", rowMap.get("PrescriptionNo"))
                .append("COSTS_REAL_MONEY", rowMap.get("COSTS_REAL_MONEY"))
                .append("ITEM_DATE", rowMap.get("ITEM_DATE"));

        mingxiMap.put(new MongoKey(rowMap.get("PID"), rowMap.get("ETL_Patient_IDStr")), appendObject);

        counter++;
        if (counter % BATCH_SIZE == 0) {
            long second = (new Date().getTime() - beginDate.getTime()) / 1000l;
            second = (second == 0) ? 1 : second;

            String timeformatString = new DateTime().toString("HH:mm:ss");

            System.out.println("此次" + BATCH_SIZE + "条oracle原始纪录，共聚合成" + mingxiMap.asMap().size() + "条纪录。");
            for (Map.Entry<MongoKey, Collection<BasicDBObject>> mongoEntry : mingxiMap.asMap().entrySet()) {
                MongoKey key = mongoEntry.getKey();
                Collection<BasicDBObject> value = mongoEntry.getValue();

                Object pid = key.getPID();
                Object id = key.getETL_Patient_IDStr();

                BasicDBList mingxiList = new BasicDBList();
                mingxiList.addAll(value);

                BasicDBObject searchQuery = new BasicDBObject().append("ETL_Patient_IDStr", id).append("PID", pid);
                BasicDBObject newDocument = new BasicDBObject().append("$push", new BasicDBObject().append("mingxi", new BasicDBObject().append("$each", mingxiList)));

                try {
                    mongoCollection.updateOne(searchQuery, newDocument);
                } catch (Exception e) {
                    try {
                        Files.append("时间 " + timeformatString + ", 处理条目PID:" + pid + "" + ", ETL_Patient_IDStr: " + id + ". 错误信息: " + e.getMessage() + "\n", logFile, com.google.common.base.Charsets.UTF_8);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }

            }

            System.out.println("时间 " + timeformatString + ", 已处理" + counter / 10000.0 + "万条数据, 处理速度" + counter / second + "条/秒.");

            mingxiMap.clear();
        }
    }

    @Override
    public void streamArrivalTerminal() {

    }
}
