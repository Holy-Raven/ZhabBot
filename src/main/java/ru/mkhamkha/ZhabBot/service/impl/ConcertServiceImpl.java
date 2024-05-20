package ru.mkhamkha.ZhabBot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.model.entity.Band;
import ru.mkhamkha.ZhabBot.model.entity.Concert;
import ru.mkhamkha.ZhabBot.repository.ConcertRepository;
import ru.mkhamkha.ZhabBot.service.BandService;
import ru.mkhamkha.ZhabBot.service.ConcertService;
import ru.mkhamkha.ZhabBot.service.PlaceService;
import ru.mkhamkha.ZhabBot.util.exception.exception.ConflictException;
import ru.mkhamkha.ZhabBot.util.exception.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;
    private final PlaceService placeService;
    private final BandService bandService;

    @Override
    public Page<Concert> findAllConcerts(Pageable pageable) {
        return concertRepository.findAll(pageable);

    }

    @Override
    public Concert findConcertById(Long concertId) {
        return concertRepository.findById(concertId).orElseThrow(() ->
                new NotFoundException(Concert.class, String.format("Concert %d не найден", concertId)));
    }

    @Override
    public Concert addConcert(Concert concert) {

        return concertRepository.save(checkPlaceAndBand(concert));
    }

    @Override
    public Concert updateConcert(Long concertId, Concert concert) {

        Concert updatedConcert = findConcertById(concertId);

        if (concert.getTitle() != null)
            updatedConcert.setTitle(concert.getTitle());
        if (concert.getDescription() != null)
            updatedConcert.setDescription(concert.getDescription());
        if (concert.getPrice() != null)
            updatedConcert.setPrice(concert.getPrice());
        if (concert.getStart() != null)
            updatedConcert.setStart(concert.getStart());
        if (concert.getEnd() != null)
            updatedConcert.setEnd(concert.getEnd());
        if (concert.getLink() != null)
            updatedConcert.setLink(concert.getLink());
        if (concert.getPlace() != null)
            updatedConcert.setPlace(concert.getPlace());
        if (concert.getBands() != null)
            updatedConcert.setBands(concert.getBands());

        return concertRepository.save(checkPlaceAndBand(updatedConcert));
    }

    @Override
    public void deleteConcertById(Long concertId) {

        concertRepository.delete(findConcertById(concertId));
    }

    @Override
    public void deleteAllConcert() {

        List<Concert> forDel = concertRepository.findAll();

        try {
            if (forDel.isEmpty()) throw new ConflictException("Список концертов пуст.");
        } catch (ConflictException e) {
            log.error(e.getMessage());
        }

        concertRepository.deleteAll(forDel);
    }

    private Concert checkPlaceAndBand(Concert concert) {

        //если место проведения уже было в базе, то достаем его оттуда и помещаем в наш концерт
        placeService.findByNameAndCity(concert.getPlace().getName(), concert.getPlace().getName()).ifPresent(concert::setPlace);

        //если группа участник концерта уже была в базе, то достаем ее оттуда и помещаем в список участников нашего концерта
        List<Band> bandList = new ArrayList<>();
        concert.getBands().forEach(band -> {
            Optional<Band> newBandOptional = bandService.findByNameAndCity(band.getName(), band.getCity());
            Band newBand = newBandOptional.orElse(band);
            bandList.add(newBand);
        });
        concert.setBands(bandList);

        return concert;
    }
}
