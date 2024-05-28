package ru.mkhamkha.ZhabBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mkhamkha.ZhabBot.model.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
