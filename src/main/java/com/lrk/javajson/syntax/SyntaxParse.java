package main.java.com.lrk.javajson.syntax;

import main.java.com.lrk.javajson.elment.JsonArray;
import main.java.com.lrk.javajson.elment.JsonEmpty;
import main.java.com.lrk.javajson.elment.JsonObject;
import main.java.com.lrk.javajson.Main.parse.JsonToken;
import main.java.com.lrk.javajson.Main.parse.JsonValue;
import main.java.com.lrk.javajson.Main.ParseJson;
import main.java.com.lrk.javajson.elment.JsonSymbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public JsonValue syntaxJson() throws Exception {
        JsonValue jsonValue = new JsonEmpty();
        try {
            if (this.parse.isMap()) {
                jsonValue = parseObject();
            } else if (this.parse.isArray()) {
                jsonValue = parseArray();
            } else {
                jsonValue = getJsonValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonValue;
    }

    private JsonObject parseObject() throws Exception {

        JsonObject jo = new JsonObject();

        JsonValue jvLb = getJsonValue();
        if (!jvLb.getToken().equals(JsonToken.LB)) {
            System.out.println("must be LB : " + jvLb.get());
            return null;
        }

        List<Entry> entrys = parseEntry();

        for (Entry entry : entrys) {
            jo.add(entry.getKey(), entry.getValue());
        }

        JsonValue jvRb = getJsonValue();
        if (!jvRb.getToken().equals(JsonToken.RB)) {
            System.out.println("must be RB : " + jvRb.get());
            return null;
        }


        return jo;
    }

    private List<Entry> parseEntry() throws Exception {

        List<Entry> entrys = new ArrayList<Entry>();

        JsonValue jvKey = getJsonValue();
        if(!jvKey.getToken().equals(JsonToken.STRING)) {
            System.out.println("must be string : " + jvKey.get());
        }

        JsonValue jvColon = syntaxJson();

        if (!jvColon.getToken().equals(JsonToken.COLON)) {
            System.out.println("must be colon : " + jvColon.get());
        }

        JsonValue jvValue = syntaxJson();

        entrys.add(new Entry(jvKey, jvValue));

        while (this.parse.isComma()) {

            JsonValue jvComma = getJsonValue();
            if(!jvKey.getToken().equals(JsonToken.COMMA)) {
                System.out.println("must be comma : " + jvComma.get());
            }

            jvKey = getJsonValue();
            if(!jvKey.getToken().equals(JsonToken.STRING)) {
                System.out.println("must be string : " + jvKey.get());
            }

            jvColon = syntaxJson();

            if (!jvColon.getToken().equals(JsonToken.COLON)) {
                System.out.println("must be colon : " + jvColon.get());
            }

            jvValue = syntaxJson();
            entrys.add(new Entry(jvKey, jvValue));
        }
        return entrys;
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

    class Entry {

        private JsonValue key;
        private JsonValue value;

        Entry(JsonValue key, JsonValue value) {
            this.key = key;
            this.value = value;
        }

        public JsonValue getKey() {
            return this.key;
        }

        public JsonValue getValue() {
            return this.value;
        }
    }

}
