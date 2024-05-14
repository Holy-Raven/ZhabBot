package ru.mkhamkha.ZhabBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mkhamkha.ZhabBot.repository.composkey.ConcertBand;
import ru.mkhamkha.ZhabBot.repository.composkey.ConcertBandId;

@Repository
public interface ConcertBandRepository extends JpaRepository<ConcertBand, ConcertBandId> {
}
