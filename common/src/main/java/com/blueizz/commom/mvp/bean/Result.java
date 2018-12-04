package com.blueizz.commom.mvp.bean;

public class Result {

    public boolean success;

    public String errorCode;

    public String error;

    public Object obj;

    public Result(boolean success, String errorCode, String error) {
        this.success = success;
        this.errorCode = errorCode;
        this.error = error;
    }

    public Result(String errorCode, String error) {
        this.success = false;
        this.errorCode = errorCode;
        this.error = error;
    }

    public Result(Object obj) {
        this.success = true;
        this.obj = obj;
    }

    public Result() {
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
