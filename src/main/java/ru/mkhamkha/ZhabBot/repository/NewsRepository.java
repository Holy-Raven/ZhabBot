package ru.mkhamkha.ZhabBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mkhamkha.ZhabBot.model.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
