package ru.mkhamkha.ZhabBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mkhamkha.ZhabBot.model.entity.Band;

import java.util.Optional;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {

    Optional<Band> findByNameAndCity(String name, String city);
}
