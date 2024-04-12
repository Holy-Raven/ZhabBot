package ru.mkhamkha.ZhabBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mkhamkha.ZhabBot.model.Follower;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
}
