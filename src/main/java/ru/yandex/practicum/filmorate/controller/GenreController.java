package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class GenreController {

    private final FilmService filmService;

    public GenreController(FilmService filmService) {
        this.filmService = filmService;
    }
    @GetMapping("/genres")
    public List<Genre> getGenres() {
        log.debug("Получен запрос GET /genres");
        return filmService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public Optional<Genre> getGenreById(
            @PathVariable int id
    ) {
        log.debug("Получен запрос GET /genres/{id}");
        return filmService.getGenreById(id);
    }
}
