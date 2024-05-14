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
import ru.mkhamkha.ZhabBot.model.dto.ConcertDTO;
import ru.mkhamkha.ZhabBot.model.entity.Concert;
import ru.mkhamkha.ZhabBot.model.mapper.ConcertMapper;
import ru.mkhamkha.ZhabBot.service.ConcertService;

import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.FROM_ERROR_MESSAGE;
import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.SIZE_ERROR_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/concerts")
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Page<ConcertDTO> findAllConcert(@PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @Positive(message = SIZE_ERROR_MESSAGE)
                                           @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from, size);
        Page<Concert> concerts = concertService.findAllConcerts(page);

        log.info("GET request: /zhabalaka/admin/concerts");
        return concerts.map(ConcertMapper::toDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ConcertDTO findConcertById(@PathVariable("id") Long concertId) {

        log.info("GET request: /zhabalaka/admin/concert/" + concertId);
        return ConcertMapper.toDTO(concertService.findConcertById(concertId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ConcertDTO addConcert(@Valid @RequestBody ConcertDTO concertDTO) {

        log.info("POST request: /zhabalaka/admin/concert");
        return ConcertMapper.toDTO(concertService.addConcert(ConcertMapper.toEntity(concertDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ConcertDTO updateConcert(@RequestBody ConcertDTO concertDTO,
                                    @PathVariable("id") Long concertId) {

        log.info("PUT request: /zhabalaka/admin/concert/" + concertId);
        return ConcertMapper.toDTO(concertService.updateConcert(concertId, ConcertMapper.toEntity(concertDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteConcertById(@PathVariable("id") Long concertId) {

        log.info("DEL request: /zhabalaka/admin/concert/" + concertId);
        concertService.deleteConcertById(concertId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllConcert() {

        log.info("DEL request: /zhabalaka/admin/concert");
        concertService.deleteAllConcert();
    }
}
