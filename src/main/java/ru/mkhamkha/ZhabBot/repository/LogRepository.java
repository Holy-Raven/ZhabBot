package ru.mkhamkha.ZhabBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mkhamkha.ZhabBot.model.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
}
