package ru.mkhamkha.ZhabBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mkhamkha.ZhabBot.model.dto.ConcertDTO;
import ru.mkhamkha.ZhabBot.model.entity.Concert;
import ru.mkhamkha.ZhabBot.model.mapper.ConcertMapper;
import ru.mkhamkha.ZhabBot.service.ConcertService;

import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.FROM_ERROR_MESSAGE;
import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.SIZE_ERROR_MESSAGE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/concerts")
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Постраничная выдача всех концертов")
    public Page<ConcertDTO> findAllConcert(@PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @Positive(message = SIZE_ERROR_MESSAGE)
                                           @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from, size);
        Page<Concert> concerts = concertService.findAllConcerts(page);

        log.info("[GET] request: /zhabalaka/admin/concerts");
        return concerts.map(ConcertMapper::toDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Выдать концерт по id")
    public ConcertDTO findConcertById(@PathVariable("id") Long concertId) {

        log.info("[GET] request: /zhabalaka/admin/concert/{}", concertId);
        return ConcertMapper.toDTO(concertService.findConcertById(concertId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Добавить новый концерт")
    public ConcertDTO addConcert(@Valid @RequestBody ConcertDTO concertDTO) {

        ConcertDTO result = ConcertMapper.toDTO(concertService.addConcert(ConcertMapper.toEntity(concertDTO)));

        log.info("[POST] request: /zhabalaka/admin/concert, id:{}", result.getId());
        return result;
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Обновить имеющийся концерт по его id")
    public ConcertDTO updateConcert(@Valid @RequestBody ConcertDTO concertDTO,
                                    @PathVariable("id") Long concertId) {

        log.info("[PUT] request: /zhabalaka/admin/concert/{}", concertId);
        return ConcertMapper.toDTO(concertService.updateConcert(concertId, ConcertMapper.toEntity(concertDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить концерт по id")
    public void deleteConcertById(@PathVariable("id") Long concertId) {

        log.info("[DEL] request: /zhabalaka/admin/concert/{}", concertId);
        concertService.deleteConcertById(concertId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить все концерты")
    public void deleteAllConcert() {

        log.info("[DEL] request: /zhabalaka/admin/concert");
        concertService.deleteAllConcert();
    }
}
