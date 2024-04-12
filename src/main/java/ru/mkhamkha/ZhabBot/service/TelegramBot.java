package ru.mkhamkha.ZhabBot.service;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mkhamkha.ZhabBot.config.BotConfig;
import ru.mkhamkha.ZhabBot.model.Follower;

import java.util.ArrayList;
import java.util.List;

@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final FollowerService followerService;


    public TelegramBot(BotConfig config, FollowerService userService) {
        this.config = config;
        this.followerService = userService;

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
                case "/start" -> {
                    startCommand(chatId, update.getMessage().getChat().getFirstName());
                    log.info("Ответили пользователю: " + update.getMessage().getChat().getFirstName());

                    followerService.addFollower(update);
                }
                case "/description" -> {
                    String description = "ЖаБЪ царь болот и ему все обязаны.";
                    sendMessage(chatId, description);
                }
                case "/news" -> {
                    String news = "Живем потихонечку.";
                    sendMessage(chatId, news);
                }
                case "/concerts" -> {
                    String concerts = "Скоро затусим с байкерами - 22 июня в 21:00 на Nord-Feste";
                    sendMessage(chatId, concerts);
                }
                case "/mydata" -> {

                    String mydata;

                    if (followerService.findById(chatId).isPresent()) {
                        Follower follower = followerService.findById(chatId).get();
                        mydata = String.format("Знаю, что звать тебя - %s, а по батюшке - %s.", follower.getName(), follower.getFamily());
                    } else {
                        mydata = "Кажется мы не знакомы, жми /start и давай дружить.";
                    }

                    sendMessage(chatId, mydata);
                }
                case "/deldata" -> {

                    String deldata;

                    if (followerService.deleteFollowerById(chatId)) {
                        deldata = "Ой, а кто-это тут у нас? ЖаБЪ таких не знает! \n" +
                                  "Жми /start, давай знакомиться! ";
                    } else {
                        deldata = "ЖаБЪ и так тебя впервые видит! \n" +
                                  "Жми /start, давай знакомиться! ";
                    }
                    sendMessage(chatId, deldata);
                }
                case "/help" -> {
                    String help = "Бог поможет.";
                    sendMessage(chatId, help);
                }
                case "/settings" -> {
                    String settings = "Мы все уже настроили, иди лучше пивка бахни!";
                    sendMessage(chatId, settings);
                }
                default -> sendMessage(chatId, "Извини, моя твоя не понимать, приходи потом)");
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
