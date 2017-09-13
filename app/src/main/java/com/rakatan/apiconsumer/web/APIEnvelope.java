package com.rakatan.apiconsumer.web;

public class APIEnvelope<T> {
    private T response;

    private ApiError error;

    public APIEnvelope() {
    }

    public APIEnvelope(T response, ApiError error) {
        this.response = response;
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }
}