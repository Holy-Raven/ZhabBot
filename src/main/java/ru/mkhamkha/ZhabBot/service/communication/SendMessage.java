package ru.mkhamkha.ZhabBot.service.communication;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.mkhamkha.ZhabBot.config.property.MailProperty;
import ru.mkhamkha.ZhabBot.model.dto.SendMailData;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SendMessage {

    private final JavaMailSender mailSender;
    private final MailProperty mailProperty;

    @Transactional
    public void sendMessage(SendMailData sendMailData) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        mimeMessageHelper.setTo(InternetAddress.parse(mailProperty.getTarget()));
        mimeMessageHelper.setSubject(sendMailData.getSubject());
        mimeMessageHelper.setText(sendMailData.getHtmlBody(), true);

        mailSender.send(mimeMessage);
    }
}