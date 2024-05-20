package ru.mkhamkha.ZhabBot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mkhamkha.ZhabBot.config.property.BotProperties;

@Configuration
@EnableConfigurationProperties(value = {BotProperties.class})
public class MainConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper =  new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
