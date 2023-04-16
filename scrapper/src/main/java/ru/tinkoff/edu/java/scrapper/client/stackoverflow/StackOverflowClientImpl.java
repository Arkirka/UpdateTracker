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

    /*@Override
    public StackOverflowAnswerResponse fetchQuestionAnswersBeforeId(long questionId, String id) {
        if (id == null)
            return fetchAllQuestionAnswers(questionId);
        int countOfAnswersPerPage = 10;
        int currentPage = 1;
        boolean found = false;
        List<Answer> allAnswers = new ArrayList<>();

        while (!found) {
            StackOverflowAnswerResponse response = webClient.get()
                    .uri(
                            "/questions/{questionId}/answers?page={currentPage}&pagesize={countOfAnswersPerPage}&order=desc&sort=activity&site=stackoverflow",
                            questionId, currentPage, countOfAnswersPerPage
                    )
                    .retrieve()
                    .bodyToMono(StackOverflowAnswerResponse.class)
                    .block();

            if (response != null && response.getAnswers() != null && !response.getAnswers().isEmpty()) {

                for (Answer answer : response.getAnswers()) {
                    if (answer.getAnswerId().equals(id)) {
                        found = true;
                        break;
                    }
                    allAnswers.add(answer);
                }

                if (!found) {
                    currentPage++;
                }
            } else {
                break;
            }
        }

        StackOverflowAnswerResponse result = new StackOverflowAnswerResponse();
        result.setAnswers(allAnswers);

        return result;
    }

    @Override
    public StackOverflowAnswerResponse fetchAllQuestionAnswers(long questionId) {
        int countOfAnswersPerPage = 10;
        int currentPage = 1;
        List<Answer> allAnswers = new ArrayList<>();

        while (true) {
            StackOverflowAnswerResponse response = webClient.get()
                    .uri(
                            "/questions/{questionId}/answers?page={currentPage}&pagesize={countOfAnswersPerPage}&order=desc&sort=activity&site=stackoverflow",
                            questionId, currentPage, countOfAnswersPerPage
                    )
                    .retrieve()
                    .bodyToMono(StackOverflowAnswerResponse.class)
                    .block();

            if (response != null && response.getAnswers() != null && !response.getAnswers().isEmpty())
                allAnswers.addAll(response.getAnswers());
            else
                break;
        }

        StackOverflowAnswerResponse result = new StackOverflowAnswerResponse();
        result.setAnswers(allAnswers);

        return result;
    }*/
}
