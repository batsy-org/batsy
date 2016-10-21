package org.batsy.core.service;

import org.batsy.core.exception.BatsyException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ufuk on 21.10.2016.
 */
public class ServiceLoader {

    private static final Logger logger = Logger.getLogger(ServiceLoader.class.getSimpleName());

    private static List<IBatsyService> SERVICES = new ArrayList<>();

    public static void addServiceRunner(IBatsyService service) {
        SERVICES.add(service);
    }

    public static void runAll() throws BatsyException {
        for (IBatsyService service : SERVICES) {
            service.start();
            logger.info(service.getClass().getName() + " started.");
        }
    }
}
