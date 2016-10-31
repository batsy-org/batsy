package org.batsy.core.service;

import org.batsy.core.annotation.*;
import org.batsy.core.exception.BatsyException;
import org.batsy.core.servlet.ParameterSpecification;
import org.batsy.core.util.PackageUtil;
import org.batsy.core.util.RequestMapUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ufuk on 21.10.2016.
 */
public class AnnotationService implements IBatsyService {

    private static final Logger logger = Logger.getLogger(AnnotationService.class.getSimpleName());

    @Override
    public void start() throws BatsyException {
        try {
            PackageUtil.getClasses("").stream().forEach(clazz -> {
                if (clazz.isAnnotationPresent(RestController.class)) {
                    Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            Annotation annotation = method.getAnnotation(RequestMapping.class);
                            RequestMapping requestMapping = (RequestMapping) annotation;

                            List<ParameterSpecification> params = new ArrayList<>();

                            final Annotation[][] paramAnnotations = method.getParameterAnnotations();
                            final Class[] paramTypes = method.getParameterTypes();
                            for (int i = 0; i < paramAnnotations.length; i++) {
                                for (Annotation a : paramAnnotations[i]) {
                                    //TODO what?
                                    if (a instanceof QueryParam) {
                                        QueryParam param = (QueryParam) a;
                                        params.add(new ParameterSpecification(param.paramName(), param.defaultValue(), ParameterSpecification.Type.QUERY));
                                    } else if (a instanceof PathParam){
                                        PathParam param = (PathParam) a;
                                        if (!requestMapping.path().contains(param.paramName())) {
                                            throw new BatsyException(requestMapping.path() + " has not " + param.paramName());
                                        }
                                        params.add(new ParameterSpecification(param.paramName(), null, ParameterSpecification.Type.PATH));
                                    } else if (a instanceof BodyParam) {
                                        final Class type =  paramTypes[i];
                                        params.add(new ParameterSpecification(null, null, ParameterSpecification.Type.BODY, type));
                                    } else {
                                        throw new BatsyException("Annotation not defined!");
                                    }
                                }
                            }

                            RequestMapUtil.putRequestMap(requestMapping.path(), clazz, method, requestMapping.method(), params);
                            logger.info(requestMapping.path() + " mapped");
                        }
                    });
                }
            });
        } catch (Exception e) {
            throw new BatsyException(e.getMessage(), e);
        }
    }
}
