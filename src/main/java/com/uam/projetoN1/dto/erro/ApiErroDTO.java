package com.uam.projetoN1.dto.erro;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiErroDTO {

    private String message;
    private List<String> errors;
    private HttpStatus status;


    public ApiErroDTO(String message, List<String> errors, HttpStatus status) {
        this.message = message;
        this.errors = errors;
        this.status = status;
    }

    public ApiErroDTO(String message, String error, HttpStatus status) {
        this.message = message;
        this.errors = Arrays.asList(error);
        this.status = status;
    }

    public ApiErroDTO() {
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}