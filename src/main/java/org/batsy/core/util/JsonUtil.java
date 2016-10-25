package org.batsy.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ufuk on 25.10.2016.
 */
public final class JsonUtil {

    private static Gson gson = new GsonBuilder().create();

    public static <T> String toJson(T t) {
        return gson.toJson(t);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
