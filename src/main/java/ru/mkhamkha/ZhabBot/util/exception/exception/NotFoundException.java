package ru.mkhamkha.ZhabBot.util.exception.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> entityClass, String message) {
        super("Entity " + entityClass.getSimpleName() + " not found. " + message);
    }
}