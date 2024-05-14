package ru.mkhamkha.ZhabBot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mkhamkha.ZhabBot.config.property.BotProperties;

@Configuration
@EnableConfigurationProperties(value = {BotProperties.class})
public class MainConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
