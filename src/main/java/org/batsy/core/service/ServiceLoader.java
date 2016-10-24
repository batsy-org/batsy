package org.batsy.core.service;

import org.batsy.core.exception.BatsyException;
import org.batsy.core.http.RequestMethod;
import org.batsy.core.servlet.MethodSpecification;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by ufuk on 21.10.2016.
 */
public class ServiceLoader {

    private static final Logger logger = Logger.getLogger(ServiceLoader.class.getSimpleName());

    private static List<IBatsyService> SERVICES = new ArrayList<>();

    private static Map<String, MethodSpecification> REQUEST_MAP = new HashMap<>();

    static {
        addBatsyService(new PropertyService());
        addBatsyService(new AnnotationService());
        addBatsyService(new WebServerService());
    }

    public static void addBatsyService(IBatsyService service) {
        SERVICES.add(service);
    }

    public static void runAll() throws BatsyException {
        for (IBatsyService service : SERVICES) {
            service.start();
            logger.info(service.getClass().getName() + " started.");
        }
    }

    public static void putRequestMap(String path, Class clazz, Method method, RequestMethod requestMethod) {
        if (isPathContains(path)) {
            throw new BatsyException("Path already mapped! " + path);
        }
        REQUEST_MAP.put(path, new MethodSpecification(clazz, method, requestMethod));
    }

    private static boolean isPathContains(String path) {
        return REQUEST_MAP.containsKey(path);
    }

    public static Map<String, MethodSpecification> getRequestMap() {
        return REQUEST_MAP;
    }

}
