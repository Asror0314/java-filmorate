package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmValidation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {

    private final FilmValidation filmValid = new FilmValidation();
    @GetMapping("/films")
    public Collection<Film> getFilms(){
        log.debug("Получен запрос GET /films");
        return filmValid.getFilms();
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody final Film film){
        log.debug("Получен запрос Post /film");
        return filmValid.addFilm(film);
    }

    @PatchMapping("/films")
    public Film updateFilm(@RequestBody final Film film){
        log.debug("Получен запрос Patch /film");
        return filmValid.updateFilm(film);
    }
}
