package com.website.backend.model;

public class ResponseModel {

    long count;
    boolean logged;
    private Object data;
    private String message;
    private String error;
    private int code;

    public ResponseModel(Object data, String error, int code, String message, long count, boolean logged) {
        super();
        this.data = data;
        this.error = error;
        this.code = code;
        this.message = message;
        this.count = count;
        this.logged = logged;
    }

    public ResponseModel(Object data, String error, int code, String message, boolean logged) {
        this(data, error, code, message, 0, logged);

    }

    public static ResponseModel createSuccessResponseWithData(Object data, boolean logged) {
        return new ResponseModel(data, "", 200, "Basarili", logged);
    }

    public static ResponseModel createErrorResponseWithErrorMessage(Exception e) {
        return new ResponseModel(null, e.getLocalizedMessage(), 999, e.toString(), true);
    }

    public static ResponseModel createErrorResponseWithErrorMessage(Exception e,int code) {
        return new ResponseModel(null, e.getLocalizedMessage(), code, e.toString(), true);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

}

