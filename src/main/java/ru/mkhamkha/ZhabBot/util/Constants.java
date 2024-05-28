package ru.mkhamkha.ZhabBot.util;

public interface Constants {

    interface ErrorMessage {
        String FROM_ERROR_MESSAGE = "Индекс первого элемента не может быть отрицательным";
        String SIZE_ERROR_MESSAGE = "Количество отображаемых элементов должно быть положительным";
    }

    interface Formatter {
        String DATE_FORMAT = "yyyy-MM-dd";
        String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
        String DATE_FULL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }

    interface Menu {
        String START = "Войти во владение царя болот.";
        String DESCRIPTION = "Кто такой этот ваш ЖаБЪ?!";
        String NEWS = "Свежие новости с болот.";
        String CONCERTS = "Ближайшие концерты.";
        String MY_DATA = "Что ЖаБЪ о тебе знает.";
        String DELETE_DATA = "Попрощаться с ЖаБЪ-ом";
        String HELP = "Нужна помощь?";
        String MARKET = "Болотная барахолка";
    }
}
