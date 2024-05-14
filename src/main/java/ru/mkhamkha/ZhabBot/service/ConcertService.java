package ru.mkhamkha.ZhabBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mkhamkha.ZhabBot.model.entity.Concert;

public interface ConcertService {

    Page<Concert> findAllConcerts(Pageable pageable);

    Concert findConcertById(Long concertId);

    Concert addConcert(Concert concert);

    Concert updateConcert(Long concertId, Concert concert);

    void deleteConcertById(Long concertId);

    void deleteAllConcert();
}
