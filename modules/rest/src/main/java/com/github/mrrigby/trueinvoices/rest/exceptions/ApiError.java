package com.github.mrrigby.trueinvoices.rest.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author MrRigby
 */
public class ApiError extends ResourceSupport implements Serializable {

    @JsonProperty("httpStatusCode")
    private final int code;

    @JsonProperty("httpStatusName")
    private final String name;

    @JsonProperty("messages")
    private final String message;

    @JsonProperty("requestUrl")
    private final String requestUrl;

    public ApiError(int code, String name, String message, String requestUrl) {
        this.code = code;
        this.name = name;
        this.message = message;
        this.requestUrl = requestUrl;
    }

    public ApiError(HttpStatus httpStatus, String message, String requestUrl) {
        this.code = httpStatus.value();
        this.name = httpStatus.name();
        this.message = message;
        this.requestUrl = requestUrl;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getRequestUrl() {
        return requestUrl;
    }
}
