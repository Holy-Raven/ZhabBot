package ru.mkhamkha.ZhabBot.Service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.Service.UserService;
import ru.mkhamkha.ZhabBot.model.User;
import ru.mkhamkha.ZhabBot.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User addUser(User user) {

        return userRepository.save(user);
    }
}
