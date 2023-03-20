package ru.tinkoff.edu.java.scrapper.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setDescription("Некорректные параметры запроса");
        errorResponse.setCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setExceptionName(ex.getClass().getSimpleName());
        errorResponse.setExceptionMessage(ex.getMessage());
        List<String> stackTrace = Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        errorResponse.setStackTrace(stackTrace);
        return errorResponse;
    }
}
