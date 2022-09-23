package com.obvious.nasagallery.util;


import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GSONBuilder {
    public static <T> String getString(T object) {
        return new Gson().toJson(object);
    }

    public static <T> T getObject(String data, Class<T> classType) {
        try {
            return new Gson().fromJson(data, classType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject(String data, Type type) {
        try {
            return new Gson().fromJson(data, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

