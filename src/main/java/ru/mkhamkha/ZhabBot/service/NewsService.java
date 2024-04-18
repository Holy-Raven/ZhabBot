package ru.mkhamkha.ZhabBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mkhamkha.ZhabBot.model.dto.NewsDTO;
import ru.mkhamkha.ZhabBot.model.entity.News;

public interface NewsService {

    Page<News> findAllNews(Pageable pageable);

    News findById(Long newsId);

    News addNews(News news);

    News updateNews(Long newsId, NewsDTO newsDTO);

    void deleteNewsById(Long newsId);

    void deleteAll();
}
