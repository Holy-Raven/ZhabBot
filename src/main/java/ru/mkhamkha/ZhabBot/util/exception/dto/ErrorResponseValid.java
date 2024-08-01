package ru.mkhamkha.ZhabBot.util.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponseValid (

    @JsonProperty("message")
    Map<String,String> errorMessage,

    @JsonProperty("exception")
    String exceptionClass,

    @JsonProperty("status")
    Integer status

) {
}