package org.batsy.core.service;

import org.batsy.core.annotation.RequestMapping;
import org.batsy.core.annotation.RestController;
import org.batsy.core.exception.BatsyException;
import org.batsy.core.util.PackageUtil;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * Created by ufuk on 21.10.2016.
 */
public class AnnotationService implements IBatsyService {

    //TODO
    @Override
    public void start() throws BatsyException {
        try {
            PackageUtil.getClasses("").stream().forEach(clazz -> {
                if (clazz.isAnnotationPresent(RestController.class)) {
                    Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            Annotation annotation = method.getAnnotation(RequestMapping.class);
                            RequestMapping requestMapping = (RequestMapping) annotation;


                            ServiceLoader.putRequestMap(requestMapping.path(), clazz, method, requestMapping.method());

                        }
                    });
                }
            });
        } catch (Exception e) {
            throw new BatsyException(e.getMessage(), e);
        }
    }
}
