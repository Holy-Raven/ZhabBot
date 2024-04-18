package ru.mkhamkha.ZhabBot.model.mapper;

import org.mapstruct.Mapper;
import ru.mkhamkha.ZhabBot.model.entity.News;
import ru.mkhamkha.ZhabBot.model.dto.NewsDTO;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    News toEntity(NewsDTO newsDTO);

    NewsDTO toDTO(News news);
}
