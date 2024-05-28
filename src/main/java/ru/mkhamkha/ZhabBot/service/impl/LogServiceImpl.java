package ru.mkhamkha.ZhabBot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.model.entity.Log;
import ru.mkhamkha.ZhabBot.repository.LogRepository;
import ru.mkhamkha.ZhabBot.service.LogService;
import ru.mkhamkha.ZhabBot.util.exception.exception.NotFoundException;

import java.time.*;
import java.time.format.DateTimeFormatter;

import static ru.mkhamkha.ZhabBot.util.Constants.Formatter.*;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Override
    public void addLog(Log log) {
        logRepository.save(log);
    }

    @Override
    public Log findLogById(Long logId) {
        return logRepository.findById(logId).orElseThrow(() ->
                new NotFoundException(Log.class, String.format("Log %d не найден", logId)));
    }

    @Override
    public Page<Log> findAllLogByParam(String level, String logger, String text, String startDate, String endDate, Pageable pageable) {

        LocalDateTime start = null;
        LocalDateTime end = null;

        // Парсинг даты начала и установка времени на начало дня
        if (startDate != null && !startDate.isEmpty()) {
            start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DATE_FORMAT)).atStartOfDay();
        }

        // Парсинг даты окончания и установка времени на конец дня
        if (endDate != null && !endDate.isEmpty()) {
            end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern(DATE_FORMAT)).atTime(LocalTime.MAX);
        } else {
            if (start != null) {
                end = start.with(LocalTime.MAX);
            }
        }

        return logRepository.findAllLogByParam(level, logger, text, start, end, pageable);
    }
}
