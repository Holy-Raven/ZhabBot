package ru.mkhamkha.ZhabBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mkhamkha.ZhabBot.entity.model.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
