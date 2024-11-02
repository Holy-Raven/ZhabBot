package ru.mkhamkha.ZhabBot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ru.mkhamkha.ZhabBot.config.property.BotProperties;
import ru.mkhamkha.ZhabBot.config.property.MailProperty;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {BotProperties.class, MailProperty.class})
public class MainConfig {

    private final MailProperty mailProperty;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(mailProperty.getHost());
        sender.setPort(mailProperty.getPort());
        sender.setUsername(mailProperty.getUsername());
        sender.setPassword(mailProperty.getPassword());

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        sender.setJavaMailProperties(props);

        return sender;
    }
}
