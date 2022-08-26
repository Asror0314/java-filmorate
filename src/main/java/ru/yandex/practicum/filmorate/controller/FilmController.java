package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmValidation;

import java.util.Collection;

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

    @PutMapping("/films")
    public Film updateFilm(@RequestBody final Film film){
        log.debug("Получен запрос Patch /film");
        return filmValid.updateFilm(film);
    }
}
