import java.util.Map;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {
        ParseJson parse = new Parse();
        Syntax syntax = new SyntaxParse(parse);
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String s = sc.nextLine();
            try {
                syntax.setStart(s);
                Map map = syntax.syntaxJson();
                System.out.println(map.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
