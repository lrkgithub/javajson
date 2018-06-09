package main.java.com.lrk.javajson.Main.parse;

public enum JsonToken {


    //    {}
    LB('{', "{"),
    RB('}', "}"),

    //    []
    LL('[', "["),
    RL(']', "]"),

    NUMBER(),

    //    错误的标识符
    BADTOKEN(),

    STRING,

    //    ，
    COMMA(',', ","),

    //    ；
    SEMICOLON,

    TRUE,
    FALSE,
    NULL,

    //    :
    COLON(':', ":");


    Character c;
    String s;

    JsonToken(char c, String s) {
        this.c = c;
        this.s = s;
    }

    JsonToken() {}

}
