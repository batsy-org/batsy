package org.batsy.core.servlet;

import org.batsy.core.exception.BatsyException;
import org.batsy.core.service.ServiceLoader;
import org.batsy.core.util.BatsyUtil;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

/**
 * Created by ufuk on 22.10.2016.
 */
public class BatsyServlet extends GenericServlet {
    private static final Logger logger = Logger.getLogger(BatsyServlet.class.getSimpleName());

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestPath = BatsyUtil.getRequestPath(request);
        logger.info("BatsyServlet worked! Path : " + requestPath);

        MethodSpecification specification = ServiceLoader.getRequestMap().get(requestPath);

        if (specification == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Request Map Not Found");
            return;
        }

        if (!BatsyUtil.isRequestMethodValid(request, specification.getRequestMethod().name())) {
            throw new UnsupportedOperationException();
        }

        servletResponse.setContentType("application/json");
        try {
            servletResponse.getOutputStream().
                    print((String) specification.getMethod().invoke(specification.getClazz().newInstance()));
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
