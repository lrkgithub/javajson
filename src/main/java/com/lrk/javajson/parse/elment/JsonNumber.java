package main.java.com.lrk.javajson.parse.elment;

import main.java.com.lrk.javajson.parse.JsonValue;

public class JsonNumber extends JsonValue {

    private String number;

    public void setNumber(String number) {
        this.number = number;
    }

    public String get() {
        return this.number;
    }

}
