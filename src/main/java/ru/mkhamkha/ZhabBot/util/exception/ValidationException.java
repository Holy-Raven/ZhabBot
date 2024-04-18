package ru.mkhamkha.ZhabBot.util.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}