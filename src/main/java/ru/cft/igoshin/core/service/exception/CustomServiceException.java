package ru.cft.igoshin.core.service.exception;

public class CustomServiceException extends RuntimeException {
    public CustomServiceException (String message) {
        super(message);
    }
}
