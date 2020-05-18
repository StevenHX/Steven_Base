package com.hx.steven.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonHelp {
    private static Gson gson = new GsonBuilder().create();

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return gson.fromJson(jsonStr, objClass);
    }

    public static <T> T json2Bean(String jsonStr, Type objClass) {
        return gson.fromJson(jsonStr, objClass);
    }

    public static String toJson(Object obj) {
        String reString = gson.toJson(obj);
        return reString;
    }
}
