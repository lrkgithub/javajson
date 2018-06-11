package main.java.com.lrk.javajson.parse;

import main.java.com.lrk.javajson.annotation.Property;
import main.java.com.lrk.javajson.core.JsonMapper;
import main.java.com.lrk.javajson.core.Maker;
import main.java.com.lrk.javajson.parse.elment.JsonArray;
import main.java.com.lrk.javajson.parse.elment.JsonFalse;
import main.java.com.lrk.javajson.parse.elment.JsonNumber;
import main.java.com.lrk.javajson.parse.elment.JsonObject;
import main.java.com.lrk.javajson.parse.elment.JsonString;
import main.java.com.lrk.javajson.parse.elment.JsonTrue;

import java.lang.reflect.Field;
import java.util.List;

public class JsonMaker implements Maker {

    private Class clazz;

    public String toJson(Object object) throws IllegalAccessException {

        JsonMapper mapper = new JsonMapper();
        Class<?> cls = object.getClass();

        if (List.class.isAssignableFrom(cls)) {

            JsonValue jv = toJsonArray((List)object);

            mapper.setMainValue(jv);

        }

        return mapper.get();
    }

    private JsonValue toJsonArray(List list) throws IllegalAccessException {

        JsonArray jsonArray = new JsonArray();

        for (Object obj : list) {
            jsonArray.add(toJsonSingle(obj));
        }

        return jsonArray;
    }

    private JsonValue toJsonSingle(Object object) throws IllegalAccessException {

        JsonObject jsonObject = new JsonObject();
        Class<?> cls = object.getClass();

        if (List.class.isAssignableFrom(cls)) {

            return toJsonArray((List)object);

        }

        Field[] fields = cls.getFields();

        for (Field field : fields) {

            Property property = field.getAnnotation(Property.class);

            String propertyName;
            String alliesName = property.value();

            if ("".equals(alliesName)) {
                propertyName = field.getName();
            } else {
                propertyName = alliesName;
            }

            JsonString jvKey = new JsonString();
            jvKey.setString(propertyName);

            Class<?> fieldType = field.getType();

            if (fieldType == String.class) {

                JsonString jsonString = new JsonString();
                jsonString.setString((String) field.get(object));

                jsonObject.add(jvKey, jsonString);

            } else if (fieldType == Boolean.class || fieldType == boolean.class) {

                boolean bool =  (Boolean)field.get(object);

                JsonValue jvValue;

                if (bool) {
                    jvValue = new JsonTrue();
                } else {
                    jvValue = new JsonFalse();
                }

                jsonObject.add(jvKey, jvValue);

            } else if (Number.class.isAssignableFrom(fieldType)
                    || fieldType == double.class
                    || fieldType == long.class
                    || fieldType == short.class
                    || fieldType == int.class
                    || fieldType == byte.class
                    || fieldType == float.class) {

                JsonNumber jsonNumber = new JsonNumber();
                jsonNumber.setNumber(field.get(object) + "");

                jsonObject.add(jvKey, jsonNumber);

            }

        }
return null;
    }
}
