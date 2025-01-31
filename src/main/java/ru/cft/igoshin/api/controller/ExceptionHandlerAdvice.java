package ru.cft.igoshin.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.cft.igoshin.api.dto.ErrorResponse;
import ru.cft.igoshin.core.service.exception.CustomServiceException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("MethodArgumentNotValidException: {}", ex.getMessage());
        StringBuilder errorMsg = new StringBuilder("Error validating fields:");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMsg.append(" ").append(error.getField());
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(1, errorMsg.toString()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentTypeMismatchException ex) {
        log.warn("MethodArgumentTypeMismatchException: {}", ex.getMessage());
        String errorMsg = "Method argument type mismatch (did you make a typo in an enum?): " + ex.getMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(4, errorMsg));
    }

    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(CustomServiceException ex) {
        log.warn("CustomServiceException: {}", ex.getMessage());
        String errorMsg = ex.getMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(4, errorMsg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleValidationException(Exception ex) {
        log.warn("Exception ({}): {}", ex.getClass(), ex.getMessage());
        String errorMsg = "Unspecified internal server error: " + ex.getMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(5, errorMsg));
    }
}