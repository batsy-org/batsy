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
    public static final String PATH_PARAM_DELIMETER = "{";
    public static final String REQUEST_PATH_DELIMETER = "/";

    private static Map<String, MethodSpecification> REQUEST_MAP = new HashMap<>();

    public static void putRequestMap(String path, Class clazz, Method method, RequestMethod requestMethod, List<ParameterSpecification> parameters) {
        if (contains(path)) {
            throw new BatsyException("Path already mapped! " + path);
        }
        REQUEST_MAP.put(path, new MethodSpecification(path, clazz, method, requestMethod, parameters));
    }

    private static boolean contains(String path) {
        if (REQUEST_MAP.containsKey(path)) {
            return true;
        }
        return  isPathContains(path);
    }

    //TODO rf
    private static boolean isPathContains(String path) {
        if (path.contains(PATH_PARAM_DELIMETER)) {
            boolean isContains = false;
            for (Map.Entry<String, MethodSpecification> entry : getRequestMap().entrySet()) {
                if (entry.getKey().contains(PATH_PARAM_DELIMETER)) {
                    String[] pathArray = path.split(REQUEST_PATH_DELIMETER);
                    String[] mappedArray = entry.getValue().getPath().split(REQUEST_PATH_DELIMETER);
                    if (pathArray.length == mappedArray.length) {
                        for (int i = 0; i < pathArray.length; i++) {
                            if (!pathArray[i].contains(PATH_PARAM_DELIMETER)) {
                                if (!pathArray[i].equals(mappedArray[i])) {
                                    isContains = false;
                                    break;
                                }
                            } else {
                                isContains = true;
                            }
                        }
                    } else {
                        isContains = true;
                    }
                }
            }
            return isContains;
        }
        return false;
    }

    public static Map<String, MethodSpecification> getRequestMap() {
        return REQUEST_MAP;
    }
}
