package org.batsy.core.http;

import org.batsy.core.util.JsonUtil;

import java.io.Serializable;

/**
 * Created by ufuk on 24.10.2016.
 */
public class BatsyResponse<T> implements Serializable {
    private T result;
    private int statusCode = HttpStatusCode.OK;

    public BatsyResponse(T result, int statusCode) {
        this.result = result;
        this.statusCode = statusCode;
    }

    public BatsyResponse(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public String getJsonResult() {
        return JsonUtil.toJson(this.result);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
