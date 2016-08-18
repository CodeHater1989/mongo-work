package testCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test8 {
    public static void main(String[] args) throws Throwable {
        String sql = "select * from s where d > #{begin_date} and #{end_date}";
        String reg = "\\#\\{.*?\\}";

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        System.out.println(sql.replaceAll(reg, "?"));
    }
}
