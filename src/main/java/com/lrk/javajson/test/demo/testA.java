package main.java.com.lrk.javajson.test.demo;

import main.java.com.lrk.javajson.core.JsonBuilder;

public class testA {

    public static void main(String[] args) {

        A a = new A();

        a.setS("aaa");
        a.setSs(1);

        JsonBuilder jsonBuilder = new JsonBuilder();

        try {
            String result = jsonBuilder.toJson(a);
            System.out.println(result);

            A aa = (A)jsonBuilder.fromJson(result, A.class);

            System.out.println(aa);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
