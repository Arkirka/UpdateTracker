package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.metric.MetricProcessor;

import java.util.List;

@Service("bot")
public class TelegramBotImpl implements Bot {
    private final TelegramBot bot;
    private final UserMessageProcessor messageProcessor;

    private final MetricProcessor metricProcessor;

    public TelegramBotImpl(String token, UserMessageProcessor messageProcessor, MetricProcessor metricProcessor) {
        bot = new TelegramBot(token);
        this.messageProcessor = messageProcessor;
        this.metricProcessor = metricProcessor;
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
        metricProcessor.incrementMessageCount();
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            SendMessage response = messageProcessor.process(update);
            if (response != null) {
                bot.execute(response);
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void start() {
        bot.setUpdatesListener(this);
    }

    @Override
    public void close() {
        bot.removeGetUpdatesListener();
    }
}
