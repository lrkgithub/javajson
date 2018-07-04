package main.java.com.lrk.javajson.parse;

import main.java.com.lrk.javajson.annotation.Ignore;
import main.java.com.lrk.javajson.annotation.Property;
import main.java.com.lrk.javajson.core.Ljson;
import main.java.com.lrk.javajson.core.Parse;
import main.java.com.lrk.javajson.core.Syntax;
import main.java.com.lrk.javajson.parse.elment.JsonArray;
import main.java.com.lrk.javajson.parse.elment.JsonFalse;
import main.java.com.lrk.javajson.parse.elment.JsonNull;
import main.java.com.lrk.javajson.parse.elment.JsonNumber;
import main.java.com.lrk.javajson.parse.elment.JsonMap;
import main.java.com.lrk.javajson.parse.elment.JsonString;
import main.java.com.lrk.javajson.parse.elment.JsonTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GeneralLjson implements Ljson {

    public String toJson(Object object) throws IllegalAccessException {

//        JsonMapper mapper = new JsonMapper();
        Class<?> cls = object.getClass();

        if (List.class.isAssignableFrom(cls)) {

            JsonValue jv = toJsonArray((List)object);

            return jv.get().toString();

        } else {
            /* 这里怎么写比较好 */
//        if (cls.getClass().getClassLoader().getClass() == this.getClass().getClassLoader().getClass()) {

            JsonValue jv = toJsonSingle(object);

            return jv.get().toString();

        }
//        return mapper.toJson();
    }

    public Object fromJson(String jsonString, Class clazz) throws Exception {

        Parse parseJson = new ParseJson();
        Syntax syntaxParse = new SyntaxParse(parseJson);

        syntaxParse.setStart(jsonString);

        JsonValue jv = syntaxParse.syntaxJson();

        return transJson(jv, clazz);
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

        JsonMap jsonMap = new JsonMap();
        Class<?> cls = object.getClass();

        if (List.class.isAssignableFrom(cls)) {

            return toJsonArray((List)object);

        }

        Field[] fields = cls.getDeclaredFields();

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

            JsonString jvKey = ElementMaker.jsonString();
            jvKey.setString(propertyName);

            Class<?> fieldType = field.getType();

            field.setAccessible(true);

            if (null == field.get(object)) {

                jsonMap.add(jvKey, new JsonNull());

                continue;
            }

            if (fieldType == String.class) {

                JsonString jsonString = new JsonString();

                field.setAccessible(true);
                jsonString.setString((String) field.get(object));

                jsonMap.add(jvKey, jsonString);

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

                jsonMap.add(jvKey, jvValue);

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

                jsonMap.add(jvKey, jsonNumber);

                continue;
            }

            if(List.class.isAssignableFrom(fieldType)) {

                JsonValue jvValue = toJsonArray((List)field.get(object));

                jsonMap.add(jvKey, jvValue);
            }

        }

        return jsonMap;
    }

    private Object transJson(JsonValue jsonValue, Class clazz) {

        if (jsonValue.getClass() == JsonArray.class) {

            ArrayList<Object> arrayList = new ArrayList<Object>();

            List<JsonValue> jsonValueList = ((JsonArray)jsonValue).getList();

            for (JsonValue jv : jsonValueList) {

                try {
                    arrayList.add(fromSingleJson(jv, clazz));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            return arrayList;
        }

        if (clazz.getClassLoader() == this.getClass().getClassLoader()) {

            try {
                return fromSingleJson(jsonValue, clazz);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    private Object fromSingleJson(JsonValue jv, Class clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {

        if (jv instanceof JsonMap) {

            Field[] fields = clazz.getDeclaredFields();

            Map<String, Field> alliesNames = prepareFileds(fields);

            Iterator<Map.Entry<String, Field>> it1 = alliesNames.entrySet().iterator();



            Object obj = clazz.getConstructors()[0].newInstance();

            while (it1.hasNext()) {

                Map.Entry<String, Field> entryC = it1.next();

                Set<Map.Entry<JsonString, JsonValue>> entrySet= ((JsonMap)jv).getMap().entrySet();

                for (Map.Entry<JsonString, JsonValue> entryJ : entrySet) {

                    if (entryC.getKey().equals((entryJ.getKey()).getString())) {

                        Field target = entryC.getValue();

                        target.setAccessible(true);

                        JsonValue value = entryJ.getValue();

                        Class targetClass = target.getType();

                        try {
                            target.set(obj, transForObj(value, targetClass));
                        } catch (IllegalArgumentException e) {
                            target.set(obj, null);
                        }

                    }

                }

            }
            return obj;
        }

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

    private Object transForObj(JsonValue targetValue, Class targetClass) {

        if (JsonString.class == targetValue.getClass()) {
            return transJsonString(((JsonString)targetValue).get(), targetClass);
        }

        if (JsonTrue.class == targetValue.getClass()) {
            return transJsonTrue(targetClass);
        }

        if (JsonFalse.class == targetValue.getClass()) {
             return transJsonFalse(targetClass);
        }

        if (JsonNull.class == targetValue.getClass()) {
            return transJsonNull();
        }

        if (JsonNumber.class == targetValue.getClass()) {
            return transJsonNumber(((JsonNumber)targetValue).get(), targetClass);
        }

        if (JsonArray.class == targetValue.getClass()) {
            return transJson(targetValue, targetClass);
        }
        return null;
    }

    private Object transJsonString(String value, Class targetClass) {

        if (String.class == targetClass) {

            return value;

        }


        if (Double.class == targetClass || double.class == targetClass) {

            return Double.parseDouble(value);

        }

        if (Long.class == targetClass || long.class == targetClass) {

            return Long.parseLong(value);

        }

        if (Integer.class == targetClass || int.class == targetClass) {

            return Integer.parseInt(value);

        }

        if (Short.class == targetClass || short.class == targetClass) {

            return Short.parseShort(value);

        }

        if (Byte.class == targetClass || byte.class == targetClass) {

            return Byte.parseByte(value);

        }

        if (Float.class == targetClass || float.class == targetClass) {

            return Float.parseFloat(value);

        }

        if (Character.class == targetClass) {
            if (1 == value.length()) {
                return value.toCharArray()[0];
            }
        }

        if (Boolean.class == targetClass) {

            if ("true".equals(value)) {
                return true;
            } else if ("false".equals(value)) {
                return false;
            }

        }
        return null;
    }

    private Object transJsonTrue(Class targetClass) {

        if (Boolean.class == targetClass || boolean.class == targetClass) {
            return true;
        }

        if (String.class == targetClass) {
            return "true";
        }

        return null;
    }

    private Object transJsonFalse(Class targetClass) {

        if (Boolean.class == targetClass || boolean.class == targetClass) {
            return false;
        }

        if (String.class == targetClass) {
            return "false";
        }

        return null;
    }

    private Object transJsonNull() {

        return null;

    }

    private Object transJsonNumber(String value, Class targetClass) {

        if (String.class == targetClass) {

            return value;

        }

        if (Double.class == targetClass || double.class == targetClass) {

            return Double.parseDouble(value);

        }

        if (Long.class == targetClass || long.class == targetClass) {

            return Long.parseLong(value);

        }

        if (Integer.class == targetClass || int.class == targetClass) {

            return Integer.parseInt(value);

        }

        if (Short.class == targetClass || short.class == targetClass) {

            return Short.parseShort(value);

        }

        if (Byte.class == targetClass || byte.class == targetClass) {

            return Byte.parseByte(value);

        }

        if (Float.class == targetClass || float.class == targetClass) {

            return Float.parseFloat(value);

        }

        return null;
    }
}
