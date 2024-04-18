package ru.mkhamkha.ZhabBot.service.buisness;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.model.entity.News;
import ru.mkhamkha.ZhabBot.service.NewsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsAnswerService {

    private final NewsService service;
    private final EntityManager entityManager;

    public List<News> topNews(Integer top) {

        String query = "select * from zhab.news order by create_time limit :limit";
        Query nativeQuery = entityManager.createNativeQuery(query, News.class);
        nativeQuery.setParameter("limit", top);

        return nativeQuery.getResultList();
    }
}
