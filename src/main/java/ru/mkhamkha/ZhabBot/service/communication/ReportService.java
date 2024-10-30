package ru.mkhamkha.ZhabBot.service.communication;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import ru.mkhamkha.ZhabBot.model.dto.SendMailData;
import ru.mkhamkha.ZhabBot.util.exception.exception.MailException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final SendMessage sendMessage;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    public void sendErrorMessage(String subject, String message)  {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("date", LocalDate.now());
        thymeleafContext.setVariable("message", message);

        String htmlBody = thymeleafTemplateEngine.process("message-form.html", thymeleafContext);

        SendMailData sendMailData = SendMailData.builder()
                .subject(subject + LocalDate.now())
                .htmlBody(htmlBody)
                .build();
        try {
            sendMessage.sendMessage(sendMailData);
        } catch (MessagingException e) {
            throw new MailException(e.getMessage());
        }
    }
}
