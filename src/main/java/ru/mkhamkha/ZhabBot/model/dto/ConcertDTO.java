package ru.mkhamkha.ZhabBot.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static ru.mkhamkha.ZhabBot.util.Constants.Formatter.DATE_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDTO {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    Long id;

    @Size(max = 100, message = "Максимальная длина title не должна быть более 100 симворлов.")
    @JsonProperty(value = "title")
    String title;

    @Size(max = 2000, message = "Максимальная длина description не должна быть более 2000 симворлов.")
    @JsonProperty(value = "description")
    String description;

    @JsonProperty(value = "price")
    Integer price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    @JsonProperty(value = "start_time")
    LocalDateTime start;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    @JsonProperty(value = "end_time")
    LocalDateTime end;

    @Size(max = 250, message = "Максимальная длина link не должна быть более 250 симворлов.")
    @JsonProperty(value = "link")
    String link;

    @JsonProperty(value = "place")
    PlaceDTO placeDTO;

    @JsonProperty(value = "band")
    List<BandDTO> bandDTO;
}
