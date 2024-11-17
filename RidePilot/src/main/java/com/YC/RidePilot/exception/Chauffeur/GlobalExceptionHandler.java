package com.YC.RidePilot.exception.Chauffeur;

import com.YC.RidePilot.exception.Chauffeur.response.ErrorResponse;
import com.YC.RidePilot.exception.Chauffeur.response.ValidationResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        log.error("Erreur de validation: {}", ex.getMessage());
        Map<String, String> errorResponse = Map.of(
            "error", "Erreur de validation",
            "message", ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleChauffeurNotFoundException(NotFoundException ex) {
        log.error("Chauffeur non trouvé: {}", ex.getMessage());
        Map<String, String> errorResponse = Map.of(
            "error", "Chauffeur non trouvé",
            "message", ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    ///

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse errorRes = new ErrorResponse("404", HttpStatus.NOT_FOUND, "Not found");
        errorRes.setMessage(String.format("URL %s, Méthode %s inexistante.", ex.getRequestURL(), ex.getHttpMethod()));
        errorRes.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorRes);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getMessage();

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, error);
        return ResponseEntity.badRequest().body(errorRes);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " Paramètre Manquant où Nom de paramètre invalide ";

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, error);

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            String fieldName = null;
            for (   Path.Node node : constraintViolation.getPropertyPath()) {
                fieldName = node.getName();
            }
            errors.add(fieldName + " : " + constraintViolation.getMessage());
        }

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, "Erreur de paramétre");
        errorRes.setMessage("Paramètre invalide");
        errorRes.setParameters(errors);

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        String error = ex.getName();

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, error);
        errorRes.setMessage(ex.getLocalizedMessage());

        return ResponseEntity.badRequest().body(errorRes);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ValidationResponse> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String object = error.getObjectName();
            String fieldName = ((FieldError) error).getField();
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            String message = error.getDefaultMessage();
            errors.add(new ValidationResponse(object, fieldName, rejectedValue, message));
        });



        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, "BAD REQUEST");

        errorRes.setParameters(errors.stream().map(ValidationResponse::getMessage).collect(Collectors.toList()));

        return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> globalExceptionHundler(Exception ex) {
        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, "");
        errorRes.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({SQLGrammarException.class})
    public ResponseEntity<Object> sqlGrammarException(SQLGrammarException ex, WebRequest request) {

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, "Bad Request ");
        errorRes.setMessage(ex.getLocalizedMessage());

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, "Bad Request ");
        errorRes.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<Object> dateTimeParseException(ObjectNotFoundException ex, WebRequest request) {

        ErrorResponse errorRes = new ErrorResponse("404", HttpStatus.NOT_FOUND, "Not Found ");
        errorRes.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<Object> objectNotFoundException(ObjectNotFoundException ex, WebRequest request) {

        ErrorResponse errorRes = new ErrorResponse("404", HttpStatus.NOT_FOUND, "Not Found ");
        errorRes.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRes);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorRes) {
        HttpStatus status = HttpStatus.valueOf(errorRes.getStatus());
        System.out.println("status = " + status);

        return new ResponseEntity<>(errorRes, status);
    }

}