package ru.mkhamkha.ZhabBot.component.log;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mkhamkha.ZhabBot.model.entity.Log;
import ru.mkhamkha.ZhabBot.service.LogService;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogCollector {

    private final LogService logService;

    public void sendLog(Log log) {
        logService.addLog(log);
    }
}
