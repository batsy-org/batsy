package org.batsy.core.service;

import org.batsy.core.annotation.QueryParam;
import org.batsy.core.annotation.RequestMapping;
import org.batsy.core.annotation.RestController;
import org.batsy.core.exception.BatsyException;
import org.batsy.core.servlet.ParameterSpecification;
import org.batsy.core.util.PackageUtil;
import org.batsy.core.util.RequestMapUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

                            List<ParameterSpecification> params = new ArrayList<>();
                            Arrays.stream(method.getParameterAnnotations()).forEach(p -> {
                                if (p[0] instanceof QueryParam) {
                                    QueryParam queryParam = (QueryParam) p[0];
                                    params.add(new ParameterSpecification(queryParam.paramName(), queryParam.defaultValue(), ParameterSpecification.Type.QUERY));
                                } else {
                                    //TODO PATH PARAM
                                }
                            });

                            RequestMapUtil.putRequestMap(requestMapping.path(), clazz, method, requestMapping.method(), params);

                        }
                    });
                }
            });
        } catch (Exception e) {
            throw new BatsyException(e.getMessage(), e);
        }
    }
}
