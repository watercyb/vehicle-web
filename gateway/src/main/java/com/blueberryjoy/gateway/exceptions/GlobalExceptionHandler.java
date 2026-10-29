package com.blueberryjoy.gateway.exceptions;


import com.blueberryjoy.gateway.model.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<ErrorMessage> handlerError(UserUnauthorizedException e) {
        var errorMessage = new ErrorMessage(400, e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorMessage> handleClientError(HttpClientErrorException e) {
        try {
            String body = e.getResponseBodyAsString(); // {"code":404,"message":"Not Found"}
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> json = mapper.readValue(body, Map.class);
            String message = (String) json.get("message");
            return new ResponseEntity<>(new ErrorMessage(400, message), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ErrorMessage(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
