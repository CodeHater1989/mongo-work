package oracle2Mongo;

import java.util.Objects;

/**
 * Created by wanglei on 16/7/22.
 */
public class MongoKey {

    private String PID;
    private String ETL_Patient_IDStr;

    public MongoKey(String PID, String ETL_Patient_IDStr) {
        this.PID = PID;
        this.ETL_Patient_IDStr = ETL_Patient_IDStr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PID, ETL_Patient_IDStr);
    }

    @Override
    public boolean equals(Object o){
        if (null == o) return false;

        if (o == this) return true;

        if (o instanceof MongoKey) {
            MongoKey m = (MongoKey) o;
            return Objects.equals(m.PID, this.PID) && Objects.equals(m.ETL_Patient_IDStr, this.ETL_Patient_IDStr);
        } else {
            return false;
        }

    }

    public String getPID() {
        return PID;
    }

    public String getETL_Patient_IDStr() {
        return ETL_Patient_IDStr;
    }
}
