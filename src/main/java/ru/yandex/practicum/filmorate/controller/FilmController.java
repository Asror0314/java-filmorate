package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {

    private Map<Integer, Film> films = new HashMap<>();
    private static final LocalDate RELEASEDATE = LocalDate.of(1895,12,28);

    @GetMapping("/films")
    public Collection<Film> getFilms(){
        log.debug("Получен запрос GET /films");
        return films.values();
    }

    @PostMapping("/film")
    public Film addFilm(@RequestBody final Film film){
        log.debug("Получен запрос Post /film");
        if(films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ContainsException("This film does not exist");
        }
        return film;
    }

    @PatchMapping("/film")
    public Film updateFilm(@RequestBody final Film film){
        log.debug("Получен запрос Patch /film");
        if(films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ContainsException("This film already exists");
        }
        return film;
    }

    private boolean valid(final Film film){
        if(film.getName().isBlank()
                || film.getDescription().length()>200
                || film.getDuration()<0
                || film.getReleaseDate().isAfter(RELEASEDATE)) {
            throw new ValidationException("Validation failed");
        } else {
            return true;
        }
    }
}
