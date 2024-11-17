package com.YC.RidePilot.exception.Chauffeur;

import com.YC.RidePilot.utilis.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SuccesMessageException {
    @ExceptionHandler(ClassSucesseExption.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SuccessResponse> succeClasseException(ClassSucesseExption ex) {
        SuccessResponse success = new SuccessResponse(
                HttpStatus.OK.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
