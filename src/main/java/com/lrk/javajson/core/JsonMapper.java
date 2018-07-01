package main.java.com.lrk.javajson.core;

import main.java.com.lrk.javajson.annotation.Property;
import main.java.com.lrk.javajson.parse.JsonValue;
import main.java.com.lrk.javajson.parse.elment.JsonArray;
import main.java.com.lrk.javajson.parse.elment.JsonObject;
import main.java.com.lrk.javajson.parse.elment.JsonString;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 *  定制json串的时候，需要的类，json支配器。
 */
public class JsonMapper {

    private JsonValue jsonValue;

    public String toJson() {
        return "";
    }

}
