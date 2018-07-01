package main.java.com.lrk.javajson.parse.elment;

import main.java.com.lrk.javajson.parse.JsonValue;

public class JsonNull extends JsonValue {

    private static final Object NULL = null;

    public Object get() {
        return NULL;
    }

}
