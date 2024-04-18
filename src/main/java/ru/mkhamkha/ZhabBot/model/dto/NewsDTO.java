package ru.mkhamkha.ZhabBot.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

import static ru.mkhamkha.ZhabBot.util.Constants.DATE_FORMAT;

@Getter
public record NewsDTO (

        @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank
        @Size(max = 250)
        @JsonProperty(value = "title")
        String title,

        @NotBlank
        @Size(max = 2000)
        @JsonProperty(value = "message")
        String message,

        @Past
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
        @JsonProperty(value = "create_time")
        LocalDateTime time

) {

}
