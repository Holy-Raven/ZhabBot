package ru.mkhamkha.ZhabBot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mkhamkha.ZhabBot.entity.model.News;

public interface NewsService {

    Page<News> findAllNews(Pageable pageable);

    News findById(Long newsId);
}
