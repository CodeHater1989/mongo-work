package Utils;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglei on 16/8/19.
 */
public class DocumentHandler {
    public static Map<String, Object> flatDocument(Document document) {
        Map<String, Object>  flatMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

//            if (null != key) System.out.println(key);
            if (null != value) System.out.println(value.getClass().getName());
        }

        return flatMap;
    }
}
