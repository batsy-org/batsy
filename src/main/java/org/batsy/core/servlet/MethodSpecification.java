package org.batsy.core.servlet;

import org.batsy.core.http.RequestMethod;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by ufuk on 24.10.2016.
 */
public class MethodSpecification implements Serializable {

    private Class clazz;
    private Method method;
    private RequestMethod requestMethod;
    //TODO private Object

    public MethodSpecification(Class clazz, Method method, RequestMethod requestMethod) {
        this.clazz = clazz;
        this.method = method;
        this.requestMethod = requestMethod;
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
