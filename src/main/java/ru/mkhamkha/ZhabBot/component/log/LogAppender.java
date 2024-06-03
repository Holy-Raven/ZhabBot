package ru.mkhamkha.ZhabBot.component.log;

import ch.qos.logback.classic.spi.ILoggingEvent;

import ch.qos.logback.core.AppenderBase;
import ru.mkhamkha.ZhabBot.component.SpringContext;
import ru.mkhamkha.ZhabBot.model.entity.Log;

import java.time.Instant;
import java.time.ZoneId;

public class LogAppender extends AppenderBase<ILoggingEvent> {

    private LogCollector logCollector;

    @Override
    protected void append(ILoggingEvent eventObject) {

        String loggerName = eventObject.getLoggerName();

        if (eventObject.getLoggerName().startsWith("ru.mkhamkha.ZhabBot")
                && !loggerName.equals("ru.mkhamkha.ZhabBot.ZhabBotApplication")) {
            if (logCollector == null) {
                logCollector = SpringContext.getBean(LogCollector.class);
            }

            Log logData = Log.builder()
                    .level(eventObject.getLevel().toString())
                    .logger(eventObject.getLoggerName())
                    .message(eventObject.getFormattedMessage())
                    .create(Instant.ofEpochMilli(eventObject.getTimeStamp()).atZone(ZoneId.of("Europe/Moscow")).toLocalDateTime())
                    .build();

            logCollector.sendLog(logData);
        }
    }
}