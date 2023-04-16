package ru.tinkoff.edu.java.scrapper.client.stackoverflow;

import ru.tinkoff.edu.java.scrapper.dto.stackoverflow.StackOverflowAnswerResponse;

public interface StackOverflowClient {
    StackOverflowAnswerResponse fetchLastQuestionAnswer(long questionId);
}
