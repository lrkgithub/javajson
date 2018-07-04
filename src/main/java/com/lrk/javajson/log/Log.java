package main.java.com.lrk.javajson.log;

public class Log {

    private static final boolean DEBUG = false;

    public static void log(String string) {

        if (DEBUG) {
            System.out.println(string);
        }

    }

}
