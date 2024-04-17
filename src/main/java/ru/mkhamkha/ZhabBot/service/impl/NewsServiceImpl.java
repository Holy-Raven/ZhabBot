package ru.mkhamkha.ZhabBot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.util.exception.NotFoundException;
import ru.mkhamkha.ZhabBot.entity.model.News;
import ru.mkhamkha.ZhabBot.repository.NewsRepository;
import ru.mkhamkha.ZhabBot.service.NewsService;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public Page<News> findAllNews(Pageable pageable) {

        return newsRepository.findAll(pageable);
    }

    @Override
    public News findById(Long newsId) {

        return newsRepository.findById(newsId).orElseThrow(() ->
                new NotFoundException(News.class, String.format("News %d не найдена", newsId)));
    }
}
