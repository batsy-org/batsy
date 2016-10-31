package org.batsy.core.servlet;

import java.io.Serializable;

/**
 * Created by ufuk on 25.10.2016.
 */
public class ParameterSpecification implements Serializable {

    public enum Type{
        QUERY, PATH, BODY
    }

    private String paramName;
    private Object value;
    private Type type;
    private Class classType;

    public ParameterSpecification(String paramName, Object value, Type type) {
        this.paramName = paramName;
        this.value = value;
        this.type = type;
    }

    public ParameterSpecification(String paramName, Object value, Type type, Class classType) {
        this.paramName = paramName;
        this.value = value;
        this.type = type;
        this.classType = classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public Class getClassType() {
        return classType;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
