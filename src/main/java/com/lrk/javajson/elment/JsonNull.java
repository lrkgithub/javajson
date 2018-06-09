package main.java.com.lrk.javajson.elment;

import main.java.com.lrk.javajson.Main.parse.JsonValue;

public class JsonNull extends JsonValue {

    private static final String NULL = "null";

    @Override
    public String toString() {
        return NULL;
    }

}
