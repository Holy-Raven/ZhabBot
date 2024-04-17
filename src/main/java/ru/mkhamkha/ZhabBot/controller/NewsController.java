package ru.mkhamkha.ZhabBot.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkhamkha.ZhabBot.entity.model.News;
import ru.mkhamkha.ZhabBot.entity.dto.NewsDTO;
import ru.mkhamkha.ZhabBot.entity.mapper.NewsMapper;
import ru.mkhamkha.ZhabBot.service.NewsService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.mkhamkha.ZhabBot.util.Constants.FROM_ERROR_MESSAGE;
import static ru.mkhamkha.ZhabBot.util.Constants.SIZE_ERROR_MESSAGE;

@Log4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/news")
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<NewsDTO> findAllNews(@PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                     @RequestParam(defaultValue = "0") Integer from,
                                     @Positive(message = SIZE_ERROR_MESSAGE)
                                     @RequestParam(defaultValue = "100") Integer size) {

        PageRequest page = PageRequest.of(from, size);
        Page<News> news = newsService.findAllNews(page);

        log.info("GET request: /zhabalaka/admin/news");
        return news.stream().map(newsMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public NewsDTO findById(@PathVariable("id") Long newsId) {

        log.info("GET request: /zhabalaka/admin/news/" + newsId);
        return newsMapper.toDTO(newsService.findById(newsId));
    }
}

