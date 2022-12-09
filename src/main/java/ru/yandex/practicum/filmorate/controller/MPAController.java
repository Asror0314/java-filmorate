package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class MPAController {

    private final FilmService filmService;

    public MPAController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/mpa")
    public List<MPA> getMpa() {
        log.debug("Получен запрос GET /mpa");
        return filmService.getMpa();
    }

    @GetMapping("/mpa/{id}")
    public Optional<MPA> getmpaById(
            @PathVariable int id
    ) {
        log.debug("Получен запрос Get /mpa/{id}");
        return filmService.getmpaById(id);
    }
}
