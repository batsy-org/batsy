package org.batsy.core.servlet;

import com.google.gson.GsonBuilder;
import org.batsy.core.exception.BatsyException;
import org.batsy.core.http.BatsyResponse;
import org.batsy.core.http.HttpStatusCode;
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

        MethodSpecification specification = ServiceLoader.getRequestMap().get(requestPath);

        if (specification == null) {
            response.sendError(HttpStatusCode.NOT_FOUND, "Request Map Not Found");
            return;
        }

        if (!BatsyUtil.isRequestMethodValid(request, specification.getRequestMethod().name())) {
            throw new UnsupportedOperationException();
        }

        servletResponse.setContentType("application/json");
        try {
            Object object = specification.getMethod().invoke(specification.getClazz().newInstance());
            if (object instanceof BatsyResponse) {
                BatsyResponse batsyResponse = (BatsyResponse) object;
                response.setStatus(batsyResponse.getStatusCode());
                servletResponse.getOutputStream().print(batsyResponse.getJsonResult());
            } else {
                response.setStatus(HttpStatusCode.OK);
                servletResponse.getOutputStream().print(new GsonBuilder().create().toJson(object));
            }

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new BatsyException(e.getMessage(), e);
        }
    }
}
