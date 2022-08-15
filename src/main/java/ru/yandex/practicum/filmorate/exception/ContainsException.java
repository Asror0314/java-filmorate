package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This user already exists")

public class ContainsException extends RuntimeException {

    public ContainsException(String message) {
        super(message);
    }
}
