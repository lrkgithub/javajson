package main.java.com.lrk.javajson.parse.elment;

import main.java.com.lrk.javajson.parse.JsonValue;

public class JsonNumber extends JsonValue {

    private String number;

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumString() {
        return this.number;
    }

    public Number get() {
//  待优化
        if (number.contains(".")) {
            return Double.valueOf(number);
        } else {
            return Long.valueOf(number);
        }
    }

}
