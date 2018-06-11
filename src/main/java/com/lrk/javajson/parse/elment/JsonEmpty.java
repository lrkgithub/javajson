package main.java.com.lrk.javajson.parse.elment;

import main.java.com.lrk.javajson.parse.JsonValue;

public class JsonEmpty extends JsonValue {

    private String empty;

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public String get() {
        return "empty " + this.empty;
    }
}
