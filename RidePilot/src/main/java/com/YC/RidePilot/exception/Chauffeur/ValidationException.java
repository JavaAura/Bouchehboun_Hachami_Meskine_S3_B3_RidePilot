package com.YC.RidePilot.exception.Chauffeur;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
