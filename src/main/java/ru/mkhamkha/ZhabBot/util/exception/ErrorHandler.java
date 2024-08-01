package ru.mkhamkha.ZhabBot.util.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mkhamkha.ZhabBot.util.exception.dto.ErrorResponse;
import ru.mkhamkha.ZhabBot.util.exception.dto.ErrorResponseValid;
import ru.mkhamkha.ZhabBot.util.exception.exception.ConflictException;
import ru.mkhamkha.ZhabBot.util.exception.exception.NotFoundException;
import ru.mkhamkha.ZhabBot.util.exception.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .exceptionClass(e.getClass().toString())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        log.error("ERROR: {}", response.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .exceptionClass(e.getClass().toString())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        log.error("ERROR: {}", response.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e) {
        ErrorResponse response = ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .exceptionClass(e.getClass().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        log.error("ERROR: {}", response.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleEmailExistException(ConflictException e) {
        ErrorResponse response = ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .exceptionClass(e.getClass().toString())
                .status(HttpStatus.CONFLICT.value())
                .build();

        log.error("ERROR: {}", response.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponseValid> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> mapErrors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) ->
                mapErrors.put(((FieldError) error).getField(), error.getDefaultMessage()));

        ErrorResponseValid response = ErrorResponseValid.builder()
                .errorMessage(mapErrors).status(HttpStatus.BAD_REQUEST.value())
                .exceptionClass(e.getClass().toString())
                .build();

        log.error("ERROR: {}", response.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponseValid> handleConstraintViolationException(ConstraintViolationException e) {

        Map<String, String> mapErrors = new HashMap<>();
        e.getConstraintViolations().forEach((error) -> {
            String propertyPath = error.getPropertyPath().toString();
            mapErrors.put(propertyPath.substring(propertyPath.lastIndexOf('.') + 1), error.getMessage());
        });

        ErrorResponseValid response = ErrorResponseValid.builder()
                .errorMessage(mapErrors).status(HttpStatus.BAD_REQUEST.value())
                .exceptionClass(e.getClass().toString())
                .build();

        log.error("ERROR: {}", response.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}