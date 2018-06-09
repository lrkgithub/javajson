package main.java.com.lrk.javajson.elment;

import main.java.com.lrk.javajson.Main.parse.JsonToken;
import main.java.com.lrk.javajson.Main.parse.JsonValue;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject {

    Map<JsonValue, Object> map = new LinkedHashMap<JsonValue, Object>();

    public void add(JsonValue jv1, Object jv2) {
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


        Set<Map.Entry<JsonValue, Object>> entry = map.entrySet();
        Iterator<Map.Entry<JsonValue, Object>> it = entry.iterator();

        //  此处至少有一个值
        while (true) {

            Map.Entry<JsonValue, Object> entry1 = it.next();

            result.append(entry1.getKey().get());
            result.append(":");
            result.append(entry1.getValue().toString());

            if (it.hasNext()) {
                result.append(",");
            } else {
                break;
            }

        }

        result.append("}");
        return result.toString();
    }

}
