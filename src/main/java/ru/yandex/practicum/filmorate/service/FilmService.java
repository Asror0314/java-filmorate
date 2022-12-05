package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private static final LocalDate RELEASEDATE = LocalDate.of(1895,12,28);

    @Autowired
    public FilmService(
            final FilmStorage filmStorage,
            final UserService userService
    ) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public List<Film> getFilms(){
        return filmStorage.getFilms();
    }

    public Film getFilmById(final int id){
        return filmStorage.getFilmById(id);
    }

    public Film addFilm(final Film film){
        valid(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(final Film film){
        valid(film);
        return filmStorage.updateFilm(film);
    }


    public List<Film> getPopularFilms(
            final Integer count
    ) {
        return filmStorage
                .getFilms()
                .stream()
                .sorted(Comparator.comparing(Film::getLikesSize).reversed())
                .limit(count)
                .collect(toList());
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

    private void valid(final Film film){
        if(film.getReleaseDate().isBefore(RELEASEDATE)) {
            throw new ValidationException(String.format("Validation failed"));
        }
    }
}
