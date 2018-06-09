package main.java.com.lrk.javajson.test;

import main.java.com.lrk.javajson.Main.Parse;
import main.java.com.lrk.javajson.Main.parse.JsonValue;
import main.java.com.lrk.javajson.syntax.Syntax;
import main.java.com.lrk.javajson.syntax.SyntaxParse;
import main.java.com.lrk.javajson.Main.ParseJson;

import java.util.Map;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {
        ParseJson parse = new Parse();
        Syntax syntax = new SyntaxParse(parse);

        String s = "{\n" +
                "\t\"code\":0,\n" +
                "\t\"message\":\"success\",\n" +
                "\t\"result\":[\n" +
                "\t\t\t\t{\"id\":0,\n" +
                "\t\t\t\t \"img\":\"http://i0.hdslb.com/bfs/bangumi/34a310447b25a5599a2d061ad6661c56edfb03b7.jpg\",\n" +
                "\t\t\t\t \"link\":\"http://bangumi.bilibili.com/anime/6425\",\n" +
                "\t\t\t\t \"pub_time\":\"2017-12-07 00:00:00\",\n" +
                "\t\t\t\t \"title\":\"治愈一下\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t \"id\":0,\"img\":\"http://i0.hdslb.com/bfs/bangumi/5d9bc755524e8e8d4bb1956d7797ec05c84d6eeb.jpg\",\n" +
                "\t\t\t\t \"link\":\"https://www.bilibili.com/blackboard/topic/activity-rkgxJ8ref.html\",\n" +
                "\t\t\t\t \"pub_time\":\"2017-12-04 13:34:00\",\n" +
                "\t\t\t\t \"title\":\"补番推荐~~\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t ]\n" +
                "}";

        syntax.setStart(s);
        JsonValue jsonValue = null;
        try {
            jsonValue = syntax.syntaxJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonValue.toString());
    }

}
