package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public List<Film> getPopularFilms(
            final Integer count
    ) {
        return filmStorage
                .getFilms()
                .stream()
                .sorted((p0,p1)
                        -> -1 * Integer.valueOf(p0.getLikes().size()).compareTo(p1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());

    }

    public Film addLike(
            final int id,
            final int userId
    ) {
        final Film film = findFilmById(id);
        userService.findUserById(userId);

        film.setLikes(userId);
        return film;
    }

    public Film deleteLike(
            final int id,
            final int userId
    ) {
        final Film film = findFilmById(id);
        userService.findUserById(userId);

        film.getLikes().removeIf(p -> p.equals(userId));
        return film;
    }

    private Film findFilmById(final int id) {
        return filmStorage.getFilms()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Film id = %d not found", id)));
    }

}
