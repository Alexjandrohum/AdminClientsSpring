package com.clients.admin.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class ApiException extends RuntimeException{

    private HttpStatus code;
    private String msg;
    private ArrayList<String> details;

    public ApiException(HttpStatus code, String msg, ArrayList<String> details) {
        this.code = code;
        this.msg = msg;
        this.details = details;
    }

    public ApiException(String message, HttpStatus code, String msg, ArrayList<String> details) {
        super(message);
        this.code = code;
        this.msg = msg;
        this.details = details;
    }

    public ApiException(String message, Throwable cause, HttpStatus code, String msg, ArrayList<String> details) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
        this.details = details;
    }

    public ApiException(Throwable cause, HttpStatus code, String msg, ArrayList<String> details) {
        super(cause);
        this.code = code;
        this.msg = msg;
        this.details = details;
    }

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus code, String msg, ArrayList<String> details) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
        this.details = details;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", details=" + details +
                '}';
    }
}
