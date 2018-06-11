package main.java.com.lrk.javajson.parse.elment;

import main.java.com.lrk.javajson.parse.JsonValue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JsonArray extends JsonValue {

    List<Object> ll = new LinkedList<Object>();

    public void add(Object element) {
        ll.add(element);
    }

    public String get() {
        StringBuilder result = new StringBuilder();

        result.append("[");
        Iterator it = ll.iterator();

        while(it.hasNext()) {
            result.append(it.next().toString());
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
