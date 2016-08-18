package testCode;

/**
 * Created by wanglei on 16/7/26.
 */
public class Test14 {
    public static void main(String[] args) {
        stringAdd();
        stringBuilderAppend();
    }

    public static void stringAdd() {
        String s = "a";

        for (int i = 0; i < 5; i++) {
            s += i;
        }

        System.out.println(s);
    }

    public static void stringBuilderAppend() {
        String s = "a";

        for (int i = 0; i < 5; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(s);
            sb.append(i);
            s = sb.toString();
        }

        System.out.println(s);
    }
}
