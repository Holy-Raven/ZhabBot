package ru.mkhamkha.ZhabBot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.model.entity.Band;
import ru.mkhamkha.ZhabBot.repository.BandRepository;
import ru.mkhamkha.ZhabBot.service.BandService;
import ru.mkhamkha.ZhabBot.util.exception.exception.ConflictException;
import ru.mkhamkha.ZhabBot.util.exception.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BandServiceImpl implements BandService {

    private final BandRepository bandRepository;

    @Override
    public Optional<Band> findByNameAndCity(String name, String city) {
        return bandRepository.findByNameAndCity(name, city);
    }

    @Override
    public Page<Band> findAllBands(Pageable pageable) {
        return bandRepository.findAll(pageable);

    }

    @Override
    public Band findBandById(Long bandId) {
        return bandRepository.findById(bandId).orElseThrow(() ->
                new NotFoundException(Band.class, String.format("Band %d не найдена", bandId)));
    }

    @Override
    public Band addBand(Band band) {
        return bandRepository.save(band);
    }

    @Override
    public List<Band> addBandList(List<Band> bands) {
        return bandRepository.saveAll(bands);
    }

    @Override
    public Band updateBand(Long bandId, Band band) {

        Band updatedBand = findBandById(bandId);

        if (band.getName() != null)
            updatedBand.setName(band.getName());
        if (band.getCity() != null)
            updatedBand.setCity(band.getCity());
        if (band.getGenre() != null)
            updatedBand.setGenre(band.getGenre());
        if (band.getDescription() != null)
            updatedBand.setDescription(band.getDescription());
        if (band.getLink() != null)
            updatedBand.setLink(band.getLink());

        return updatedBand;
    }

    @Override
    public void deleteBandById(Long bandId) {
        bandRepository.delete(findBandById(bandId));
    }

    @Override
    public void deleteAllBand() {

        List<Band> forDel = bandRepository.findAll();

        try {
            if (forDel.isEmpty()) throw new ConflictException("Список команд пуст.");
        } catch (ConflictException e) {
            log.error(e.getMessage());
        }

        bandRepository.deleteAll(forDel);
    }
}
