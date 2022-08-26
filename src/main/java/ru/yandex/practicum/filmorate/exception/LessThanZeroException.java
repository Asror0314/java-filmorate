package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class LessThanZeroException extends RuntimeException {

    public LessThanZeroException(String message) {
        super(message);
    }
}
