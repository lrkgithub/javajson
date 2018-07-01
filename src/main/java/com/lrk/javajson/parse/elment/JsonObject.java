package main.java.com.lrk.javajson.parse.elment;

import main.java.com.lrk.javajson.parse.JsonToken;
import main.java.com.lrk.javajson.parse.JsonValue;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject extends JsonValue {

    Map<JsonValue, JsonValue> map = new LinkedHashMap<JsonValue, JsonValue>();

    public void add(JsonValue jv1, JsonValue jv2) {
        if(!jv1.getToken().equals(JsonToken.STRING)) {
            return;
        }
        map.put(jv1, jv2);
    }

    public String get() {

        //  若为空则返回空的{}
        if (this.map.isEmpty()) {
            return "{}";
        }

        StringBuilder result = new StringBuilder();
        result.append("{");


        Set<Map.Entry<JsonValue, JsonValue>> entry = map.entrySet();
        Iterator<Map.Entry<JsonValue, JsonValue>> it = entry.iterator();

        //  此处至少有一个值
        while (true) {

            Map.Entry<JsonValue, JsonValue> entry1 = it.next();

            result.append(entry1.getKey().get());
            result.append(":");
            result.append(entry1.getValue().get());

            if (it.hasNext()) {
                result.append(",");
            } else {
                break;
            }

        }

        result.append("}");
        return result.toString();
    }

    public Map<JsonValue, JsonValue> getMap() {
        return this.map;
    }

}
