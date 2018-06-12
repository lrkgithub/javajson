package main.java.com.lrk.javajson.core;

import main.java.com.lrk.javajson.annotation.Property;
import main.java.com.lrk.javajson.parse.JsonValue;
import main.java.com.lrk.javajson.parse.elment.JsonArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            ArrayList<Object> arrayList = new ArrayList<Object>();

            List<JsonValue> jsonValueList = ((JsonArray)jsonValue).getList();

            for (JsonValue jv : jsonValueList) {

                arrayList.add(fromSingleJson(jv, clazz));
            }
            return arrayList;
        }

        if (clazz.getClassLoader() == this.getClass().getClassLoader()) {

            return fromSingleJson(this.jsonValue, clazz);
            
        }

        return null;
    }

    private Object fromSingleJson(JsonValue jv, Class clazz) {

        Field[] fields = clazz.getDeclaredFields();

        Map<String, Field> alliesNames = prepareFileds(fields);



        return null;
    }

    private Map<String, Field> prepareFileds(Field[] fields) {

        Map<String, Field> map = new HashMap<String, Field>();

        for (Field field : fields) {

            Property property = field.getAnnotation(Property.class);

            String name;

            if (null != property) {
                name = property.value();
            } else {
                name = "";
            }

            if ("".equals(name)) {
                name = field.getName();
            }

            map.put(name, field);
        }

        return map;
    }
}
