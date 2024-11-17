package com.YC.RidePilot.exception.Chauffeur.response;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ValidationResponse {

    private String object;

    private String field;

    private Object rejectedValue;

    private String message;

    public ValidationResponse(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ValidationResponse(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.message = message;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public String getMessage() {
        return message;
    }
}