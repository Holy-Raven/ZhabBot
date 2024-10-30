package ru.mkhamkha.ZhabBot.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperty {

    private int port;
    private String host;
    private String username;
    private String password;
    private String address;
    private String target;
}
