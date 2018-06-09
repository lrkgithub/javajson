package main.java.com.lrk.javajson.Main.parse;

public abstract class JsonValue {

    JsonToken token;

    public void setToken(JsonToken token) {
        this.token = token;
    }

    public JsonToken getToken() {
        return this.token;
    }

    public abstract String get();

//    存放String的方法
    public void setString(String s) {}

//    存放数字的方法
    public void setNumber(String n) {}

//    存放符号的方法
    public void setSymbol(String v) {}

    @Override
    public String toString() {

        return get();

    }

}
