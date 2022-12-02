package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getFilms(){
        log.debug("Получен запрос GET /films");
        return filmStorage.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(
            @PathVariable final int id
    ){
        log.debug("Получен запрос GET /films/{id}");
        return filmStorage.getFilmById(id);
    }

    @PostMapping
    public Film addFilm(@RequestBody final Film film){
        log.debug("Получен запрос Post /films");
        return filmStorage.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody final Film film){
        log.debug("Получен запрос Put /films");
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(defaultValue = "2", required = false) String count) {
        log.debug("Получен запрос Get /films/popular");
        return filmService.getPopularFilms(Integer.parseInt(count));
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(
            @PathVariable int id,
            @PathVariable int userId
    ) {
        log.debug("Получен запрос Put /films/{id}/like/{userId}");
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(
            @PathVariable int id,
            @PathVariable int userId
    ) {
        log.debug("Получен запрос Delete /films/{id}/like/{userId}");
        return filmService.deleteLike(id, userId);
    }
}
