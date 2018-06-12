package main.java.com.lrk.javajson.core;


import main.java.com.lrk.javajson.parse.JsonMaker;

import java.io.InputStream;

public class JsonBuilder {

    private Maker maker;

    public String toJson(Object object) throws IllegalAccessException {

        this.maker = new JsonMaker();

        return maker.toJson(object);
    }

    public Object fromJson(String jsonString, Class clazz) throws Exception {

        this.maker = new JsonMaker();

        return maker.fromJson(jsonString, clazz);
    }

    public Object formJson(InputStream in, Class clazz) throws Exception {

        byte[] buffer = new byte[1024];
        int i = -1;

        StringBuilder sb = new StringBuilder(1024);

        while ((i = in.read(buffer)) != -1) {

            sb.append(new String(buffer, 0, i));

        }

        return fromJson(sb.toString(), clazz);
    }

}
