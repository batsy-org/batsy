package org.batsy.core.util;

import org.batsy.core.servlet.MethodSpecification;
import org.batsy.core.servlet.ParameterSpecification;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.batsy.core.util.RequestMapUtil.PATH_PARAM_DELIMETER;
import static org.batsy.core.util.RequestMapUtil.REQUEST_PATH_DELIMETER;

/**
 * Created by ufuk on 24.10.2016.
 */
public final class BatsyUtil {

    public static String getRequestPath(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.endsWith("/")) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    public static boolean isRequestMethodValid(HttpServletRequest request, String requestMethod) {
        return request.getMethod().equalsIgnoreCase(requestMethod);
    }

    public static MethodSpecification findMethodSpecificationByRequestPath(String requestPath) {
        MethodSpecification specification = RequestMapUtil.getRequestMap().get(requestPath);//first no path variable
        if (specification == null) {//then path variable
            for (Map.Entry<String, MethodSpecification> entry : RequestMapUtil.getRequestMap().entrySet()) {
                String[] pathArray = entry.getValue().getPath().split(REQUEST_PATH_DELIMETER);
                String[] requestPathArray = requestPath.split(REQUEST_PATH_DELIMETER);
                if (pathArray.length == requestPathArray.length) {
                    boolean isMatched = true;
                    for (int i = 0; i < pathArray.length; i++) {
                        if (!pathArray[i].contains(PATH_PARAM_DELIMETER)) {
                            if (!pathArray[i].equals(requestPathArray[i])) {
                                isMatched = false;
                            }
                        }
                    }
                    if (isMatched) {
                        return entry.getValue();
                    }
                }
            }

        }
        return specification;
    }

    public static String[] getMethodParams(MethodSpecification specification) {
        String[] params = new String[specification.getParameters().size()];
        for (int i = 0; i < params.length; i++) {
            params[i] = specification.getParameters().get(i).getDefaultValue();
        }
        return params;
    }

    public static void findAndSetParams(HttpServletRequest request, MethodSpecification specification) {
        specification.getParameters().forEach(p -> {
            if (p.getType() == ParameterSpecification.Type.QUERY) {
                String value = request.getParameter(p.getParamName());
                p.setDefaultValue(value);
            } else if (p.getType() == ParameterSpecification.Type.PATH) {
                String[] requestPathArray = BatsyUtil.getRequestPath(request).split(REQUEST_PATH_DELIMETER);
                String[] pathArray = specification.getPath().split(REQUEST_PATH_DELIMETER);
                for (int i = 0; i < pathArray.length; i++) {
                    if (pathArray[i].contains("{")) {
                        p.setDefaultValue(requestPathArray[i]);
                    }
                }
            }
        });
    }

}
