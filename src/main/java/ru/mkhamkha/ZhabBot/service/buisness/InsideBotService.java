package ru.mkhamkha.ZhabBot.service.buisness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class InsideBotService extends TelegramLongPollingBot {

    private String botUsername;
    private String botToken;

    private final ThreadLocal<Update> updateEvent = new ThreadLocal<>();

    protected void setBotCredentials(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {}

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setUpdateEvent(Update update) {
        this.updateEvent.set(update);
    }

    /**
     * Метод возвращает ID текущего Telegram-чата
     */
    private Long getCurrentChatId() {
        if (updateEvent.get().hasMessage()) {
            return updateEvent.get().getMessage().getFrom().getId();
        }

        if (updateEvent.get().hasCallbackQuery()) {
            return updateEvent.get().getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    /**
     * Метод отправляет в чат текстовое сообщение.
     * Поддерживается markdown-разметка.
     */
    protected void sendMessage(String sendMessage)  {
        SendMessage command = createApiSendMessageCommand(sendMessage);
        keyboard(command);
        executeTelegramApiMethod(command);
    }

    /**
     * Метод отправляет в чат Картинку.
     * Все картинки содержатся в папке resources/images
     */
    protected void sendImageMessage(String sendImage) {
        SendPhoto command = createApiPhotoMessageCommand(sendImage, null);
        executeTelegramApiMethod(command);
    }

    // Загрузка текста из resources/message
    protected String loadMessage(String name) {
        try {
            var is = ClassLoader.getSystemResourceAsStream("messages/" + name + ".txt");
            assert is != null;
            return new String(is.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't load message!");
        }
    }

    // Загрузка image из resources/images
    private InputStream loadImage(String name) {
        try {
            return ClassLoader.getSystemResourceAsStream("images/" + name + ".jpg");
        } catch (Exception e) {
            throw new RuntimeException("Can't load photo!");
        }
    }

    /**
     * Метод добавляет интерактивную клавиатуру.
     */
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

    private SendMessage createApiSendMessageCommand(String text) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        message.setChatId(Objects.requireNonNull(getCurrentChatId()));
        return message;
    }

    private SendPhoto createApiPhotoMessageCommand(String photoKey, String text) {
        try {
            InputFile inputFile = new InputFile();
            var is = loadImage(photoKey);
            inputFile.setMedia(is, photoKey);

            SendPhoto photo = new SendPhoto();
            photo.setPhoto(inputFile);
            photo.setChatId(Objects.requireNonNull(getCurrentChatId()));

            if (text != null && !text.isEmpty())
                photo.setCaption(text);

            return photo;
        } catch (Exception e) {
            throw new RuntimeException("Can't create photo message!");
        }
    }

    private <T extends Serializable, Method extends BotApiMethod<T>> void executeTelegramApiMethod(Method method) {
        try {
            super.sendApiMethod(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeTelegramApiMethod(SendPhoto message) {
        try {
            super.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
