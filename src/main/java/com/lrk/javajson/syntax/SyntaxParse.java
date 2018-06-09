package main.java.com.lrk.javajson.syntax;

import main.java.com.lrk.javajson.elment.JsonArray;
import main.java.com.lrk.javajson.elment.JsonObject;
import main.java.com.lrk.javajson.Main.parse.JsonToken;
import main.java.com.lrk.javajson.Main.parse.JsonValue;
import main.java.com.lrk.javajson.Main.ParseJson;
import main.java.com.lrk.javajson.elment.JsonSymbol;

import java.util.HashMap;
import java.util.Map;

public class SyntaxParse implements Syntax {

    ParseJson parse;
    JsonValue aHead;

    public SyntaxParse(ParseJson parse) {
        this.parse = parse;
    }

    public void setStart(String jsonString) {
        parse.setJsonschars(jsonString);
    }

    public Map syntaxJson() throws Exception {
        HashMap<Object, Object> hm = new HashMap<Object, Object>();
        try {
            parseObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hm;
    }

    private JsonObject parseObject() {
        JsonValue jvLb = getJsonValue();
        if (!jvLb.getToken().equals(JsonToken.LB)) {
            System.out.println("must be { : " + jvLb.get());
            return null;
        }
        JsonValue jvKey = getJsonValue();
        if(!jvKey.getToken().equals(JsonToken.STRING)) {
            System.out.println("must be string : " + jvKey.get());
        }
        Object jvValue = parseValue();
        JsonObject jo = new JsonObject();
        JsonValue jvRb = getJsonValue();
        if (!jvRb.getToken().equals(JsonToken.RB)) {
            System.out.println("must be { : " + jvRb.get());
            return null;
        }
        jo.add(jvKey, jvValue);
        return jo;
    }

    private Object parseValue() {

        JsonValue jv = getJsonValue();
        Object value = null;

        if(jv.getToken().equals(JsonToken.LL)) {
            JsonArray ja = parseArray();
            jv = getJsonValue();
            if(!jv.getToken().equals(JsonToken.RL)) {

            }
        }



        return null;
    }

    private JsonArray parseArray() {
        return null;
    }

    /**
     *
     * @return 一个json的值
     */
    private JsonValue getJsonValue() {
        try {
            JsonValue jv = parse.parseJson();
            return jv;
        } catch (Exception e) {
            e.printStackTrace();
            JsonValue jv = new JsonSymbol();
            jv.setToken(JsonToken.BADTOKEN);
            jv.setSymbol("axiba");
            return jv;
        }
    }

}
