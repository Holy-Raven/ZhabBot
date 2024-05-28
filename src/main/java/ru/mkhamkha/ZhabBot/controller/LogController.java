package ru.mkhamkha.ZhabBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkhamkha.ZhabBot.model.dto.LogDTO;
import ru.mkhamkha.ZhabBot.model.entity.Log;
import ru.mkhamkha.ZhabBot.model.mapper.LogMapper;
import ru.mkhamkha.ZhabBot.service.LogService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/logs")
public class LogController {

    private final LogService logService;

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Выдать лог по id")
    public LogDTO findLogById(@PathVariable("id") Long logId) {

        log.info("[GET] request: /zhabalaka/admin/logs/{}", logId);
        return LogMapper.toDto(logService.findLogById(logId));
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Постраничная выдача логов с учетом заданных параметров")
    public Page<LogDTO> findAllLogByParam(@RequestParam(required = false, name = "level") String level,
                                          @RequestParam(required = false, name = "logger") String logger,
                                          @RequestParam(required = false, name = "text") String text,
                                          @RequestParam(required = false, name = "startDate") String startDate,
                                          @RequestParam(required = false, name = "endDate") String endDate,
                                          @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                          @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(from, size);

        Page<Log> logsPage = logService.findAllLogByParam(level, logger, text, startDate, endDate, pageable);
        List<LogDTO> logDTOList = logsPage.stream().map(LogMapper::toDto).toList();
        Page<LogDTO> result = new PageImpl<>(logDTOList, pageable, logsPage.getTotalElements());

        log.info("[GET] request: /zhabalaka/admin/logs/ from:{}, size:{}", from, size);
        return result;
    }
}