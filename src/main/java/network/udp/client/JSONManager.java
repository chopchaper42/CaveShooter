package network.udp.client;

import com.google.gson.Gson;

public class JSONManager
{
    public static String convertObjectToJson(UpdatedState updatedState)
    {
        Gson gson = new Gson();
        return gson.toJson(updatedState);
    }

    public static UpdatedState convertJsonToObject(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, UpdatedState.class);
    }

}
