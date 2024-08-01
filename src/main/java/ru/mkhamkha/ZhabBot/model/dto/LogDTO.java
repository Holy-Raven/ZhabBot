package ru.mkhamkha.ZhabBot.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static ru.mkhamkha.ZhabBot.util.Constants.Formatter.DATE_FULL_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(max = 5, message = "Максимальная длина level не должна быть более 5 симворлов.")
    @JsonProperty(value = "level")
    private String level;

    @Size(max = 250, message = "Максимальная длина logger не должна быть более 250 симворлов.")
    @JsonProperty(value = "logger")
    private String logger;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FULL_TIME_FORMAT)
    private LocalDateTime create;

}
