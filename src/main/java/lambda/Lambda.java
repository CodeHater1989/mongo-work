package lambda;

@FunctionalInterface
interface Print<T> {
    void print(T t);
}
public class Lambda {
    public static void PrintString(String s, Print<String> print) {
        print.print(s);
    }

    public static void main(String[] atgs) {
        PrintString("hello", s -> System.out.println(s));
    }
}
