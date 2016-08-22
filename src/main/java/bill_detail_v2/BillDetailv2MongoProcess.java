package bill_detail_v2;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import com.mongodb.BasicDBList;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import oracle2Mongo.MongoKey;
import oracle2Mongo.RowProcess;
import org.bson.Document;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglei on 16/8/18.
 */
public class BillDetailv2MongoProcess implements RowProcess {

    private static final int                          BATCH_SIZE             = 10_0000;
    private              MongoCollection              mongoCollection;
    private              Map<MongoKey, Integer>       keyStat                = new HashMap<>();
    private static final int                          MAX_RECODE_DETAIL_SIZE = 2_0000;
    private static       String                       SEPARATOR              = "###";
    private              Multimap<MongoKey, Document> mingxiMap              = ArrayListMultimap.create();
    private              int                          recordCounter          =      0;
    private              Date                         beginDate;
    private              File                         logFile;

    public BillDetailv2MongoProcess() {
        MongoClient   mongoClient = new MongoClient("10.117.130.122", 27000);
//        MongoClient   mongoClient = new MongoClient();
        MongoDatabase db          = mongoClient.getDatabase("wl_insert");

        mongoCollection = db.getCollection("gz_detail_v2");

        beginDate       = new Date();
        logFile         = new File("detail_log.txt");

        if (logFile.exists()) {
            logFile.delete();
        }
    }

    private MongoKey getMongoKey(String pid, String ETL_Patient_IDStr) {
        MongoKey mongoKey = new MongoKey(pid, ETL_Patient_IDStr);

        Integer counter = keyStat.get(mongoKey);
        if (counter != null && counter > MAX_RECODE_DETAIL_SIZE) {
            int numberSuffix = pid.contains(SEPARATOR) ? (Integer.parseInt(pid.split(SEPARATOR)[1]) + 1) : 1;
            String pidPrefix = pid.contains(SEPARATOR) ? (pid.split(SEPARATOR)[0]) : pid;
            return getMongoKey(pidPrefix + SEPARATOR + numberSuffix, ETL_Patient_IDStr);
        }

        return mongoKey;
    }

    @Override
    public void processOneRow(Map<String, Object> rowMap) {

        Object pid    = rowMap.get("PID");
        Object ETL_ID = rowMap.get("ETL_PATIENT_IDSTR");

        if (!(pid instanceof String) || !(ETL_ID instanceof String))
            throw new AssertionError("断言pid, ETL_Patient_IDStr为String类型失败!");

        MongoKey mongoKey = getMongoKey((String) pid, (String) ETL_ID);
        if (keyStat.get(mongoKey) == null) {
            Document document = new Document()
                    .append("ETL_Patient_IDStr", mongoKey.getETL_Patient_IDStr())
                    .append("PID", mongoKey.getPID())
                    .append("RD_MedicalType", new Document()
                            .append("theFirstClinicalType", rowMap.get("THEFIRSTCLINICALTYPE"))
                            .append("theSecondType", rowMap.get("THESECONDTYPE")))
                    .append("RD_DiseaseCode", rowMap.get("RD_DISEASECODE"))
                    .append("RD_HospitalID", rowMap.get("RD_HOSPITALID"))
                    .append("RD_HospitalType", rowMap.get("RD_HOSPITALTYPE"))
                    .append("RD_FIRST_DATE", rowMap.get("RD_FIRST_DATE"))
                    .append("ETL_ClaimID", rowMap.get("ETL_CLAIMID"));

            mongoCollection.insertOne(document);

            keyStat.put(mongoKey, 0);
        }

        Document appendObject = new Document()
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
                .append("Specification", rowMap.get("SPECIFICATION"))
                .append("USAGE_DAYS", rowMap.get("USAGE_DAYS"))
                .append("USAGE", rowMap.get("USAGE"))
                .append("FREQUENCY_INTERVAL", rowMap.get("FREQUENCY_INTERVAL"))
                .append("USE_METHOD", rowMap.get("USE_METHOD"))
                .append("TheUseType", rowMap.get("THEUSETYPE"))
                .append("SELF_AMOUNT", rowMap.get("SELF_AMOUNT"))
                .append("ELIGIBLE_AMOUNT", rowMap.get("ELIGIBLE_AMOUNT"))
                .append("UsageAmount", rowMap.get("USAGEAMOUNT"))
                .append("ITEM_TICKS_TIME_Next", rowMap.get("ITEM_TICKS_TIME_NEXT"))
                .append("PhysicianLevel", rowMap.get("PHYSICIANLEVEL"))
                .append("PHYSICIAN_ID", rowMap.get("PHYSICIAN_ID"))
                .append("PHYSICIAN_NAME", rowMap.get("PHYSICIAN_NAME"))
                .append("REAL_NUM", rowMap.get("REAL_NUM"))
                .append("REAL_MONEY", rowMap.get("REAL_MONEY"))
                .append("ALLOW_HISTORY", rowMap.get("ALLOW_HISTORY"))
                .append("ApprovalNumber", rowMap.get("APPROVALNUMBER"))
                .append("PhysicianAP", rowMap.get("PHYSICIANAP"))
                .append("CostCategory", rowMap.get("COSTCATEGORY"))
                .append("AKF003", rowMap.get("AKF003"))
                .append("BKA609", rowMap.get("BKA609"))
                .append("ZZZ_Flag", rowMap.get("ZZZ_FLAG"))
                .append("PrescriptionNo", rowMap.get("PRESCRIPTIONNO"))
                .append("COSTS_REAL_MONEY", rowMap.get("COSTS_REAL_MONEY"))
                .append("ITEM_DATE", rowMap.get("ITEM_DATE"));

        mingxiMap.put(mongoKey, appendObject);
        keyStat.put(mongoKey, keyStat.get(mongoKey) + 1);

        if (++recordCounter % BATCH_SIZE == 0) {
            insertBatch();
        }
    }

    private void insertBatch() {
        long second = (new Date().getTime() - beginDate.getTime()) / 1000l;
        second = (second == 0) ? 1 : second;

        String timeformatString = new DateTime().toString("HH:mm:ss");

        System.out.println("此次" + BATCH_SIZE + "条oracle原始纪录，共聚合成" + mingxiMap.asMap().size() + "条纪录。");
        for (Map.Entry<MongoKey, Collection<Document>> mongoEntry : mingxiMap.asMap().entrySet()) {
            MongoKey key = mongoEntry.getKey();
            Collection<Document> value = mongoEntry.getValue();

            Object pid = key.getPID();
            Object id = key.getETL_Patient_IDStr();

            BasicDBList mingxiList = new BasicDBList();
            mingxiList.addAll(value);

            Document searchQuery = new Document().append("ETL_Patient_IDStr", id).append("PID", pid);
            Document newDocument = new Document().append("$push", new Document().append("mingxi", new Document().append("$each", mingxiList)));

            try {
                mongoCollection.updateOne(searchQuery, newDocument);
            } catch (Exception e) {
                try {
                    Files.append("时间 " + timeformatString + ", 处理条目PID:" + pid + "" + ", ETL_Patient_IDStr: " + id + ". 错误信息: " + e.getMessage() + "\n", logFile, Charsets.UTF_8);
                } catch (IOException e1) {
                    System.out.println(Throwables.getStackTraceAsString(e1));
                }
                e.printStackTrace();
            }
        }

        System.out.println("时间 " + timeformatString + ", 已处理" + recordCounter / 10000.0 + "万条数据, 处理速度" + recordCounter / second + "条/秒.");

        mingxiMap.clear();
    }

    @Override
    public void streamArrivalTerminal() {
        insertBatch();
    }
}
