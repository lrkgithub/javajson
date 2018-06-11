package main.java.com.lrk.javajson.core;

import main.java.com.lrk.javajson.parse.JsonValue;

public interface Syntax {

    /**
     *
     * @param s 待解析的json字符串，转化成char[]数组保存
     */
    void setStart(String s);

    /**
     * 解析json的方法，得到的是一个Map
     */
    JsonValue syntaxJson() throws Exception;

}
