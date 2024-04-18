package ru.mkhamkha.ZhabBot.service.buisness;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

import static ru.mkhamkha.ZhabBot.util.Constants.DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final NewsAnswerService newsAnswerService;

    public String newsAnswer(Integer top) {

        StringBuilder builder = new StringBuilder();

        newsAnswerService.topNews(top).forEach(message ->
        {
            builder.append(message.getTitle());
            builder.append(" - (" + message.getTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + ")");
            builder.append("\n\n");
            builder.append(message.getMessage());
            builder.append("\n\n");
        });

        return builder.toString();
    }

    public String descriptionAnswer() {

        return  "Вы спрашиваете нас, ЧТО такое ЖаБЪ?!" +
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
