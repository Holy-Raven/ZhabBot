package ru.mkhamkha.ZhabBot.service.buisness;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.model.entity.Concert;
import ru.mkhamkha.ZhabBot.model.entity.News;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final AnswerNewsService answerNewsService;
    private final AnswerConcertService answerConcertService;

    public String startAnswer(String name) {

        //кодировка смайлов взята с https://emojisup.org
        return EmojiParser.parseToUnicode("Привет, " + name + " ЖаБЪ тебя скушает! Но не сегодня, живи пока что!" + " :blush:");
    }

    public String newsAnswer(Integer top) {

        List<News> news = answerNewsService.topNews(top);

        if (!news.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            news.forEach(message ->
            {
                builder.append(answerNewsService.printNews(message));
                builder.append("______________________________________");
                builder.append("\n\n");

            });

            return builder.toString();
        } else {
            return "А новостей то и нет, все тихо спокойно.";
        }
    }

    public String upcomingConcerts() {

        List<Concert> concerts = answerConcertService.upcomingConcerts();

        if (!concerts.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            concerts.forEach(message ->
            {
                builder.append(answerConcertService.printConcert(message));
                builder.append("______________________________________");
                builder.append("\n\n");

            });

            return builder.toString();
        } else {
            return "Концертов пока не назначено, следите за новостями. Все будет!";
        }
    }

    public String descriptionAnswer() {

        return "Вы спрашиваете нас, ЧТО такое ЖаБЪ?!" +
                "\n\n" +
                "ЖаБЪ — это музыка, которую невозможно вместить в узкие рамки одного стиля. Это целая вселенная, " +
                "где эпичный викинг-метал органично сочетается с заводными народными мотивами, мощные гитарные риффы " +
                "переплетаются с задорными переливами этнических инструментов, а каждая песня — это отдельная история, " +
                "повествующая о приключениях могучего болотного тролля." +
                "\n\n" +
                "Вы спрашиваете нас, КТО такой ЖаБЪ?" +
                "\n\n" +
                "ЖаБЪ — это могучий болотный тролль, который рано или поздно всех нас съест." +
                "\n" +
                "А пока он этого не сделал, мы продолжаем восхвалять ЖаБЪ-а в своих песнях!" +
                "\n\n" +
                "ЖаБЪ в VK:\n" +
                "https://vk.com/zhabalaka";
    }
}
