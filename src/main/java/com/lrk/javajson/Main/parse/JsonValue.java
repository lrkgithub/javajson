package main.java.com.lrk.javajson.Main.parse;

public class JsonValue {

    JsonToken token;
    String value;
    Double number;

    public void setToken(JsonToken token) {
        this.token = token;
    }

    public JsonToken getToken() {
        return this.token;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Double getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        JsonToken token = this.getToken();
        StringBuilder result = new StringBuilder();
        if(token.equals(JsonToken.STRING)) {
            result.append(getValue());
        } else if(token.equals(JsonToken.NUMBER)) {
            result.append(getNumber());
        } else if (token.equals(JsonToken.FALSE)) {
            result.append("false");
        } else if (token.equals(JsonToken.TRUE)) {
            result.append("true");
        } else if (token.equals(JsonToken.NULL)) {
            result.append("null");
        } else {
            result.append(" wrongwrongwrong ! ");
        }
        return result.toString();
    }

}
