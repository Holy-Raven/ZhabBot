package ru.mkhamkha.ZhabBot.service.buisness;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.model.entity.News;
import ru.mkhamkha.ZhabBot.service.NewsService;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.mkhamkha.ZhabBot.util.Constants.Formatter.DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class AnswerNewsService {

    private final NewsService service;
    private final EntityManager entityManager;

    public List<News> topNews(Integer top) {

        String query = "select * from zhab.news order by create_time desc limit :limit";
        Query nativeQuery = entityManager.createNativeQuery(query, News.class);
        nativeQuery.setParameter("limit", top);

        return nativeQuery.getResultList();
    }

    public String printNews(News news) {

        StringBuilder builder = new StringBuilder();

        builder.append(news.getTitle());
        builder.append(" - (").append(news.getTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT))).append(")");
        builder.append("\n\n");
        builder.append(news.getMessage());
        builder.append("\n");

        return builder.toString();
    }
}
