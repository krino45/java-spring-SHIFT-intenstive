package ru.cft.igoshin.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.cft.igoshin.api.dto.ErrorResponse;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errorMsg = new StringBuilder("Error validating data: ");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMsg.append(error.getField()).append(", ");
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(1, errorMsg.toString()));
    }

    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(CustomServiceException ex) {
        String errorMsg = ex.getMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(4, errorMsg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleValidationException(Exception ex) {
        String errorMsg = "Unspecified internal server error: " + ex.getMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(5, errorMsg));
    }
}