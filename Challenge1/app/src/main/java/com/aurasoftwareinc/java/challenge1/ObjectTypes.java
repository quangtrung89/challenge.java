package com.aurasoftwareinc.java.challenge1;

import org.json.JSONObject;

public class ObjectTypes implements JsonMarshalInterface
{
    public Byte objByte;
    private Short objShort;
    public Integer objInt;
    private Long objLong;

    private Float objFloat;
    private Double objDouble;
    private Double objDoubleNull;

    public Boolean objBoolean;
    public Boolean objBooleanNull;

    public String objString;
    public String objStringNull;

    public Byte[] objByteArray;
    public Byte[] objByteArrayNull;

    public void populateTestData()
    {
        objByte = 88;
        objShort = 8888;
        objInt = 47114711;
        objLong = 12345678901234567L;
        objFloat = 42.5f;
        objDouble = 42.123456789012345;
        objBoolean = true;
        objString = "abcdefghijk";
        objByteArray = new Byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 14};
    }

    @Override
    public JSONObject marshalJSON()
    {
        return JsonMarshal.marshalJSON(this);
    }

    @Override
    public boolean unmarshalJSON(JSONObject json)
    {
        return JsonMarshal.unmarshalJSON(this, json);
    }
}
