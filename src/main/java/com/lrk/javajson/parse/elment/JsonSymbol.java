package main.java.com.lrk.javajson.parse.elment;

import main.java.com.lrk.javajson.parse.JsonValue;

public class JsonSymbol extends JsonValue {

    String symbol;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String get() {
        return this.symbol;
    }
}
