package ru.mkhamkha.ZhabBot.model.mapper;

import org.springframework.stereotype.Component;
import ru.mkhamkha.ZhabBot.model.dto.LogDTO;
import ru.mkhamkha.ZhabBot.model.entity.Log;

@Component
public class LogMapper {

    public static Log toEntity(LogDTO logDTO) {

        return Log.builder()
                .id(logDTO.getId())
                .level(logDTO.getLevel())
                .logger(logDTO.getLogger())
                .message(logDTO.getMessage())
                .create(logDTO.getCreate())
                .build();
    }


    public static LogDTO toDto(Log log) {

        return LogDTO.builder()
                .id(log.getId())
                .level(log.getLevel())
                .logger(log.getLogger())
                .message(log.getMessage())
                .create(log.getCreate())
                .build();
    }
}
