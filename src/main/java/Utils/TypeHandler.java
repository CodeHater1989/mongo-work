package Utils;

import java.math.BigDecimal;

/**
 * Created by wanglei on 16/8/18.
 */
public class TypeHandler {
    public static Object convertType(Object object) {
        return convertType(object, "");
    }

    public static Object convertType(Object object, String type) {
        if (null == object || null == type) return null;

        if ("".equals(type) || "int".equals(type)) {
            if (object instanceof BigDecimal) {
                BigDecimal num = (BigDecimal) object;
                return num.intValue();
            }
        } else if ("long".equals(type)) {
            return ((BigDecimal) object).longValue();
        }

        return object;
    }
}
