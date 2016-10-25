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


}
