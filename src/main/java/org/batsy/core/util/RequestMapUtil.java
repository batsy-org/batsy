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
        if (findAvailableMapping(path) != null) {
            throw new BatsyException("Path already mapped! " + path);
        }
        REQUEST_MAP.put(path, new MethodSpecification(path, clazz, method, requestMethod, parameters));
    }

    public static Map<String, MethodSpecification> getRequestMap() {
        return REQUEST_MAP;
    }

    public static String findAvailableMapping(String mapping) {
        if (REQUEST_MAP.get(mapping) != null) {
            return mapping;
        }
        String availableMapping = null;
        String[] mappingArray = mapping.split(REQUEST_PATH_DELIMETER);
        for (Map.Entry<String, MethodSpecification> entry : getRequestMap().entrySet()) {
            if (entry.getKey().contains(PATH_PARAM_DELIMETER)) {
                String[] mappedArray = entry.getValue().getPath().split(REQUEST_PATH_DELIMETER);
                if (mappingArray.length == mappedArray.length) {
                    for (int i = 0; i < mappingArray.length; i++) {
                        if (!mappedArray[i].contains(PATH_PARAM_DELIMETER)) {
                            if (!mappingArray[i].equals(mappedArray[i])) {
                                availableMapping = null;
                                break;
                            }
                        } else {
                            availableMapping = entry.getKey();
                        }
                    }
                } else {
                    availableMapping = null;
                }
            }
        }

        return availableMapping;
    }
}
