package com.aurasoftwareinc.java.challenge1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonMarshal {

    private static final String TAG = "JsonMarshal";
    private static final List OBJECT_TYPES = Arrays.asList(Boolean.class, Character.class, Byte.class,
                    Short.class, Integer.class, Long.class, Float.class, Double.class, String.class, JSONObject.class, JSONArray.class);

    /**
     * Marshals JSON object by marshalling for all object's fields. Support fields type: primitive, JSONObject
     *  JSONArrays and implemented JavaMarshalInterface. Ignore null field values
     *
     * @param object The object to be marshaled
     * @return The JSONObject
     */
    public static JSONObject marshalJSON(Object object) {
        JSONObject json = new JSONObject();
        try {
            //Go through all fields in the object
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldObject = field.get(object);
                Class<?> type = field.getType();
                String fieldName = field.getName();

                // Skip if field is null
                if (fieldObject == null) continue;

                if (isJsonType(type)) {
                    json.put(fieldName, fieldObject);
                } else if (type.isArray()) {
                    List list = marshalArray(fieldObject);
                    json.put(field.getName(), new JSONArray(list));
                } else if (JsonMarshalInterface.class.isAssignableFrom(type)) {
                    json.put(fieldName, ((JsonMarshalInterface) fieldObject).marshalJSON());
                }
            }
        } catch (IllegalAccessException | JSONException ex ) {
            Log.e(TAG, "marshalJSON exception: {}", ex);
            return null;
        }
        return json;
    }

    /**
     *  Unmarshal JSON, Parsing a JSONObject to fill up all fields for an input object
     * , JSONArrays and implemented JavaMarshalInterface.
     *
     * @param object the object to be filled as a result of the unmarshaling operation.
     * @param json   the json to be marshaled
     * @return true if marshaling was successful, false otherwise.
     */
    public static boolean unmarshalJSON(Object object, JSONObject json) {
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                String fieldName = field.getName();

                // Skip if json not contains fieldName
                if (!json.has(fieldName)) continue;

                if (isJsonType(type)) {
                    field.set(object, json.get(fieldName));
                } else if (type.isArray()) {
                    JSONArray jsonArray = json.getJSONArray(fieldName);
                    Object array = unmarshalArray(type, jsonArray);
                    field.set(object, array);
                } else if (JsonMarshalInterface.class.isAssignableFrom(type)) {
                    Object o = type.newInstance();
                    ((JsonMarshalInterface) o).unmarshalJSON((JSONObject) json.get(fieldName));
                    field.set(object, o);
                }
            }
        } catch (IllegalAccessException | JSONException | InstantiationException ex ) {
            Log.e(TAG, "unmarshalJSON exception: {}", ex);
            return false;
        }

        return true;
    }

    /**
     *  MarshalArray return the recursively marshal array list items
     *
     * @param array The object array to be marshaled
     * @return A List of Objects that can be directly marshaled to JSON
     */
    private static List<Object> marshalArray(Object array) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < Array.getLength(array); i++) {
            Object arrayObject = Array.get(array, i);
            if (arrayObject != null) {
                if (isJsonType(arrayObject.getClass())) {
                    list.add(arrayObject);
                } else if (arrayObject.getClass().isArray()) {
                    list.add(marshalArray(arrayObject));
                } else if (arrayObject instanceof JsonMarshalInterface) {
                    list.add(((JsonMarshalInterface) arrayObject).marshalJSON());
                }
            }
        }
        return list;
    }

    /**
     * Unmarshals a JSONArray, return array object by unmarshal jsonArray.
     *
     * @param type      type for unmarshall operation
     * @param jsonArray the json array to be unmarshaled
     * @return Object   the array return after unmarshaled
     */
    private static Object unmarshalArray(Class<?> type, JSONArray jsonArray) {
        type = type.getComponentType();
        Object array = Array.newInstance(type, jsonArray.length());
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Object jsonElement = jsonArray.get(i);
                if (jsonElement instanceof JSONArray) {
                    Array.set(array, i, unmarshalArray(type, (JSONArray) jsonElement));
                } else if (isJsonType(type)) {
                    Array.set(array, i, jsonElement);
                } else if (JsonMarshalInterface.class.isAssignableFrom(type)) {
                    JsonMarshalInterface marshalledObject = (JsonMarshalInterface) type.newInstance();
                    marshalledObject.unmarshalJSON((JSONObject) jsonElement);
                    Array.set(array, i, marshalledObject);
                }
            }
            return array;
        } catch (IllegalAccessException | JSONException | InstantiationException ex ) {
            Log.e(TAG, "unmarshalArray exception: {}", ex);
        }
        return null;
    }

    /**
     * Function check if Object is a type can be marshaled to JSON.
     *
     * @param type the type to be checked
     * @return true if can be directly marshaled to JSON, false otherwise.
     */
    private static boolean isJsonType(Class<?> type) {
        return type.isPrimitive() || OBJECT_TYPES.contains(type);
    }
}