package com.backendwizards.country.exception;

public class ExternalApiException extends RuntimeException {
    private final String apiName;

    public ExternalApiException(String apiName, String message) {
        super(message);
        this.apiName = apiName;
    }

    public String getApiName() { return apiName; }
}
