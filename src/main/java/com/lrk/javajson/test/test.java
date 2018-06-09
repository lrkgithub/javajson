package main.java.com.lrk.javajson.test;

import main.java.com.lrk.javajson.Main.Parse;
import main.java.com.lrk.javajson.syntax.Syntax;
import main.java.com.lrk.javajson.syntax.SyntaxParse;
import main.java.com.lrk.javajson.Main.ParseJson;

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
