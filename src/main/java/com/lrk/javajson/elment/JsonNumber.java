package main.java.com.lrk.javajson.elment;

import main.java.com.lrk.javajson.Main.parse.JsonValue;

public class JsonNumber extends JsonValue {

    private String number;

    public void setNumber(String number) {
        this.number = number;
    }

    public String get() {
        return this.number;
    }

}
