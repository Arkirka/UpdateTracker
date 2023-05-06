package ru.tinkoff.edu.java.scrapper.client.stackoverflow;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.stackoverflow.StackOverflowAnswerResponse;

public class StackOverflowClientImpl implements StackOverflowClient{
    private static final String DEFAULT_URL = "https://api.stackexchange.com/2.3";
    private final WebClient webClient;

    public StackOverflowClientImpl(WebClient.Builder webClientBuilder, String baseUrl) {
        String url = baseUrl == null || baseUrl.isBlank() ? DEFAULT_URL : baseUrl;
        this.webClient = webClientBuilder.baseUrl(url).build();
    }

    @Override
    public StackOverflowAnswerResponse fetchLastQuestionAnswer(long questionId) {
        int countOfAnswers = 1;
        return webClient.get()
                .uri(
                        "/questions/{id}/answers?pagesize={countOfAnswers}&order=desc&sort=activity&site=stackoverflow",
                        questionId, countOfAnswers
                )
                .retrieve()
                .bodyToMono(StackOverflowAnswerResponse.class)
                .block();
    }
}
