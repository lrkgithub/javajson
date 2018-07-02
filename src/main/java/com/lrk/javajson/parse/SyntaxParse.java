package main.java.com.lrk.javajson.parse;

import main.java.com.lrk.javajson.core.Parse;
import main.java.com.lrk.javajson.core.Syntax;
import main.java.com.lrk.javajson.parse.elment.JsonArray;
import main.java.com.lrk.javajson.parse.elment.JsonEmpty;
import main.java.com.lrk.javajson.parse.elment.JsonObject;
import main.java.com.lrk.javajson.parse.elment.JsonString;
import main.java.com.lrk.javajson.parse.elment.JsonSymbol;

import java.util.ArrayList;
import java.util.List;

public class SyntaxParse implements Syntax {

    private Parse parse;

    public SyntaxParse(Parse parse) {
        this.parse = parse;
    }

    public void setStart(String jsonString) {
        parse.setJsonschars(jsonString);
    }

    public JsonValue syntaxJson() {
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

    private JsonObject parseObject() {

        JsonObject jo = new JsonObject();

        JsonValue jvLb = getJsonValue();
        if (!jvLb.getToken().equals(JsonToken.LB)) {
            System.out.println("must be LB : " + jvLb.get());
            return null;
        }

        List<Entry> entries = parseEntry();

        for (Entry entry : entries) {
            jo.add((JsonString)entry.getKey(), entry.getValue());
        }

        JsonValue jvRb = getJsonValue();
        if (!jvRb.getToken().equals(JsonToken.RB)) {
            System.out.println("must be RB : " + jvRb.get());
            return null;
        }


        return jo;
    }

    private List<Entry> parseEntry() {

        List<Entry> entries = new ArrayList<Entry>();

        if (this.parse.isMapEnd()) {
            return entries;
        }

        JsonValue jvKey = getJsonValue();
        if(!jvKey.getToken().equals(JsonToken.STRING)) {
            System.out.println("must be string : " + jvKey.get());
        }

        JsonValue jvColon = syntaxJson();

        if (!jvColon.getToken().equals(JsonToken.COLON)) {
            System.out.println("must be colon : " + jvColon.get());
        }

        JsonValue jvValue = syntaxJson();

        entries.add(new Entry((JsonString)jvKey, jvValue));

        while (this.parse.isComma()) {

            JsonValue jvComma = getJsonValue();
            if(!jvComma.getToken().equals(JsonToken.COMMA)) {
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
            entries.add(new Entry((JsonString)jvKey, jvValue));
        }
        return entries;
    }

    private JsonArray parseArray() {

        JsonValue jvLL = getJsonValue();
        if(!jvLL.getToken().equals(JsonToken.LL)) {
            System.out.println("must be LL : " + jvLL.get());
        }

        JsonArray jvarrs = new JsonArray();

        if (this.parse.isArrayEnd()) {
            return jvarrs;
        }

        JsonValue jvEle = syntaxJson();
        jvarrs.add(jvEle);

        while (this.parse.isComma()) {

            JsonValue jvComma = getJsonValue();
            if(!jvComma.getToken().equals(JsonToken.COMMA)) {
                System.out.println("must be COMMA : " + jvComma.get());
            }

            jvEle = syntaxJson();
            jvarrs.add(jvEle);
        }

        JsonValue jvRL = getJsonValue();
        if(!jvRL.getToken().equals(JsonToken.RL)) {
            System.out.println("must be RL : " + jvRL.get());
        }

        return jvarrs;
    }

    /**
     *
     * @return 一个json的值
     */
    private JsonValue getJsonValue() {
        try {
            return parse.parseJson();
        } catch (Exception e) {
            e.printStackTrace();
            JsonValue jv = new JsonSymbol();
            jv.setToken(JsonToken.BADTOKEN);
            jv.setSymbol("axiba");
            return jv;
        }
    }

    class Entry {

        private JsonString key;
        private JsonValue value;

        Entry(JsonString key, JsonValue value) {
            this.key = key;
            this.value = value;
        }

        JsonString getKey() {
            return this.key;
        }

        JsonValue getValue() {
            return this.value;
        }
    }

}
