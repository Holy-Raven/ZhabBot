package ru.mkhamkha.ZhabBot.model.mapper;

import org.springframework.stereotype.Component;
import ru.mkhamkha.ZhabBot.model.entity.News;
import ru.mkhamkha.ZhabBot.model.dto.NewsDTO;

@Component
public class NewsMapper {

    public static News toEntity(NewsDTO newsDTO) {

        return News.builder()
                .id(newsDTO.getId())
                .title(newsDTO.getTitle())
                .message(newsDTO.getMessage())
                .time(newsDTO.getTime())
                .build();
    }

    public static NewsDTO toDTO(News news) {

        return NewsDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .message(news.getMessage())
                .time(news.getTime())
                .build();
    }
}