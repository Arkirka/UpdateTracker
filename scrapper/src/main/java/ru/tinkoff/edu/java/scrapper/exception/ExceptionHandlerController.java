package ru.tinkoff.edu.java.scrapper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return getApiErrorResponse("Некорректные параметры запроса",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ex.getStackTrace());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorResponse handleLinkNotFound(NotFoundException ex) {
        return getApiErrorResponse("Ссылка не найдена",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ex.getStackTrace());
    }

    private ApiErrorResponse getApiErrorResponse(String description, String simpleName, String message, StackTraceElement[] stackTrace2) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setDescription(description);
        errorResponse.setCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setExceptionName(simpleName);
        errorResponse.setExceptionMessage(message);
        List<String> stackTrace = Arrays.stream(stackTrace2)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        errorResponse.setStackTrace(stackTrace);
        return errorResponse;
    }
}
