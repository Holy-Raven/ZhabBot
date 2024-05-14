package ru.mkhamkha.ZhabBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mkhamkha.ZhabBot.model.entity.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
