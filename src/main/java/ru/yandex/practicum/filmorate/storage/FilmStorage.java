package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> getFilms();
    Film getFilmById(final int id);
    Film addFilm(final Film film);
    Film updateFilm(final Film film);

}
