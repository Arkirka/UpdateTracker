package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.constant.AccessType;
import ru.tinkoff.edu.java.scrapper.scheduler.Scheduler;


@Validated
@EnableScheduling
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @Bean Scheduler scheduler,
                                @Bean AccessType databaseAccessType,
                                String exchangeName, String queueName, String routingKey,
                                boolean useQueue) {
}