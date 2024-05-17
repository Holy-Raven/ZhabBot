package ru.mkhamkha.ZhabBot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.mkhamkha.ZhabBot.omponent.LogCollector;
import ru.mkhamkha.ZhabBot.service.buisness.TelegramBot;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotInitializer {

    private final TelegramBot bot;
    private final LogCollector logCollector;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
           log.error("Произошла ошибка: " + e.getMessage());
        }
    }
}
