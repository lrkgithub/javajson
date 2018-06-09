import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject {

    Map<JsonValue, Object> map = new LinkedHashMap<JsonValue, Object>();

    public void add(JsonValue jv1, Object jv2) {
        if(!jv1.getToken().equals(JsonToken.STRING)) {
            return;
        }
        map.put(jv1, jv2);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(JsonValue jv : map.keySet()) {
            result.append(jv.getValue());
            result.append(":");
            result.append(map.get(jv).toString());
        }
        return result.toString();
    }

}
