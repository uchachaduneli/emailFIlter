
package com.email.filter.misc;

import com.google.gson.Gson;

/**
 * @author
 */
public class Response extends JsonSerializable {

    private static final int SUCCESS = 0;
    private int errorCode;
    private String message;
    private Object data;

    public static Response ok() {
        Response r = new Response();
        r.setErrorCode(SUCCESS);
        return r;
    }

    public static Response withError(String error) {
        Response r = new Response();
        r.setErrorCode(700);
        r.setMessage(error);
        return r;
    }

    public static Response withErrorData(String error, Object data) {
        Response r = new Response();
        r.setErrorCode(700);
        r.setMessage(error);
        r.setData(data);
        return r;
    }

    public static Response withError(int errorCode) {
        Response r = new Response();
        r.setErrorCode(errorCode);
        return r;
    }

    public static Response withError(String errorMessage, int errorCode) {
        Response r = new Response();
        r.setErrorCode(errorCode);
        r.setMessage(errorMessage);
        return r;
    }

    public static Response withSuccess(Object data) {
        Response r = ok();
        r.setData(data);
        return r;
    }

    public static Response makeSimpleData(Object data) {
        Response r = ok();
        r.setData(new SimpelData(data));
        return r;
    }

    public static String asJson(Response r) {
        return new Gson().toJson(r);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}

class SimpelData {

    private Object data;

    public SimpelData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}