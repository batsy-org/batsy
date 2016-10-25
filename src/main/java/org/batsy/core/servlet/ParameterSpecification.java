package org.batsy.core.servlet;

import java.io.Serializable;

/**
 * Created by ufuk on 25.10.2016.
 */
public class ParameterSpecification implements Serializable {

    public enum Type{
        QUERY, PATH
    }

    private String paramName;
    private String defaultValue;
    private Type type;

    public ParameterSpecification(String paramName, String defaultValue, Type type) {
        this.paramName = paramName;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
