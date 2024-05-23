package ru.mkhamkha.ZhabBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkhamkha.ZhabBot.model.dto.LogDTO;
import ru.mkhamkha.ZhabBot.model.mapper.LogMapper;
import ru.mkhamkha.ZhabBot.service.LogService;

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




}