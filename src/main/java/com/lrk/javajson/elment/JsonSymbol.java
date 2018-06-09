package main.java.com.lrk.javajson.elment;

import main.java.com.lrk.javajson.Main.parse.JsonValue;

public class JsonSymbol extends JsonValue {

    String symbol;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String get() {
        return this.symbol;
    }
}
