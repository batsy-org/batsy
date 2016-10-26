package org.batsy.core.servlet;

import org.batsy.core.exception.BatsyException;
import org.batsy.core.http.BatsyResponse;
import org.batsy.core.http.HttpStatusCode;
import org.batsy.core.util.BatsyUtil;
import org.batsy.core.util.JsonUtil;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by ufuk on 22.10.2016.
 */
public final class BatsyServlet extends GenericServlet {
    private static final String CONTENT_TYPE = "application/json";

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestPath = BatsyUtil.getRequestPath(request);

        MethodSpecification specification = BatsyUtil.findMethodSpecificationByRequestPath(requestPath);

        if (specification == null) {
            response.sendError(HttpStatusCode.NOT_FOUND, "Request Map Not Found");
            return;
        }

        if (!BatsyUtil.isRequestMethodValid(request, specification.getRequestMethod().name())) {
            throw new UnsupportedOperationException();
        }

        servletResponse.setContentType(CONTENT_TYPE);
        try {
            BatsyUtil.findAndSetParams(request, specification);
            Object object = specification.getMethod().invoke(specification.getClazz().newInstance(), (Object[]) BatsyUtil.getMethodParams(specification));
            if (object instanceof BatsyResponse) {
                BatsyResponse batsyResponse = (BatsyResponse) object;
                response.setStatus(batsyResponse.getStatusCode());
                servletResponse.getOutputStream().print(batsyResponse.getJsonResult());
            } else {
                response.setStatus(HttpStatusCode.OK);
                servletResponse.getOutputStream().print(JsonUtil.toJson(object));
            }

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new BatsyException(e.getMessage(), e);
        }
    }

}
