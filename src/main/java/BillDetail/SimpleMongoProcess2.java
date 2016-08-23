package Bill;

import Utils.Constants;
import Utils.TypeHandler;
import com.google.common.io.Files;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import oracle2Mongo.RowProcess;
import org.bson.Document;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglei on 16/8/22.
 */
public class SimpleMongoProcess2 implements RowProcess {
    private static final int BATCH_SIZE = 10_0000;
    private MongoCollection mongoCollection;
    private List<Document> documentsCache = new ArrayList<>(BATCH_SIZE);
    private long beginTime;
    private File logFile;
    private int counter = 0;

    public SimpleMongoProcess2(String collectionName, String logFileName) {
        MongoClient mongoClient = new MongoClient("10.117.130.122", 27000);
//        MongoClient   mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("wl_insert");

        mongoCollection = db.getCollection(collectionName);

        beginTime = System.currentTimeMillis();
        logFile = new File(logFileName);

        if (logFile.exists()) {
            logFile.delete();
        }
    }

    private void batchInsert() {
        mongoCollection.insertMany(documentsCache);
        documentsCache.clear();

        try {
            long endTime = System.currentTimeMillis();
            String speed = String.format("%.2f", (BATCH_SIZE) / ((endTime - beginTime) / 1000.0));
            String logString = new DateTime().toString(Constants.TIME_SHOW_FORMAT) + ", 已插入" + counter + "条数据, 抽取速度: " + speed + "条/秒";
            System.err.println(logString);
            Files.append(logString, logFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processOneRow(Map<String, Object> rowMap) {
        Document newDocument = new Document()
                .append("ETL_LOAD_DATE", System.currentTimeMillis())
                .append("ID", TypeHandler.convertType(rowMap.get("ID")))
                .append("PID", TypeHandler.convertType(rowMap.get("PID")))
                .append("ITEM_TYPE", TypeHandler.convertType(rowMap.get("ITEM_TYPE")))
                .append("ITEM_TICKS_TIME", ((Timestamp)rowMap.get("ITEM_TICKS_TIME")).getTime())
                .append("ITEM_TICKS_DATE", ((Timestamp)rowMap.get("ITEM_TICKS_DATE")).getTime())
                .append("ITEM_ID", TypeHandler.convertType(rowMap.get("ITEM_ID")))
                .append("ITEM_ID_13", TypeHandler.convertType(rowMap.get("ITEM_ID_13")))
                .append("ITEM_ID_STD", TypeHandler.convertType(rowMap.get("ITEM_ID_STD")))
                .append("ITEM_CLASS_SMALLEST", TypeHandler.convertType(rowMap.get("ITEM_CLASS_SMALLEST")))
                .append("ITEM_NAME", TypeHandler.convertType(rowMap.get("ITEM_NAME")))
                .append("NUMBERS", TypeHandler.convertType(rowMap.get("NUMBERS")))
                .append("PRICE", TypeHandler.convertType(rowMap.get("PRICE")))
                .append("COSTS", TypeHandler.convertType(rowMap.get("COSTS")))
                .append("DEPT_ID", TypeHandler.convertType(rowMap.get("DEPT_ID")))
                .append("DEPTNAME", TypeHandler.convertType(rowMap.get("DEPTNAME")))
                .append("USAGE_UNIT", TypeHandler.convertType(rowMap.get("USAGE_UNIT")))
                .append("Specification", TypeHandler.convertType(rowMap.get("SPECIFICATION")))
                .append("USAGE_DAYS", TypeHandler.convertType(rowMap.get("USAGE_DAYS")))
                .append("USAGE", TypeHandler.convertType(rowMap.get("USAGE")))
                .append("FREQUENCY_INTERVAL", TypeHandler.convertType(rowMap.get("FREQUENCY_INTERVAL")))
                .append("USE_METHOD", TypeHandler.convertType(rowMap.get("USE_METHOD")))
                .append("TheUseType", TypeHandler.convertType(rowMap.get("THEUSETYPE")))
                .append("SELF_AMOUNT", TypeHandler.convertType(rowMap.get("SELF_AMOUNT")))
                .append("ELIGIBLE_AMOUNT", TypeHandler.convertType(rowMap.get("ELIGIBLE_AMOUNT")))
                .append("UsageAmount", TypeHandler.convertType(rowMap.get("USAGEAMOUNT")))
                .append("ITEM_TICKS_TIME_Next", ((Timestamp)rowMap.get("ITEM_TICKS_TIME_NEXT")).getTime())
                .append("MED_TYPE", new Document()
                        .append("theFirstClinicalType", TypeHandler.convertType(rowMap.get("THEFIRSTCLINICALTYPE")))
                        .append("theSecondType", TypeHandler.convertType(rowMap.get("THESECONDTYPE"))))
                .append("RD_DiseaseCode", null)
                .append("RD_HospitalID", TypeHandler.convertType(rowMap.get("RD_HOSPITALID")))
                .append("RD_HospitalType", TypeHandler.convertType(rowMap.get("RD_HOSPITALTYPE")))
                .append("RD_FIRST_DATE", -1L)
                .append("PhysicianLevel", TypeHandler.convertType(rowMap.get("PHYSICIANLEVEL")))
                .append("PHYSICIAN_ID", TypeHandler.convertType(rowMap.get("PHYSICIAN_ID")))
                .append("PHYSICIAN_NAME", TypeHandler.convertType(rowMap.get("PHYSICIAN_NAME")))
                .append("REAL_NUM", TypeHandler.convertType(rowMap.get("REAL_NUM")))
                .append("REAL_MONEY", TypeHandler.convertType(rowMap.get("REAL_MONEY")))
                .append("ETL_Patient_IDStr", TypeHandler.convertType(rowMap.get("ETL_PATIENT_IDSTR")))
                .append("ALLOW_HISTORY", TypeHandler.convertType(rowMap.get("ALLOW_HISTORY")))
                .append("ApprovalNumber", TypeHandler.convertType(rowMap.get("APPROVALNUMBER")))
                .append("ETL_ClaimID", TypeHandler.convertType(rowMap.get("ETL_CLAIMID")))
                .append("PhysicianAP", TypeHandler.convertType(rowMap.get("PHYSICIANAP")))
                .append("CostCategory", TypeHandler.convertType(rowMap.get("COSTCATEGORY")))
                .append("AKF003", TypeHandler.convertType(rowMap.get("AKF003")))
                .append("BKA609", TypeHandler.convertType(rowMap.get("BKA609")))
                .append("ZZZ_Flag", TypeHandler.convertType(rowMap.get("ZZZ_FLAG")))
                .append("PrescriptionNo", TypeHandler.convertType(rowMap.get("PRESCRIPTIONNO")))
                .append("COSTS_REAL_MONEY", TypeHandler.convertType(rowMap.get("COSTS_REAL_MONEY")))
                .append("ITEM_DATE", TypeHandler.convertType(rowMap.get("ITEM_DATE")));

        documentsCache.add(newDocument);

        if (++counter % BATCH_SIZE == 0) {
            batchInsert();
        }
    }

    @Override
    public void streamArrivalTerminal() {
        batchInsert();
    }
}

