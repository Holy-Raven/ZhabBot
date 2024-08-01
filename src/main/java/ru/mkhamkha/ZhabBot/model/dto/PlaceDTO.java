package ru.mkhamkha.ZhabBot.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    Long id;

    @Size(max = 100, message = "Максимальная длина name не должна быть более 100 симворлов.")
    @JsonProperty(value = "name")
    String name;

    @Size(max = 100, message = "Максимальная длина city не должна быть более 100 симворлов.")
    @JsonProperty(value = "city")
    String city;

    @Size(max = 2000, message = "Максимальная длина description не должна быть более 2000 симворлов.")
    @JsonProperty(value = "description")
    String description;

    @Size(max = 250, message = "Максимальная длина link не должна быть более 250 симворлов.")
    @JsonProperty(value = "link")
    String link;
}