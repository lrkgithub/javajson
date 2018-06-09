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
            System.out.println("must be { : " + jvLb.getValue() + "   " + jvLb.getNumber());
            return null;
        }
        JsonValue jvKey = getJsonValue();
        if(!jvKey.getToken().equals(JsonToken.STRING)) {
            System.out.println("must be string : " + jvKey.getValue() + "   " + jvKey.getNumber());
        }
        Object jvValue = parseValue();
        JsonObject jo = new JsonObject();
        JsonValue jvRb = getJsonValue();
        if (!jvRb.getToken().equals(JsonToken.RB)) {
            System.out.println("must be { : " + jvRb.getValue() + "   " + jvRb.getNumber());
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

    private JsonValue getJsonValue() {
        try {
            JsonValue jv = parse.parseJson();
            return jv;
        } catch (Exception e) {
            e.printStackTrace();
            JsonValue jv = new JsonValue();
            jv.setToken(JsonToken.BADTOKEN);
            jv.setValue("axiba");
            return jv;
        }
    }

}
