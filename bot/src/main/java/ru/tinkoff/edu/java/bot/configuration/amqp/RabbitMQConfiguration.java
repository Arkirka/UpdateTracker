package ru.tinkoff.edu.java.bot.configuration.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    @Bean
    public Queue scrapperQueue(ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.rabbitMQConfig().queueName())
                .withArgument("x-dead-letter-exchange", applicationConfig.rabbitMQConfig().queueName().concat(".dlx"))
                .build();
    }

    @Bean
    public DirectExchange scrapperExchange(ApplicationConfig applicationConfig) {
        return new DirectExchange(applicationConfig.rabbitMQConfig().exchangeName());
    }

    @Bean
    public Binding bindingScrapper(Queue scrapperQueue, DirectExchange scrapperExchange) {
        return BindingBuilder.bind(scrapperQueue).to(scrapperExchange).withQueueName();
    }

    @Bean
    public FanoutExchange  deadLetterExchange(ApplicationConfig applicationConfig) {
        return new FanoutExchange(applicationConfig.rabbitMQConfig().queueName().concat(".dlx"));
    }

    @Bean
    public Queue deadLetterQueue(ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.rabbitMQConfig().queueName().concat(".dlq")).build();
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, FanoutExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

