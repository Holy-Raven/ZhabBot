package ru.mkhamkha.ZhabBot.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkhamkha.ZhabBot.model.dto.PlaceDTO;
import ru.mkhamkha.ZhabBot.model.entity.Place;
import ru.mkhamkha.ZhabBot.model.mapper.PlaceMapper;
import ru.mkhamkha.ZhabBot.service.PlaceService;

import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.FROM_ERROR_MESSAGE;
import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.SIZE_ERROR_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/places")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Page<PlaceDTO> findAllPlace(@PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                       @RequestParam(defaultValue = "0") Integer from,
                                       @Positive(message = SIZE_ERROR_MESSAGE)
                                       @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from, size);
        Page<Place> places = placeService.findAllPlaces(page);

        log.info("GET request: /zhabalaka/admin/places");
        return places.map(PlaceMapper::toDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public PlaceDTO findPlaceById(@PathVariable("id") Long placeId) {

        log.info("GET request: /zhabalaka/admin/place/" + placeId);
        return PlaceMapper.toDTO(placeService.findPlaceById(placeId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PlaceDTO addPlace(@Valid @RequestBody PlaceDTO placeDTO) {

        log.info("POST request: /zhabalaka/admin/place");
        return PlaceMapper.toDTO(placeService.addPlace(PlaceMapper.toEntity(placeDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PlaceDTO updatePlace(@RequestBody PlaceDTO placeDTO,
                                @PathVariable("id") Long placeId) {

        log.info("PUT request: /zhabalaka/admin/place/" + placeId);
        return PlaceMapper.toDTO(placeService.updatePlace(placeId, PlaceMapper.toEntity(placeDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePlaceById(@PathVariable("id") Long placeId) {

        log.info("DEL request: /zhabalaka/admin/place/" + placeId);
        placeService.deletePlaceById(placeId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllPlace() {

        log.info("DEL request: /zhabalaka/admin/place");
        placeService.deleteAllPlace();
    }
}