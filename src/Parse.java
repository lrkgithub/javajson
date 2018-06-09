
public class Parse implements ParseJson {

    private int index;
    private char[] jsonchars;

    public void setJsonschars(String jsonString) {
        this.jsonchars = jsonString.toCharArray();
    }

    public JsonValue parseJson() throws Exception{
        index = 0;
        while (index < jsonchars.length) {
            JsonValue jv = parse(jsonchars);
            if (jv == null) {
                System.out.println("inner wrong!");
            } else if (jv.getToken().equals(JsonToken.BADTOKEN)) {
                System.out.println("bad token ! " + jv.getValue());
                throw new Exception();
            } else if (jv.getToken().equals(JsonToken.NUMBER)) {
                System.out.println("number is " + jv.getNumber());
//                return jv;
            } else if (jv.getToken().equals(JsonToken.STRING)) {
                System.out.println("String is " + jv.getValue());
//                return jv;
            } else {
                System.out.println("the token is " + jv.getValue());
//                return jv;
            }
        }
        return null;
    }

    private JsonValue parse(char[] jsons) throws Exception{
        while (index < jsons.length) {
            char next = jsons[index];
            if (next == '\n' || next == '\t' || next == '\f') {
                index++;
                continue;
            }
            JsonValue jv = new JsonValue();
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
                    jv.setToken(JsonToken.COLON);
                    jv.setValue(":");
                    break;
                case '\"' :
                    jv = parseString(jsons);
                    break;
                case '{' :
                    jv.setToken(JsonToken.LB);
                    jv.setValue("{");
                    break;
                case '}' :
                    jv.setToken(JsonToken.RB);
                    jv.setValue("}");
                    break;
                case '[' :
                    jv.setToken(JsonToken.LL);
                    jv.setValue("[");
                    break;
                case ']' :
                    jv.setToken(JsonToken.RL);
                    jv.setValue("]");
                    break;
                case ',' :
                    jv.setToken(JsonToken.COMMA);
                    jv.setValue(",");
                    break;
                default :
                    jv.setToken(JsonToken.BADTOKEN);
                    jv.setValue(next + "");
            }
            index++;
            return jv;
        }
        return null;

    }

    private JsonValue parseNumber(char[] jsons) throws Exception {

        boolean negative = false;
        String value = jsons[index] + "";
        int start = index;
        Double number = null;

        if (value.equals("-")) {
            negative = true;
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

        if (negative) {
            if (index >= start + 1) {
                number = Double.parseDouble(value);
            }
        } else {
            if (index >= start) {
                number = Double.parseDouble(value);
            }
        }

        index++;

        if (number != null) {
            JsonValue jv = new JsonValue();
            jv.setToken(JsonToken.NUMBER);
            jv.setNumber(number);
            return jv;
        } else {
            JsonValue jv = new JsonValue();
            jv.setToken(JsonToken.BADTOKEN);
            jv.setValue(jsons[index] + "");
            return jv;
        }
    }

    private boolean isDigital(char x) {
        if ((48 <= x && x <= 57) || (x == '-')) {
            return true;
        } else {
            return false;
        }
    }

    private JsonValue parseNullFalseTrue(char[] jsons) {
        JsonValue jv = new JsonValue();
        if ((jsons[index] == 'n')
                && (jsons[index + 1] == 'u')
                && (jsons[index + 2] == 'l')
                && (jsons[index + 3] == 'l')) {
            jv.setToken(JsonToken.NULL);
            jv.setValue("null");
            index += 4;
        } else if ((jsons[index] == 't')
                && (jsons[index + 1] == 'r')
                && (jsons[index + 2] == 'u')
                && (jsons[index + 3] == 'e')) {
           jv.setToken(JsonToken.TRUE);
           jv.setValue("true");
           index += 4;
        } else if ((jsons[index] == 'f')
                && (jsons[index + 1] == 'a')
                && (jsons[index + 2] == 'l')
                && (jsons[index + 3] == 's')
                && (jsons[index + 4] == 'e')) {
            jv.setToken(JsonToken.FALSE);
            jv.setValue("false");
            index += 5;
        } else {
            jv = null;
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
        JsonValue jv = new JsonValue();
        jv.setToken(JsonToken.STRING);
        jv.setValue(value);
        return jv;
    }

    private boolean isString(char x) {
        return '"' == x;
    }
}