package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Optional<Film> addFilm(final Film film);
    Optional<Film> updateFilm(final Film film);
    List<Film> getFilms();
    Optional<Film> getFilmById(final int id);
    Optional<Film> addLike(final Film film, final int userId);
    Optional<Film> deleteLike(final Film film, final int userId);
    List<MPA> getMpa();
    Optional<MPA> getMpaById(final int id);
    List<Genre> getGenres();
    Optional<Genre> getGenreById(final int id);

}
