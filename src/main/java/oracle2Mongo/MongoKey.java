package oracle2Mongo;

import com.google.common.base.Objects;

/**
 * Created by wanglei on 16/7/22.
 */
public class MongoKey {

    private Object PID;
    private Object ETL_Patient_IDStr;

    public MongoKey(Object PID, Object ETL_Patient_IDStr) {
        this.PID = PID;
        this.ETL_Patient_IDStr = ETL_Patient_IDStr;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(PID, ETL_Patient_IDStr);
    }

    @Override
    public boolean equals(Object o){
        if (null == o) return false;

        if (o == this) return true;

        if (o instanceof MongoKey) {
            MongoKey m = (MongoKey) o;
            return m.PID.equals(this.PID) && m.ETL_Patient_IDStr.equals(this.ETL_Patient_IDStr);
        } else {
            return false;
        }

    }

    public Object getPID() {
        return PID;
    }

    public Object getETL_Patient_IDStr() {
        return ETL_Patient_IDStr;
    }
}
