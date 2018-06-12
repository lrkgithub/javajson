package main.java.com.lrk.javajson.core;

import main.java.com.lrk.javajson.parse.JsonValue;
import main.java.com.lrk.javajson.parse.elment.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class JsonMapper {

    private JsonValue jsonValue;

    public void setMainValue(JsonValue jsonValue) {
        this.jsonValue = jsonValue;
    }

    public String toJson() {
        return "";
    }

    public Object fromJson(Class clazz) {

        if (this.jsonValue.getClass() == JsonArray.class) {

            ArrayList arrayLsit = new ArrayList();

            List<JsonValue> jsonValueList = ((JsonArray)jsonValue).getList();

        }

        return null;
    }

}
