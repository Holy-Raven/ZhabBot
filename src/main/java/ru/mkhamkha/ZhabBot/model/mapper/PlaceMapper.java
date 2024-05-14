package ru.mkhamkha.ZhabBot.model.mapper;

import org.springframework.stereotype.Component;
import ru.mkhamkha.ZhabBot.model.dto.PlaceDTO;
import ru.mkhamkha.ZhabBot.model.entity.Place;

@Component
public class PlaceMapper {

    public static Place toEntity(PlaceDTO placeDTO) {

        return Place.builder()
                .id(placeDTO.getId())
                .name(placeDTO.getName())
                .city(placeDTO.getCity())
                .description(placeDTO.getDescription())
                .link(placeDTO.getLink())
                .build();
    }

    public static PlaceDTO toDTO(Place place) {

        return PlaceDTO.builder()
                .id(place.getId())
                .name(place.getName())
                .city(place.getCity())
                .description(place.getDescription())
                .link(place.getLink())
                .build();
    }
}