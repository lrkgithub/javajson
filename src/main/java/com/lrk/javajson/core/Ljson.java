package main.java.com.lrk.javajson.core;

public interface Ljson {

    String toJson(Object object) throws IllegalAccessException;

    Object fromJson(String jsonString, Class clazz) throws Exception;
}
