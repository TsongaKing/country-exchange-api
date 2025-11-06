package com.backendwizards.country.exception;

import java.util.Map;

public class ApiError {
    private String error;
    private Map<String, String> details;

    public ApiError(String error, Map<String, String> details) {
        this.error = error;
        this.details = details;
    }

    public String getError() { return error; }
    public Map<String, String> getDetails() { return details; }

    public void setError(String error) { this.error = error; }
    public void setDetails(Map<String, String> details) { this.details = details; }
}
