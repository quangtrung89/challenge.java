package com.aurasoftwareinc.java.challenge1;

import android.support.annotation.Nullable;

import org.json.JSONObject;

public class JsonMarshal
{
    @Nullable
    public static JSONObject marshalJSON(Object object)
    {
        JSONObject json = new JSONObject();

        try
        {
            json.put("test", "dummy");
        }
        catch (Exception ignore)
        {
        }

        return json;
    }

    public static boolean unmarshalJSON(Object object, JSONObject json)
    {
        return true;
    }
}