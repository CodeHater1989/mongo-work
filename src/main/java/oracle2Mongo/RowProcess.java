package oracle2Mongo;

import java.util.Map;

/**
 * Created by wanglei on 16/7/25.
 */
public interface RowProcess {
    void processOneRow(Map<String, Object> rowMap);

    void streamArrivalTerminal();
}
