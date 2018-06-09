public interface ParseJson {

    public void setJsonschars(String jsonString);

    JsonValue parseJson() throws Exception;

}
