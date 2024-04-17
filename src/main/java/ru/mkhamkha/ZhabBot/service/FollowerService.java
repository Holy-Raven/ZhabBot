package ru.mkhamkha.ZhabBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.mkhamkha.ZhabBot.entity.model.Follower;

import java.util.Optional;

public interface FollowerService {

    void addFollower(Update update);

    Optional<Follower> findById(Long followerId);

    Boolean deleteFollowerById(Long followerId);
}
