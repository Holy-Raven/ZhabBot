package ru.mkhamkha.ZhabBot.util.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ErrorResponse (

        @JsonProperty("message")
        String errorMessage,

        @JsonProperty("exception")
        String exceptionClass,

        @JsonProperty("status")
        Integer status

) {
}