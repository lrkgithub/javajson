package main.java.com.lrk.javajson.parse;

import main.java.com.lrk.javajson.parse.elment.JsonArray;
import main.java.com.lrk.javajson.parse.elment.JsonFalse;
import main.java.com.lrk.javajson.parse.elment.JsonNull;
import main.java.com.lrk.javajson.parse.elment.JsonNumber;
import main.java.com.lrk.javajson.parse.elment.JsonMap;
import main.java.com.lrk.javajson.parse.elment.JsonString;
import main.java.com.lrk.javajson.parse.elment.JsonTrue;

public class ElementMaker {

    static JsonString jsonString() {

        JsonString js = new JsonString();
        js.setToken(JsonToken.STRING);

        return js;
    }

    public static JsonString jsonString(String jsonString) {

        JsonString js = new JsonString();
        js.setToken(JsonToken.STRING);

        js.setString(jsonString);

        return js;
    }


    public static JsonArray jsonArray(JsonValue... jvs) {

        JsonArray ja = new JsonArray();
        ja.setToken(JsonToken.ARRAY);

        for (JsonValue jv : jvs) {
            ja.add(jv);
        }

        return ja;
    }

    public static JsonNull jsonNull() {

        JsonNull jn = new JsonNull();
        jn.setToken(JsonToken.NULL);

        return jn;
    }

    public static JsonTrue jsonTrue() {

        JsonTrue jt = new JsonTrue();
        jt.setToken(JsonToken.TRUE);

        return jt;
    }

    public static JsonFalse jsonFalse() {

        JsonFalse jf = new JsonFalse();
        jf.setToken(JsonToken.FALSE);

        return jf;
    }

    public static JsonNumber jsonNumber(String number) {

        JsonNumber jn = new JsonNumber();
        jn.setToken(JsonToken.NUMBER);

        jn.setNumber(number);

        return jn;
    }

    public static JsonNumber jsonNumber(Number number) {

        JsonNumber jn = new JsonNumber();
        jn.setToken(JsonToken.NUMBER);

        jn.setNumber(number.toString());

        return jn;
    }

    public static JsonMap jsonObject() {

        JsonMap jo = new JsonMap();
        jo.setToken(JsonToken.OBJECT);

        return jo;
    }

}
