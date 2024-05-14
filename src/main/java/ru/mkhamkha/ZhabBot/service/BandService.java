package ru.mkhamkha.ZhabBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mkhamkha.ZhabBot.model.entity.Band;

import java.util.List;
import java.util.Optional;

public interface BandService {

    Optional<Band> findByNameAndCity(String name, String city);

    Page<Band> findAllBands(Pageable pageable);

    Band findBandById(Long bandId);

    Band addBand(Band band);

    List<Band> addBandList(List<Band> bands);

    Band updateBand(Long bandId, Band band);

    void deleteBandById(Long bandId);

    void deleteAllBand();
}
