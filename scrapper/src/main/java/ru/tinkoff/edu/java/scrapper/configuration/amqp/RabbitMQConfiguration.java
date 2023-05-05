package ru.tinkoff.edu.java.scrapper.configuration.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;

@RequiredArgsConstructor
@Configuration
public class RabbitMQConfiguration {

    @Bean
    public DirectExchange directExchange(ApplicationConfig applicationConfig) {
        return new DirectExchange(applicationConfig.rabbitMQConfig().exchangeName());
    }

    @Bean
    public Queue scrapperQueue(ApplicationConfig applicationConfig) {
        return QueueBuilder
                .durable(applicationConfig.rabbitMQConfig().queueName())
                .withArgument("x-dead-letter-exchange", applicationConfig.rabbitMQConfig().queueName() + ".dlx")
                .build();
    }

    @Bean
    public Binding binding(ApplicationConfig applicationConfig, Queue scrapperQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(scrapperQueue).to(directExchange).with(applicationConfig.rabbitMQConfig().routingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

