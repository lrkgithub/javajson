package main.java.com.lrk.javajson.core;


import main.java.com.lrk.javajson.parse.GeneralLjson;

import java.io.InputStream;

public class JsonBuilder {

    private static Ljson maker = new GeneralLjson();



    public Ljson createLjson() {

        return maker;

    }
}
