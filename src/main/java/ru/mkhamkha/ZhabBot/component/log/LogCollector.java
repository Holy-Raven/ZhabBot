package ru.mkhamkha.ZhabBot.component.log;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mkhamkha.ZhabBot.model.entity.Log;
import ru.mkhamkha.ZhabBot.repository.LogRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogCollector {

    private final LogRepository logRepository;

    public void sendLog(Log logData) {
        logRepository.save(logData);
    }
}
