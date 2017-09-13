package com.rakatan.apiconsumer.web;

public class ApiError {
    private int resultCode;
    private String errorMessage;

    public ApiError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ApiError(int resultCode) {
        this.resultCode = resultCode;
    }

    public ApiError(int resultCode, String errorMessage) {
        this.resultCode = resultCode;
        this.errorMessage = errorMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
