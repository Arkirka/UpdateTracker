package ru.tinkoff.edu.java.scrapper.client.stackoverflow;

import ru.tinkoff.edu.java.scrapper.dto.StackOverflowResponse;

public interface StackOverflowClient {
    StackOverflowResponse fetchQuestion(long questionId);
}
