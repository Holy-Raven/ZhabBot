package ru.mkhamkha.ZhabBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mkhamkha.ZhabBot.model.entity.News;

public interface NewsService {

    Page<News> findAllNews(Pageable pageable);

    News findNewsById(Long newsId);

    News addNews(News news);

    News updateNews(Long newsId, News news);

    void deleteNewsById(Long newsId);

    void deleteAllNews();
}
