package com.blueberryjoy.user_service.exceptions;

import com.blueberryjoy.user_service.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UsernameExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NoSuchElementException.class)
    public ErrorMessage handlerError(NoSuchElementException e) {
        return new ErrorMessage(400, e.getMessage());
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ErrorMessage> handlerError(UsernameExistsException e) {
        var errorMessage = new ErrorMessage(400, e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handlerError(MethodArgumentNotValidException e) {
        List<String> msg = new ArrayList<>();
        for (var err : e.getBindingResult().getFieldErrors()) {
            msg.add(err.getDefaultMessage());
        }
        var errorMessage = new ErrorMessage(400, msg.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handlerError(UserNotFoundException e) {
        var errorMessage = new ErrorMessage(404, e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<ErrorMessage> handlerError(UserUnauthorizedException e) {
        var errorMessage = new ErrorMessage(400, e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
