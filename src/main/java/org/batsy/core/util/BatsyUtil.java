package org.batsy.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ufuk on 24.10.2016.
 */
public final class BatsyUtil {

    public static String getRequestPath(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.endsWith("/")) {
            return path.substring(0, path.length()-1);
        }
        return path;
    }

    public static boolean isRequestMethodValid(HttpServletRequest request, String requestMethod) {
        return request.getMethod().equalsIgnoreCase(requestMethod);
    }

}
