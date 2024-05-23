package ru.mkhamkha.ZhabBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mkhamkha.ZhabBot.model.entity.Log;

import java.time.LocalDate;

public interface LogService {

    void addLog(Log log);

    Log findLogById(Long logId);

    Page<Log> findAllLogByParam(String level, String logger, String text, LocalDate startDate, LocalDate endDate, Pageable pageable);

}
