package ru.mkhamkha.ZhabBot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.mkhamkha.ZhabBot.service.UserService;
import ru.mkhamkha.ZhabBot.model.User;
import ru.mkhamkha.ZhabBot.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void addUser(Update update) {

        Long id = update.getMessage().getChatId();
        if (userRepository.findById(id).isEmpty()) {

            User user = User.builder()
                    .id(id)
                    .name(update.getMessage().getChat().getFirstName())
                    .family(update.getMessage().getChat().getLastName())
                    .create(LocalDateTime.now())
                    .build();

            userRepository.save(user);
            log.info("Пользователь {} добавлен в базу: ", update.getMessage().getChat().getFirstName());

        }
    }
}
