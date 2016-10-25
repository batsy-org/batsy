package org.batsy.core.servlet;

import org.batsy.core.exception.BatsyException;
import org.batsy.core.http.BatsyResponse;
import org.batsy.core.http.HttpStatusCode;
import org.batsy.core.util.BatsyUtil;
import org.batsy.core.util.JsonUtil;
import org.batsy.core.util.RequestMapUtil;

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
public final class BatsyServlet extends GenericServlet {
    private static final Logger logger = Logger.getLogger(BatsyServlet.class.getSimpleName());

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestPath = BatsyUtil.getRequestPath(request);

        MethodSpecification specification = RequestMapUtil.getRequestMap().get(requestPath);

        if (specification == null) {
            response.sendError(HttpStatusCode.NOT_FOUND, "Request Map Not Found");
            return;
        }

        if (!BatsyUtil.isRequestMethodValid(request, specification.getRequestMethod().name())) {
            throw new UnsupportedOperationException();
        }

        servletResponse.setContentType("application/json");
        try {
            setParams(request, specification);
            Object object = specification.getMethod().invoke(specification.getClazz().newInstance(), methodParams(specification));
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

    //TODO
    private void setParams(HttpServletRequest request, MethodSpecification specification) {
        specification.getParameters().forEach(p -> {
            if (p.getType() == ParameterSpecification.Type.QUERY) {
                String value = request.getParameter(p.getParamName());
                p.setDefaultValue(value);
            }
        });
    }

    // TODO: 25.10.2016
    private String[] methodParams(MethodSpecification specification) {
        String[] params = new String[specification.getParameters().size()];
        for (int i = 0; i < params.length; i++) {
            params[i] = specification.getParameters().get(i).getDefaultValue();
        }
        return params;
    }
}
