package main.java.com.lrk.javajson.Main;

import main.java.com.lrk.javajson.Main.parse.JsonToken;
import main.java.com.lrk.javajson.Main.parse.JsonValue;
import main.java.com.lrk.javajson.elment.JsonEmpty;
import main.java.com.lrk.javajson.elment.JsonFalse;
import main.java.com.lrk.javajson.elment.JsonNull;
import main.java.com.lrk.javajson.elment.JsonNumber;
import main.java.com.lrk.javajson.elment.JsonString;
import main.java.com.lrk.javajson.elment.JsonSymbol;
import main.java.com.lrk.javajson.elment.JsonTrue;

public class Parse implements ParseJson {

    private int index = 0;
    private char[] jsonchars;

    public void setJsonschars(String jsonString) {
        this.jsonchars = jsonString.toCharArray();
    }

    public JsonValue parseJson() throws Exception{
//        index = 0;
        if (index < jsonchars.length) {
            JsonValue jv = parse();
            if (jv == null) {
                System.out.println("inner wrong!");
                return null;
            } else if (jv.getToken().equals(JsonToken.BADTOKEN)) {
                System.out.println("bad token ! " + jv.get());
                throw new Exception();
            } else if (jv.getToken().equals(JsonToken.NUMBER)) {
                System.out.println("number is " + jv.get());
                return jv;
            } else if (jv.getToken().equals(JsonToken.STRING)) {
                System.out.println("String is " + jv.get());
                return jv;
            } else {
                System.out.println("the token is " + jv.getToken() + " and the value is " + jv.get());
                return jv;
            }
        }
        return null;
    }

    private JsonValue parse() {
        char[] jsons = this.jsonchars;

        while (index < jsons.length) {
            char next = jsons[index];
            if (next == '\n' || next == '\t' || next == '\f' || next == 32) {
                index++;
                continue;
            }

            JsonValue jv;
            if(isDigital(next)) {
                 jv = parseNumber(jsons);
                 if(jv != null) {
                     return jv;
                 }
            }

            switch(next) {
                case 'n':
                case 'f':
                case 't':
                    jv = parseNullFalseTrue(jsons);
                    break;
                case ':' :
                    jv = new JsonSymbol();
                    jv.setToken(JsonToken.COLON);
                    jv.setSymbol(":");
                    break;
                case '\"' :
                    jv = parseString(jsons);
                    break;
                case '{' :
                    jv = new JsonSymbol();
                    jv.setToken(JsonToken.LB);
                    jv.setSymbol("{");
                    break;
                case '}' :
                    jv = new JsonSymbol();
                    jv.setToken(JsonToken.RB);
                    jv.setSymbol("}");
                    break;
                case '[' :
                    jv = new JsonSymbol();
                    jv.setToken(JsonToken.LL);
                    jv.setSymbol("[");
                    break;
                case ']' :
                    jv = new JsonSymbol();
                    jv.setToken(JsonToken.RL);
                    jv.setSymbol("]");
                    break;
                case ',' :
                    jv = new JsonSymbol();
                    jv.setToken(JsonToken.COMMA);
                    jv.setSymbol(",");
                    break;
                default :
                    jv = new JsonEmpty();
                    jv.setToken(JsonToken.BADTOKEN);
                    jv.setSymbol(next + "");
            }
            index++;
            return jv;
        }
        return new JsonEmpty();

    }

    public boolean isMap() {
        skip();
        return '{' == this.jsonchars[index];
    }

    public boolean isArray() {
        skip();
        return '[' == this.jsonchars[index];
    }

    public boolean isMapEnd() {
        skip();
        return '}' == this.jsonchars[index];
    }

    public boolean isArrayEnd() {
        skip();
        return ']' == this.jsonchars[index];
    }

    public boolean isComma() {
        skip();
        return ',' == this.jsonchars[index];
    }

    private void skip() {
        while (index < jsonchars.length) {
            char next = jsonchars[index];
            if (next == '\n' || next == '\t' || next == '\f' || next == 32) {
                index++;
            } else {
                break;
            }
        }
    }

    private JsonValue parseNumber(char[] jsons) {

        String value = jsons[index] + "";

        if (value.equals("-")) {
            value += jsons[++index] + "";
        }

        index++;

        assert (isDigital(jsons[index]));

        while (index < jsons.length) {
            if (jsons[index] == '.') {
                value += ".";
                index++;
                continue;
            }

            if (isDigital(jsons[index])) {
                value += jsons[index++];
            } else {
                index--;
                break;
            }
        }

        index++;

        JsonValue jv = new JsonNumber();
        jv.setToken(JsonToken.NUMBER);
        jv.setNumber(value);
        return jv;
    }

    private boolean isDigital(char x) {

        return (48 <= x && x <= 57) || x == '-';

    }

    private JsonValue parseNullFalseTrue(char[] jsons) {
        JsonValue jv = null;
        if ((jsons[index] == 'n')
                && (jsons[index + 1] == 'u')
                && (jsons[index + 2] == 'l')
                && (jsons[index + 3] == 'l')) {
            jv = new JsonNull();
            jv.setToken(JsonToken.NULL);
            index += 4;
        } else if ((jsons[index] == 't')
                && (jsons[index + 1] == 'r')
                && (jsons[index + 2] == 'u')
                && (jsons[index + 3] == 'e')) {
            jv = new JsonTrue();
            jv.setToken(JsonToken.TRUE);
            index += 4;
        } else if ((jsons[index] == 'f')
                && (jsons[index + 1] == 'a')
                && (jsons[index + 2] == 'l')
                && (jsons[index + 3] == 's')
                && (jsons[index + 4] == 'e')) {
            jv = new JsonFalse();
            jv.setToken(JsonToken.FALSE);
            index += 5;
        }
        return jv;
    }

    private JsonValue parseString(char[] jsons) {
        assert(isString(jsons[index]));
        String value = jsons[index++] + "";
        while(index < jsons.length) {
            if (jsons[index] != '"') {
                value += jsons[index++];
            } else {
//                index++;
                value += "\"";
                break;
            }
        }
        JsonValue jv = new JsonString();
        jv.setToken(JsonToken.STRING);
        jv.setString(value);
        return jv;
    }

    private boolean isString(char x) {
        return '"' == x;
    }
}