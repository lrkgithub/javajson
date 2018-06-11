package main.java.com.lrk.javajson.core;

import main.java.com.lrk.javajson.parse.JsonValue;

public interface Parse {

    public void setJsonschars(String jsonString);

    /**
     * 解析一个json的值
     * @return 返回这个json值
     * @throws Exception
     */
    JsonValue parseJson() throws Exception;

    /**
     * 当前解析是否是map
     * @return 如果是map，返回true
     */
    boolean isMap();

    /**
     * 当前解析是否是array
     * @return 如果是array，返回true
     */
    boolean isArray();

    /**
     * 解析当前是否是map的结束
     * @return 如果是map的结束，返回true
     */
    boolean isMapEnd();

    /**
     * 解析当前是否是array的结束
     * @return 如果是array的结束，返回true
     */
    boolean isArrayEnd();

    /**
     * 解析当前是否是逗号
     * @return 如果是逗号，返回true
     */
    boolean isComma();

}
