package main.java.com.lrk.javajson.parse;

import main.java.com.lrk.javajson.annotation.Ignore;
import main.java.com.lrk.javajson.annotation.Property;
import main.java.com.lrk.javajson.core.JsonMapper;
import main.java.com.lrk.javajson.core.Maker;
import main.java.com.lrk.javajson.core.Parse;
import main.java.com.lrk.javajson.core.Syntax;
import main.java.com.lrk.javajson.parse.elment.JsonArray;
import main.java.com.lrk.javajson.parse.elment.JsonFalse;
import main.java.com.lrk.javajson.parse.elment.JsonNull;
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

        } else if (cls.getClass().getClassLoader().getClass() == this.getClass().getClassLoader().getClass()) {

            /* 这里怎么写比较好 */
//        if (cls.getClass().getClassLoader().getClass() == this.getClass().getClassLoader().getClass()) {

            JsonValue jv = toJsonSingle(object);

            mapper.setMainValue(jv);
        }
        return mapper.toJson();
    }

    private JsonValue toJsonArray(List list) throws IllegalAccessException {

        JsonArray jsonArray = new JsonArray();

        for (Object obj : list) {

            JsonValue jv = toJsonSingle(obj);

            if (null != jv) {
                jsonArray.add(jv);
            }
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

            Ignore ignore = field.getAnnotation(Ignore.class);

            if (null != ignore) {
                continue;
            }

            Property property = field.getAnnotation(Property.class);

            String propertyName;
            String alliesName;

            if (null != property) {
                alliesName = property.value();
            } else {
                alliesName = "";
            }


            if ("".equals(alliesName)) {
                propertyName = field.getName();
            } else {
                propertyName = alliesName;
            }

            JsonString jvKey = new JsonString();
            jvKey.setString(propertyName);

            Class<?> fieldType = field.getType();

            field.setAccessible(true);

            if (null == field.get(object)) {

                jsonObject.add(jvKey, new JsonNull());

                continue;
            }

            if (fieldType == String.class) {

                JsonString jsonString = new JsonString();
                jsonString.setString((String) field.get(object));

                jsonObject.add(jvKey, jsonString);

                continue;
            }

            if (fieldType == Boolean.class || fieldType == boolean.class) {

                boolean bool =  (Boolean)field.get(object);

                JsonValue jvValue;

                if (bool) {
                    jvValue = new JsonTrue();
                } else {
                    jvValue = new JsonFalse();
                }

                jsonObject.add(jvKey, jvValue);

                continue;
            }

            if (Number.class.isAssignableFrom(fieldType)
                    || fieldType == double.class
                    || fieldType == long.class
                    || fieldType == short.class
                    || fieldType == int.class
                    || fieldType == byte.class
                    || fieldType == float.class) {

                JsonNumber jsonNumber = new JsonNumber();
                jsonNumber.setNumber(field.get(object) + "");

                jsonObject.add(jvKey, jsonNumber);

                continue;
            }

            if(List.class.isAssignableFrom(fieldType)) {

                JsonValue jvValue = toJsonArray((List)field.get(object));

                jsonObject.add(jvKey, jvValue);
            }

        }

        return jsonObject;
    }


    public Object fromJson(String jsonString, Class clazz) throws Exception {

        JsonMapper mapper = new JsonMapper();

        Parse parseJson = new ParseJson();
        Syntax syntaxParse = new SyntaxParse(parseJson);

        syntaxParse.setStart(jsonString);

        JsonValue jv = syntaxParse.syntaxJson();

        mapper.setMainValue(jv);

        return mapper.fromJson(clazz);
    }
}
