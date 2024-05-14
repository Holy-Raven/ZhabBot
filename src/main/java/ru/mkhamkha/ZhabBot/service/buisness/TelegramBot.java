package ru.mkhamkha.ZhabBot.service.buisness;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mkhamkha.ZhabBot.config.property.BotProperties;
import ru.mkhamkha.ZhabBot.model.entity.Follower;
import ru.mkhamkha.ZhabBot.service.FollowerService;

import java.util.ArrayList;
import java.util.List;

import static ru.mkhamkha.ZhabBot.util.Constants.Menu.*;

@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotProperties properties;
    private final FollowerService followerService;
    private final MenuService menuService;


    public TelegramBot(BotProperties properties, FollowerService userService, MenuService menuService) {
        this.properties = properties;
        this.followerService = userService;
        this.menuService = menuService;

        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", START));
        listOfCommands.add(new BotCommand("/description", DESCRIPTION));
        listOfCommands.add(new BotCommand("/news", NEWS));
        listOfCommands.add(new BotCommand("/concerts", CONCERTS));
        listOfCommands.add(new BotCommand("/mydata", MY_DATA));
        listOfCommands.add(new BotCommand("/deldata", DELETE_DATA));
        listOfCommands.add(new BotCommand("/help", HELP));
        listOfCommands.add(new BotCommand("/market", MARKET));

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getName();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start" -> {

                    String answer = menuService.startAnswer(update.getMessage().getChat().getFirstName());
                    sendMessage(chatId, answer);

                    followerService.addFollower(update);
                }
                case "/description" -> {
                    String description = menuService.descriptionAnswer();
                    sendMessage(chatId, description);
                }
                case "/news" -> {
                    String news = menuService.newsAnswer(5);
                    sendMessage(chatId, news);
                }
                case "/concerts" -> {
                    String concerts = "Скоро затусим с байкерами - 22 июня в 21:00 на Nord-Feste";
                    sendMessage(chatId, concerts);
                }
                case "/market" -> {
                    String settings = "Продаем всякое. Присморись, у нас точно есть то что ты ищешь!";
                    sendMessage(chatId, settings);
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
                default -> sendMessage(chatId, "Извини, моя твоя не понимать, приходи потом)");
            }
        }
    }



    //интерактивная клавиатура
    private void keyboard(SendMessage message) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row;

        row = new KeyboardRow();
        row.add("/news");
        row.add("/concerts");
        row.add("/market");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("/help");
        row.add("/mydata");
        row.add("/deldata");

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
    }

    private void sendMessage(long chatId, String sendMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(sendMessage);

        keyboard(message);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка: " + e.getMessage());
        }
    }
}
