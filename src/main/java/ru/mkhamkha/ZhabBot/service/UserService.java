package ru.mkhamkha.ZhabBot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserService {

    void addUser(Update update);
}
