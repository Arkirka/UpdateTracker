package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.bot.configuration.amqp.RabbitMQPropertiesConfig;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test, @Bean @NotNull String token,
                                @NotNull RabbitMQPropertiesConfig rabbitMQConfig,
                                @NotNull String metricsProcessedMessageCountName) {}
