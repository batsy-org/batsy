package org.batsy.core.servlet;

import org.batsy.core.http.RequestMethod;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ufuk on 24.10.2016.
 */
public class MethodSpecification implements Serializable {

    private String path;
    private Class clazz;
    private Method method;
    private RequestMethod requestMethod;
    private List<ParameterSpecification> parameters;

    public MethodSpecification(String path, Class clazz, Method method, RequestMethod requestMethod, List<ParameterSpecification> parameters) {
        this.path = path;
        this.clazz = clazz;
        this.method = method;
        this.requestMethod = requestMethod;
        this.parameters = parameters;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setParameters(List<ParameterSpecification> parameters) {
        this.parameters = parameters;
    }

    public List<ParameterSpecification> getParameters() {
        return parameters;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
}
