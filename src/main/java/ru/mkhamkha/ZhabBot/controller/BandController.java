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
import ru.mkhamkha.ZhabBot.model.dto.BandDTO;
import ru.mkhamkha.ZhabBot.model.entity.Band;
import ru.mkhamkha.ZhabBot.model.mapper.BandMapper;
import ru.mkhamkha.ZhabBot.service.BandService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.FROM_ERROR_MESSAGE;
import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.SIZE_ERROR_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/bands")
public class BandController {

    private final BandService bandService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Page<BandDTO> findAllBand(@PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                     @RequestParam(defaultValue = "0") Integer from,
                                     @Positive(message = SIZE_ERROR_MESSAGE)
                                     @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from, size);
        Page<Band> bands = bandService.findAllBands(page);

        log.info("GET request: /zhabalaka/admin/bands");
        return bands.map(BandMapper::toDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BandDTO findBandById(@PathVariable("id") Long bandId) {

        log.info("GET request: /zhabalaka/admin/band/" + bandId);
        return BandMapper.toDTO(bandService.findBandById(bandId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BandDTO addBand(@Valid @RequestBody BandDTO bandDTO) {

        log.info("POST request: /zhabalaka/admin/band");
        return BandMapper.toDTO(bandService.addBand(BandMapper.toEntity(bandDTO)));
    }

    @PostMapping("/add-list")
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<BandDTO> addListBand(@Valid @RequestBody List<BandDTO> bandDTOlist) {

        log.info("POST request: /zhabalaka/admin/band/add-list");
        List<Band> result = bandService.addBandList(bandDTOlist.stream().map(BandMapper::toEntity).collect(Collectors.toList()));
        return result.stream().map(BandMapper::toDTO).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public BandDTO updateBand(@RequestBody BandDTO bandDTO,
                              @PathVariable("id") Long bandId) {

        log.info("PUT request: /zhabalaka/admin/band/" + bandId);
        return BandMapper.toDTO(bandService.updateBand(bandId, BandMapper.toEntity(bandDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBandById(@PathVariable("id") Long bandId) {

        log.info("DEL request: /zhabalaka/admin/band/" + bandId);
        bandService.deleteBandById(bandId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllBand() {

        log.info("DEL request: /zhabalaka/admin/band");
        bandService.deleteAllBand();
    }
}
