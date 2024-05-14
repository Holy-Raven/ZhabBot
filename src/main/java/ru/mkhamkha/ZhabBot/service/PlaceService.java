package ru.mkhamkha.ZhabBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mkhamkha.ZhabBot.model.entity.Place;

import java.util.Optional;

public interface PlaceService {

    Optional<Place> findByNameAndCity(String name, String city);

    Page<Place> findAllPlaces(Pageable pageable);

    Place findPlaceById(Long placeId);

    Place addPlace(Place place);

    Place updatePlace(Long placeId, Place place);

    void deletePlaceById(Long placeId);

    void deleteAllPlace();
}
