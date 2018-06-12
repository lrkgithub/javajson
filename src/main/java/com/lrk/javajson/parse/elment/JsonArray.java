package main.java.com.lrk.javajson.parse.elment;

import main.java.com.lrk.javajson.parse.JsonValue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JsonArray extends JsonValue {

    private List<JsonValue> ll = new LinkedList<JsonValue>();

    public void add(JsonValue element) {
        ll.add(element);
    }

    public List<JsonValue> getList() {
        return this.ll;
    }

    public String get() {
        StringBuilder result = new StringBuilder();

        result.append("[");

        for (JsonValue jsonValue : ll) {
            result.append(jsonValue.get());
            result.append(", ");
        }

        if(result.length() > 1) {
            result.setCharAt((result.length() - 2), ']');
        } else {
            result.append("]");
        }

        return result.toString();
    }

}
