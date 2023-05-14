package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClient;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClientImpl;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClientImpl;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClientImpl;
import ru.tinkoff.edu.java.scrapper.metric.MetricProcessor;

@Configuration
public class ClientConfiguration {
    @Bean
    public GitHubClient githubClient(WebClient.Builder webClientBuilder,
        @Value("${github.client.base-url}") String baseUrl) {
        return new GitHubClientImpl(webClientBuilder, baseUrl);
    }
    @Bean
    public StackOverflowClient stackOverflowClient(WebClient.Builder webClientBuilder,
        @Value("${stackoverflow.client.base-url}") String baseUrl) {
        return new StackOverflowClientImpl(webClientBuilder, baseUrl);
    }

    @Bean
    public BotClient botClient(WebClient.Builder webClientBuilder,
        @Value("${bot.client.base-url}") String baseUrl,
        MetricProcessor metricProcessor) {
        return new BotClientImpl(webClientBuilder, baseUrl, metricProcessor);
    }
}
