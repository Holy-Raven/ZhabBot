package ru.mkhamkha.ZhabBot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.model.entity.Log;
import ru.mkhamkha.ZhabBot.repository.LogRepository;
import ru.mkhamkha.ZhabBot.service.LogService;
import ru.mkhamkha.ZhabBot.util.exception.exception.NotFoundException;

import java.time.LocalDate;

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
    public Page<Log> findAllLogByParam(String level, String logger, String text, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return null;
    }
}
