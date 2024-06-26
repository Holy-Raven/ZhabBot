package ru.mkhamkha.ZhabBot.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.util.exception.exception.ConflictException;
import ru.mkhamkha.ZhabBot.util.exception.exception.NotFoundException;
import ru.mkhamkha.ZhabBot.model.entity.News;
import ru.mkhamkha.ZhabBot.repository.NewsRepository;
import ru.mkhamkha.ZhabBot.service.NewsService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public Page<News> findAllNews(Pageable pageable) {

        return newsRepository.findAll(pageable);
    }

    @Override
    public News findNewsById(Long newsId) {

        return newsRepository.findById(newsId).orElseThrow(() ->
                new NotFoundException(News.class, String.format("News %d не найдена", newsId)));
    }

    @Override
    public News addNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public News updateNews(Long newsId, News news) {

        News updatedNews = findNewsById(newsId);

        if (news.getTitle() != null)
            updatedNews.setTitle(news.getTitle());
        if (news.getMessage() != null)
            updatedNews.setMessage(news.getMessage());
        if (news.getTime() != null)
            updatedNews.setTime(news.getTime());

        return newsRepository.save(updatedNews);
    }

    @Override
    public void deleteNewsById(Long newsId) {

        newsRepository.delete(findNewsById(newsId));
    }

    @Override
    public void deleteAllNews() {

        List<News> forDel = newsRepository.findAll();

        try {
            if (forDel.isEmpty()) throw new ConflictException("Список новостей пуст.");
        } catch (ConflictException e) {
            log.error(e.getMessage());
        }

        newsRepository.deleteAll(forDel);
    }
}
