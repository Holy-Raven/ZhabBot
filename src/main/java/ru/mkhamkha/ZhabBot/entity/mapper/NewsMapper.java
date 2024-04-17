package ru.mkhamkha.ZhabBot.entity.mapper;

import org.mapstruct.Mapper;
import ru.mkhamkha.ZhabBot.entity.model.News;
import ru.mkhamkha.ZhabBot.entity.dto.NewsDTO;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    News toEntity(NewsDTO newsDTO);

    NewsDTO toDTO(News news);
}
