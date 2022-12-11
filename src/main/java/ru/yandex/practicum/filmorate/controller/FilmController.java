package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(final FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms(){
        log.debug("Получен запрос GET /films");
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Optional<Film> getFilmById(
            @PathVariable final int id
    ){
        log.debug("Получен запрос GET /films/{id}");
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Optional<Film> addFilm(
            @Valid @RequestBody final Film film,
            BindingResult result
    ){
        if(result.hasErrors()) {
            throw new ValidationException(result.getFieldError().getDefaultMessage());
        }
        log.debug("Получен запрос Post /films");
        return filmService.addFilm(film);
    }

    @PutMapping
    public Optional<Film> updateFilm(
            @Valid @RequestBody final Film film,
            BindingResult result
    ){
        if(result.hasErrors()) {
            throw new ValidationException(result.getFieldError().getDefaultMessage());
        }
        log.debug("Получен запрос Put /films");
        return filmService.updateFilm(film);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(defaultValue = "2", required = false) String count
    ) {
        log.debug("Получен запрос Get /films/popular");
        return filmService.getPopularFilms(Integer.parseInt(count));
    }

    @PutMapping("/{id}/like/{userId}")
    public Optional<Film> addLike(
            @PathVariable int id,
            @PathVariable int userId
    ) {
        log.debug("Получен запрос Put /films/{id}/like/{userId}");
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Optional<Film> deleteLike(
            @PathVariable int id,
            @PathVariable int userId
    ) {
        log.debug("Получен запрос Delete /films/{id}/like/{userId}");
        return filmService.deleteLike(id, userId);
    }

}
