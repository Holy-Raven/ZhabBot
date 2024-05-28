package ru.mkhamkha.ZhabBot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mkhamkha.ZhabBot.model.entity.Log;

import java.time.LocalDateTime;


public interface LogRepository extends JpaRepository<Log, Long> {


    @Query(value = " SELECT l FROM Log AS l" +
            " WHERE (:level IS NULL OR LOWER(l.level) = :level) " +
            " AND (CAST(:startDate AS date) IS NULL OR (l.create >= CAST(:startDate AS date) AND l.create <= CAST(:endDate AS date))) " +
            " AND ((:text IS NULL) OR (LOWER(l.message) LIKE LOWER(CONCAT('%', :text, '%')))) " +
            " AND ((:logger IS NULL) OR (LOWER(l.logger) LIKE LOWER(CONCAT('%', :logger, '%')))) " +
            " ORDER BY l.create DESC")
    Page<Log> findAllLogByParam(@Param("level") String level,
                         @Param("logger") String logger,
                         @Param("text") String text,
                         @Param("startDate") LocalDateTime startDate,
                         @Param("endDate") LocalDateTime endDate,
                         Pageable pageable);
}
