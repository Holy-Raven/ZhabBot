package ru.mkhamkha.ZhabBot.util.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}