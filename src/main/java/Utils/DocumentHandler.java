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

            if (null != value && Document.class.isAssignableFrom(value.getClass())) {
                Map<String, Object> subDocument = flatDocument((Document) value);
                flatMap.putAll(subDocument);
            } else {
                flatMap.put(key.toUpperCase(), value);
            }
        }

        return flatMap;
    }
}
