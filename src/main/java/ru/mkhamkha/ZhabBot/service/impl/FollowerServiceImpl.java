package ru.mkhamkha.ZhabBot.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.mkhamkha.ZhabBot.service.FollowerService;
import ru.mkhamkha.ZhabBot.model.entity.Follower;
import ru.mkhamkha.ZhabBot.repository.FollowerRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {

    private final FollowerRepository followerRepository;

    @Override
    @Transactional
    public void addFollower(Update update) {

        Long followerId = update.getMessage().getChatId();
        if (followerRepository.findById(followerId).isEmpty()) {

            Follower user = Follower.builder()
                    .id(followerId)
                    .name(update.getMessage().getChat().getFirstName())
                    .family(update.getMessage().getChat().getLastName())
                    .create(LocalDateTime.now())
                    .build();

            if (update.getMessage().getChat().getLastName() == null) {
                user.setFamily("безотцовщина");
            }

            followerRepository.save(user);
            log.info("Последователь добавлен в базу: " + update.getMessage().getChat().getFirstName());

        }
    }

    @Override
    public Optional<Follower> findById(Long followerId) {

        return followerRepository.findById(followerId);
    }

    @Override
    public Boolean deleteFollowerById(Long followerId) {

        if (followerRepository.findById(followerId).isPresent()) {
            followerRepository.deleteById(followerId);
            return true;
        } else {
            return false;
        }
    }
}
