package main.java.com.lrk.javajson.Main;

import main.java.com.lrk.javajson.Main.parse.JsonValue;

public interface ParseJson {

    public void setJsonschars(String jsonString);

    /**
     * 解析一个json的值
     * @return 返回这个json值
     * @throws Exception
     */
    JsonValue parseJson() throws Exception;

}
