package main.java.com.lrk.javajson.elment;

import main.java.com.lrk.javajson.Main.parse.JsonValue;

public class JsonString extends JsonValue {

    private String jsonString;

    public void setString(String jsonString) {
        this.jsonString = "\"" + jsonString + "\"";
    }

    @Override
    public String toString() {
        return this.jsonString;
    }

}
