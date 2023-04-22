package ru.tinkoff.edu.java.scrapper.client.stackoverflow;

import ru.tinkoff.edu.java.scrapper.dto.stackoverflow.StackOverflowResponse;

public interface StackOverflowClient {
    StackOverflowResponse fetchQuestion(long questionId);
}
