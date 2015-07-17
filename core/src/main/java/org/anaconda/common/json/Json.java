package org.anaconda.common.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class Json {
    private static Gson gson;
    private static Gson gsonPretty;
    
    static {
        gson = new Gson();
        GsonBuilder builder = new GsonBuilder();
        gsonPretty = builder.setPrettyPrinting().create();
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
    
    public static String toPrettyJson(Object object) {
        return gsonPretty.toJson(object);
    }
    
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return  gson.fromJson(json, classOfT);
    }
    
    public static <T> T fromJson(String json, Type typeOfT) {
        return  gson.fromJson(json, typeOfT);
    }
}
