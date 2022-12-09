package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private static final LocalDate RELEASEDATE = LocalDate.of(1895,12,28);

    @Autowired
    public FilmService(
            @Qualifier("DBFilmStorage") final FilmStorage filmStorage,
            final UserService userService
    ) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public List<Film> getFilms(){
        return filmStorage.getFilms();
    }

    public Optional<Film> getFilmById(final int id){
        return filmStorage.getFilmById(id);
    }

    public Optional<Film> addFilm(final Film film){
        valid(film);
        return filmStorage.addFilm(film);
    }

    public Optional<Film> updateFilm(final Film film){
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

    public Optional<Film> addLike(
            final int id,
            final int userId
    ) {
        final Film film = filmStorage.getFilmById(id).get();

        userService.getUserById(userId);
        return filmStorage.addLike(film, userId);
    }

    public Optional<Film> deleteLike(
            final int id,
            final int userId
    ) {
        final Film film = filmStorage.getFilmById(id).get();

        userService.getUserById(userId);
        return filmStorage.deleteLike(film, userId);
    }

    public List<MPA> getMpa() {
        return filmStorage.getMpa();
    }

    public Optional<MPA> getmpaById(final int id) {
        return filmStorage.getMpaById(id);
    }

    public List<Genre> getGenres() {
        return filmStorage.getGenres();
    }

    public Optional<Genre> getGenreById(final int id) {
        return filmStorage.getGenreById(id);
    }

            private void valid(final Film film){
        if(film.getReleaseDate().isBefore(RELEASEDATE)) {
            throw new ValidationException(String.format("Release Date value cannot be less than %s", RELEASEDATE));
        }
    }
}
