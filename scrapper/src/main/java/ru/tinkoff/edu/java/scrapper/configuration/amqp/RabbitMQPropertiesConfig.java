package ru.tinkoff.edu.java.scrapper.configuration.amqp;

public record RabbitMQPropertiesConfig(
        String queueName,
        String exchangeName,
        String routingKey
){}