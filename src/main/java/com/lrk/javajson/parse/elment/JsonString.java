package main.java.com.lrk.javajson.parse.elment;

import main.java.com.lrk.javajson.parse.JsonValue;

public class JsonString extends JsonValue {

    private String jsonString;

    public void setString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String get() {
        return this.jsonString;
    }
}
