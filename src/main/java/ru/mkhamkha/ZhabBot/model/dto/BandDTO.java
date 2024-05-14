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
public class BandDTO {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    Long id;

    @Size(max = 100)
    @JsonProperty(value = "name")
    String name;

    @Size(max = 100)
    @JsonProperty(value = "city")
    String city;

    @Size(max = 100)
    @JsonProperty(value = "genre")
    String genre;

    @Size(max = 2000)
    @JsonProperty(value = "description")
    String description;

    @Size(max = 250)
    @JsonProperty(value = "link")
    String link;
}
