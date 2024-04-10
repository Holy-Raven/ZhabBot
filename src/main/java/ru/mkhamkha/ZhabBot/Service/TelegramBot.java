package ru.mkhamkha.ZhabBot.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mkhamkha.ZhabBot.config.BotConfig;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Войти во владение царя болот."));
        listOfCommands.add(new BotCommand("/description", "Кто такой этот ваш ЖаБЪ?!"));
        listOfCommands.add(new BotCommand("/news", "Свежие новости с болот."));
        listOfCommands.add(new BotCommand("/concerts", "Ближайшие концерты."));
        listOfCommands.add(new BotCommand("/mydata", "Что ЖаБЪ о тебе знает."));
        listOfCommands.add(new BotCommand("/deldata", "Чпоньк, и ЖаБЪ забудет о тебе."));
        listOfCommands.add(new BotCommand("/help", "Нужна помощь?."));
        listOfCommands.add(new BotCommand("/settings", "Можно настроить и станет чутка получше."));

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start":
                    startCommand(chatId, update.getMessage().getChat().getFirstName());
                    log.info("Ответили пользователю: " + update.getMessage().getChat().getFirstName());
                    break;
                case "/description":
                    String description = "ЖаБЪ царь болот и ему все обязаны.";
                    sendMessage(chatId, description);
                    break;
                case "/news":
                    String news = "Живем потихонечку.";
                    sendMessage(chatId, news);
                    break;
                case "/concerts":
                    String concerts = "Скоро затусим с байкерами на Nord-Feste";
                    sendMessage(chatId, concerts);
                    break;
                case "/mydata":
                    String mydata = String.format("Знаю что звать тебя - %s, а по батюшке - %s.", update.getMessage().getChat().getFirstName(), update.getMessage().getChat().getLastName());
                    sendMessage(chatId, mydata);
                    break;
                case "/deldata":
                    String deldata = "Ха-ха, Нет! Тебе так просто от ЖаБЪ-а не скрыться! Моли о пощаде! ";
                    sendMessage(chatId, deldata);
                    break;
                case "/help":
                    String help = "Бог поможет.";
                    sendMessage(chatId, help);
                    break;
                case "/settings":
                    String settings = "Мы все уже настроили, иди лучше пивка бахни!";
                    sendMessage(chatId, settings);
                    break;
                default: sendMessage(chatId, "Извини, моя твоя не понимать, приходи потом)");
            }
        }
    }

    private void startCommand(long chatId, String name) {

        String answer = String.format("Привет %s, ЖаБЪ тебя скушает! Но не сегодня, живи пока что!", name);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String sendMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(sendMessage);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка: " + e.getMessage());
        }
    }
}
