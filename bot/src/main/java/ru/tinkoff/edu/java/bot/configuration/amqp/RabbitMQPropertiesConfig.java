package ru.tinkoff.edu.java.bot.configuration.amqp;

public record RabbitMQPropertiesConfig(
        String queueName,
        String exchangeName,
        String routingKey
){}