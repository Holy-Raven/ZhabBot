package ru.mkhamkha.ZhabBot.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.model.entity.Place;
import ru.mkhamkha.ZhabBot.repository.PlaceRepository;
import ru.mkhamkha.ZhabBot.service.PlaceService;
import ru.mkhamkha.ZhabBot.util.exception.exception.ConflictException;
import ru.mkhamkha.ZhabBot.util.exception.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Override
    public Optional<Place> findByNameAndCity(String name, String city) {
        return placeRepository.findByNameAndCity(name, city);
    }

    @Override
    public Page<Place> findAllPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable);    }

    @Override
    public Place findPlaceById(Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(() ->
                new NotFoundException(Place.class, String.format("Place %d не найден", placeId)));
    }

    @Override
    public Place addPlace(Place place) {
        return placeRepository.save(place);
    }

    @Override
    @Transactional
    public Place updatePlace(Long placeId, Place place) {

        Place updatedPlace = findPlaceById(placeId);

        if (place.getName() != null)
            updatedPlace.setName(place.getName());
        if (place.getCity() != null)
            updatedPlace.setCity(place.getCity());
        if (place.getDescription() != null)
            updatedPlace.setDescription(place.getDescription());
        if (place.getLink() != null)
            updatedPlace.setLink(place.getLink());

        return placeRepository.save(updatedPlace);
    }

    @Override
    public void deletePlaceById(Long placeId) {
        placeRepository.delete(findPlaceById(placeId));
    }

    @Override
    public void deleteAllPlace() {
        List<Place> forDel = placeRepository.findAll();

        try {
            if (forDel.isEmpty()) throw new ConflictException("Список новостей пуст.");
        } catch (ConflictException e) {
            log.error(e.getMessage());
        }

        placeRepository.deleteAll(forDel);
    }
}
