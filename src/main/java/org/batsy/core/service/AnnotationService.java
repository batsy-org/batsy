package org.batsy.core.service;

import org.batsy.core.annotation.RequestMapping;
import org.batsy.core.annotation.RestController;
import org.batsy.core.exception.BatsyException;
import org.batsy.core.service.IBatsyService;
import org.batsy.core.util.PackageUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by ufuk on 21.10.2016.
 */
public class AnnotationService implements IBatsyService{

    @Override
    public void start() throws BatsyException {
        try {
            PackageUtil.getClasses("").stream().forEach(clazz -> {
                if (clazz.isAnnotationPresent(RestController.class)) {
                    Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            Annotation annotation = method.getAnnotation(RequestMapping.class);
                            RequestMapping requestMapping = (RequestMapping) annotation;
                            //try {
                                ServiceLoader.putRequestMap(requestMapping.path(), clazz, method, requestMapping.method());
                                //method.invoke(clazz.newInstance());
                            /*} catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            }*/

                        }
                    });
                }
            });
        } catch (Exception e) {
            throw new BatsyException(e.getMessage(), e);
        }
    }
}
