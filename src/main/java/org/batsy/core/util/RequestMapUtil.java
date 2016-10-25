package org.batsy.core.util;

import org.batsy.core.exception.BatsyException;
import org.batsy.core.http.RequestMethod;
import org.batsy.core.servlet.MethodSpecification;
import org.batsy.core.servlet.ParameterSpecification;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ufuk on 25.10.2016.
 */
public final class RequestMapUtil {
    private static Map<String, MethodSpecification> REQUEST_MAP = new HashMap<>();

    public static void putRequestMap(String path, Class clazz, Method method, RequestMethod requestMethod, List<ParameterSpecification> parameters) {
        if (isPathContains(path)) {
            throw new BatsyException("Path already mapped! " + path);
        }
        REQUEST_MAP.put(path, new MethodSpecification(clazz, method, requestMethod, parameters));
    }

    private static boolean isPathContains(String path) {
        return REQUEST_MAP.containsKey(path);
    }

    public static Map<String, MethodSpecification> getRequestMap() {
        return REQUEST_MAP;
    }
}
