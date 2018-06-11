package main.java.com.lrk.javajson.core;


import main.java.com.lrk.javajson.parse.JsonMaker;
import main.java.com.lrk.javajson.parse.ParseJson;
import main.java.com.lrk.javajson.parse.SyntaxParse;

public class JsonBuilder {

    private ParseJson parse;

    private Syntax syntax;

    private Maker maker;

//    Class target;
//
//    public JsonBuilder setTargetClass(Class target) {
//        this.target = target;
//        return this;
//    }

    public String toJson(Object object) throws IllegalAccessException {

        this.maker = new JsonMaker();

        return maker.toJson(object);
    }

}
