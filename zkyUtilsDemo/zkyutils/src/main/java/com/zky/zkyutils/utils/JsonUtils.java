package com.zky.zkyutils.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static <T> String convertObjectToJson(T objects) {
        return getGson().toJson(objects);
    }

    public static <T> T convertJsonToObject(String expected, Type type) {
        return getGson().fromJson(expected, type);
    }

    private static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        return gsonBuilder.create();
    }

    public static <T> List<T> convertObjectListFromJson(String json, TypeToken token) {
        List<T> list = new ArrayList<T>();

        if (StringUtils.isBlank(json)) {
            return list;
        }

        try {
            list = getGson().fromJson(json, token.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}