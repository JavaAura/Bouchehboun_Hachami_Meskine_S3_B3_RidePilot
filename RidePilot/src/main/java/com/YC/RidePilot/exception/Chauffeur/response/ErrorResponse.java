package com.YC.RidePilot.exception.Chauffeur.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

public class ErrorResponse {

    @Schema(description = "http error code")
    private String code;

    @Schema(description = "default status of http request")
    private String status;

    @Schema(description = "Error details")
    private String message;


    @Schema()
    private List<String> parameters = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime horodatage;


    public ErrorResponse() {
        horodatage = LocalDateTime.now();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void setHorodatage(LocalDateTime horodatage) {
        this.horodatage = horodatage;
    }

    public ErrorResponse(String code, HttpStatus status, String message) {
        this();
        this.status = status.toString();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public LocalDateTime getHorodatage() {
        return horodatage;
    }
}