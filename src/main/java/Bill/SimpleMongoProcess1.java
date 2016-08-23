package Bill;

import Utils.Constants;
import Utils.TypeHandler;
import com.google.common.io.Files;
import com.mongodb.BasicDBList;
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
public class SimpleMongoProcess1 implements RowProcess {
    private static final int BATCH_SIZE = 10_0000;
    private MongoCollection mongoCollection;
    private List<Document> documentsCache = new ArrayList<>(BATCH_SIZE);
    private long beginTime;
    private File logFile;
    private int counter = 0;

    public SimpleMongoProcess1(String collectionName, String logFileName) {
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
            System.out.println(logString);
            Files.append(logString, logFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processOneRow(Map<String, Object> rowMap) {
        BasicDBList diseases = new BasicDBList();
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_ONE")));
        diseases.add(TypeHandler.convertType(rowMap.get("DISEASE_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_SECOND")));
        diseases.add(TypeHandler.convertType(rowMap.get("SECOND_DISEASE_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_THIRD")));
        diseases.add(TypeHandler.convertType(rowMap.get("THIRD_DISEASE_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_FOUR")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_FOUR_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_FIVE")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_FIVE_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_SIX")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_SIX_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_SEVEN")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_SEVEN_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_EIGHT")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_EIGHT_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_NINE")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_NINE_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_TEN")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_TEN_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_ELEVEN")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_ELEVEN_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_TWELVE")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_TWELVE_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_THIRTEEN")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_THIRTEEN_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_FOURTEEN")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_FOURTEEN_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_FIFTEEN")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_FIFTEEN_NAME")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_SIXTEEN")));
        diseases.add(TypeHandler.convertType(rowMap.get("DIAGNOSIS_SIXTEEN_NAME")));

        Document newDocument = new Document()
                .append("ETL_LOAD_DATE", System.currentTimeMillis())
                .append("ID", TypeHandler.convertType(rowMap.get("ID")))
                .append("HISID", TypeHandler.convertType(rowMap.get("HISID")))
                .append("Patient_IDStr", TypeHandler.convertType(rowMap.get("PATIENT_IDSTR")))
                .append("UNUSUAL_FLAG", TypeHandler.convertType(rowMap.get("UNUSUAL_FLAG")))
                .append("SEETLE_TICKS_TIME", ((Timestamp)rowMap.get("SEETLE_TICKS_TIME")).getTime())
                .append("SEETLE_TICKS_DATE", ((Timestamp)rowMap.get("SEETLE_TICKS_DATE")).getTime())
                .append("HOSPITAL_ID", TypeHandler.convertType(rowMap.get("HOSPITAL_ID")))
                .append("HOSPITAL_LEVEL", TypeHandler.convertType(rowMap.get("HOSPITAL_LEVEL")))
                .append("MED_TYPE", new Document()
                        .append("theFirstClinicalType", TypeHandler.convertType(rowMap.get("THEFIRSTCLINICALTYPE")))
                        .append("theSecondType", TypeHandler.convertType(rowMap.get("THESECONDTYPE"))))
                .append("BENEFIT_TYPE", TypeHandler.convertType(rowMap.get("BENEFIT_TYPE")))
                .append("DIAGNOSIS_IN", TypeHandler.convertType(rowMap.get("DIAGNOSIS_IN")))
                .append("ADMISSION_DISEASE_NAME", TypeHandler.convertType(rowMap.get("ADMISSION_DISEASE_NAME")))
                .append("DIAGNOSIS_OUT", TypeHandler.convertType(rowMap.get("DIAGNOSIS_OUT")))
                .append("DISCHARGE_DISEASE_NAME", TypeHandler.convertType(rowMap.get("DISCHARGE_DISEASE_NAME")))
                .append("DISEASE", diseases)
                .append("GENDER", TypeHandler.convertType(rowMap.get("GENDER")))
                .append("BMI_CODE", TypeHandler.convertType(rowMap.get("BMI_CODE")))
                .append("IsPregnant", false)
                .append("IsLactation", false)
                .append("TOTAL_COST", TypeHandler.convertType(rowMap.get("TOTAL_COST")))
                .append("BENEFIT_GROUP_ID", TypeHandler.convertType(rowMap.get("BENEFIT_GROUP_ID")))
                .append("CKC892", TypeHandler.convertType(rowMap.get("CKC892")))
                .append("AUDIT_AUTO_STATUS", TypeHandler.convertType(rowMap.get("AUDIT_AUTO_STATUS")))
                .append("BMI_CONVERED_AMOUNT", TypeHandler.convertType(rowMap.get("BMI_CONVERED_AMOUNT")))
                .append("PatientBenefitGroupCode", TypeHandler.convertType(rowMap.get("PATIENTBENEFITGROUPCODE")))
                .append("DISCHARGE_REASON", TypeHandler.convertType(rowMap.get("DISCHARGE_REASON")))
                .append("BAC021", TypeHandler.convertType(rowMap.get("BAC021")))
                .append("BILL_NO", TypeHandler.convertType(rowMap.get("BILL_NO")))
                .append("HaveSave", true)
                .append("DJEKD001", TypeHandler.convertType(rowMap.get("DJEKD001")))
                .append("SETTLE_DATE", TypeHandler.convertType(rowMap.get("SETTLE_DATE")))
                .append("IN_DATE", TypeHandler.convertType(rowMap.get("IN_DATE")))
                .append("OUT_DATE", TypeHandler.convertType(rowMap.get("OUT_DATE")))
                .append("PATIENT_BIRTH", TypeHandler.convertType(rowMap.get("PATIENT_BIRTH")));

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
