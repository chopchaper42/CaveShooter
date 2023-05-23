package network.udp;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

public class JSONManager
{

    public JSONManager()
    {
    }

    /**
     * Parses a JSON from a byte array
     * @param json The byte array that contains the JSON
     */
    public String parseJSONFromBytes(byte[] json)
    {
        Gson gson = new Gson();
        return new String(json, StandardCharsets.UTF_8);

    }

    /**
     * Parses a JSON from a String
     * @param json The String that contains the JSON
     */
    public Object createJSONObject(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, Object.class);
    }

    /**
     * Creates a JSON
     * @param object Object to be converted to JSON
     */
    public String createJSON(Object object)
    {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
