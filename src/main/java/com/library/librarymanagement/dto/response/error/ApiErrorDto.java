package com.library.librarymanagement.dto.response.error;

import java.time.ZonedDateTime;
import java.util.List;

public class ApiErrorDto {

    private String path;
    private String message;
    private int statusCode;
    private ZonedDateTime zonedDateTime;
    private List<String> errors;

    public ApiErrorDto() {
    }

    public ApiErrorDto(String path, String message, int statusCode, ZonedDateTime zonedDateTime, List<String> errors) {
        this.path = path;
        this.message = message;
        this.statusCode = statusCode;
        this.zonedDateTime = zonedDateTime;
        this.errors = errors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
