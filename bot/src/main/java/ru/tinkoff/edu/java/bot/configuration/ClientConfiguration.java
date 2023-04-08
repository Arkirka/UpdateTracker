package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.client.ScrapperWebClient;
import ru.tinkoff.edu.java.bot.client.ScrapperWebClientImpl;

@Configuration
public class ClientConfiguration {
    @Bean
    public ScrapperWebClient scrapperWebClient(@Value("${scrapper.client.base-url}") String baseUrl) {
        return new ScrapperWebClientImpl(baseUrl);
    }
}
