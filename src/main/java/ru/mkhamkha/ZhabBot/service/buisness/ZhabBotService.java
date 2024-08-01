package ru.mkhamkha.ZhabBot.service.buisness;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mkhamkha.ZhabBot.config.property.BotProperties;
import ru.mkhamkha.ZhabBot.model.entity.Follower;
import ru.mkhamkha.ZhabBot.service.FollowerService;

import java.util.ArrayList;
import java.util.List;

import static ru.mkhamkha.ZhabBot.util.Constants.Menu.*;

@Slf4j
@Component
public class ZhabBotService extends InsideBotService {

    protected final BotProperties properties;
    protected final FollowerService followerService;
    protected final MenuService menuService;

    public ZhabBotService(BotProperties properties, FollowerService followerService, MenuService menuService) {
        this.properties = properties;
        this.followerService = followerService;
        this.menuService = menuService;
        setBotCredentials(properties.getName(), properties.getToken());
    }

    @PostConstruct
    public void init() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", START));
        listOfCommands.add(new BotCommand("/description", DESCRIPTION));
        listOfCommands.add(new BotCommand("/news", NEWS));
        listOfCommands.add(new BotCommand("/concerts", CONCERTS));
        listOfCommands.add(new BotCommand("/market", MARKET));
        listOfCommands.add(new BotCommand("/mydata", MY_DATA));
        listOfCommands.add(new BotCommand("/deldata", DELETE_DATA));
        listOfCommands.add(new BotCommand("/help", HELP));

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка: {}", e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        setUpdateEvent(update);  // Устанавливаем текущее событие обновления

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start" -> {
                    String title = "*Привет, " + update.getMessage().getChat().getFirstName() + ", чем ЖаБЪ-bot может тебе помочь!*";
                    String text = loadMessage("main");
                    String answer = String.join("\n", title, text);
                    sendImageMessage("main");
                    sendMessage(answer);

                    menuService.startAnswer(update);
                }
                case "/description" -> {
                    String description = menuService.descriptionAnswer();
                    sendMessage(description);
                }
                case "/news" -> {
                    String news = menuService.newsAnswer(5);
                    sendMessage(news);
                }
                case "/concerts" -> {
                    String concerts = menuService.upcomingConcerts();
                    sendMessage(concerts);
                }
                case "/market" -> {
                    String settings = "Продаем всякое. Присморись, у нас точно есть то что ты ищешь!";
                    sendMessage(settings);
                }
                case "/mydata" -> {
                    String mydata;
                    if (followerService.findById(chatId).isPresent()) {
                        Follower follower = followerService.findById(chatId).get();
                        mydata = String.format("Знаю, что звать тебя - %s, а по батюшке - %s.", follower.getName(), follower.getFamily());
                    } else {
                        mydata = "Кажется мы не знакомы, жми /start и давай дружить.";
                    }
                    sendMessage(mydata);
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
                    sendMessage(deldata);
                }
                case "/help" -> {
                    String help = "Бог поможет.";
                    sendMessage(help);
                }
                default -> sendMessage("Извини, моя твоя не понимать, приходи потом)");
            }
        }
    }
}
