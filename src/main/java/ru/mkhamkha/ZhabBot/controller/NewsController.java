package ru.mkhamkha.ZhabBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mkhamkha.ZhabBot.model.entity.News;
import ru.mkhamkha.ZhabBot.model.dto.NewsDTO;
import ru.mkhamkha.ZhabBot.model.mapper.NewsMapper;
import ru.mkhamkha.ZhabBot.service.NewsService;

import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.FROM_ERROR_MESSAGE;
import static ru.mkhamkha.ZhabBot.util.Constants.ErrorMessage.SIZE_ERROR_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Постраничная выдача всех новостей")
    public Page<NewsDTO> findAllNews(@PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                     @RequestParam(defaultValue = "0") Integer from,
                                     @Positive(message = SIZE_ERROR_MESSAGE)
                                     @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from, size);
        Page<News> news = newsService.findAllNews(page);

        log.info("[GET] request: /zhabalaka/admin/news");
        return news.map(NewsMapper::toDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Выдать новость по id")
    public NewsDTO findById(@PathVariable("id") Long newsId) {

        log.info("[GET] request: /zhabalaka/admin/news/{}", newsId);
        return NewsMapper.toDTO(newsService.findNewsById(newsId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Добавить новость")
    public NewsDTO addNews(@Valid @RequestBody NewsDTO newsDTO) {

        NewsDTO result = NewsMapper.toDTO(newsService.addNews(NewsMapper.toEntity(newsDTO)));

        log.info("[POST] request: /zhabalaka/admin/news, id:{}", result.getId());
        return result;
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Обновить имеющуюся новость по ее id")
    public NewsDTO updateNews(@RequestBody NewsDTO newsDTO,
                              @PathVariable("id") Long newsId) {

        log.info("[PUT] request: /zhabalaka/admin/news/{}", newsId);
        return NewsMapper.toDTO(newsService.updateNews(newsId, NewsMapper.toEntity(newsDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить новость по id")
    public void deleteById(@PathVariable("id") Long newsId) {

        log.info("[DEL] request: /zhabalaka/admin/news/{}", newsId);
        newsService.deleteNewsById(newsId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить все новости")
    public void deleteAllNews() {

        log.info("[DEL] request: /zhabalaka/admin/news");
        newsService.deleteAllNews();
    }
}

