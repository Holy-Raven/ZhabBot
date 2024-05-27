package ru.mkhamkha.ZhabBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mkhamkha.ZhabBot.model.entity.Log;

public interface LogService {

    void addLog(Log log);

    Log findLogById(Long logId);

    Page<Log> findAllLogByParam(String level, String logger, String text, String startDate, String endDate, Pageable pageable);

}
