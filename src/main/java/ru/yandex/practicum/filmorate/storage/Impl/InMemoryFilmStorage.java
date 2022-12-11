package ru.yandex.practicum.filmorate.storage.Impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.LessThanZeroException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private int idFilmGenerater = 0;
    private final Map<Integer, Film> films = new HashMap();

    @Override
    public List<Film> getFilms(){
        return films.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Film> getFilmById(final int id){
        Film film = films.get(id);
        if(film == null) {
            throw new NotFoundException(String.format("User id = '%d' not found", id));
        }

        return Optional.of(films.get(id));
    }

    @Override
    public Optional<Film> addFilm(final Film film){
        film.setId(++idFilmGenerater);
        films.put(film.getId(), film);

        return Optional.of(film);
    }

    @Override
    public Optional<Film> updateFilm(final Film film){
        if(films.containsKey(film.getId()) ) {
            films.put(film.getId(), film);

            return Optional.of(film);
        } else if(film.getId() >= 0){
            throw new NotFoundException(String.format("Film id = '%d' not found", film.getId()));

        } else {
            throw new LessThanZeroException(String.format("Film id = '%d' less than zero", film.getId()));
        }
    }

    @Override
    public Optional<Film> addLike(
            final Film film,
            final int userId
    ) {
        film.setLike(userId);
        films.put(film.getId(), film);
        return Optional.of(film);
    }

    @Override
    public Optional<Film> deleteLike(
            final Film film,
            final int userId
    ) {
        film.getLikes().removeIf(p -> p.equals(userId));
        films.put(film.getId(), film);
        return Optional.of(film);
    }

    @Override
    public List<MPA> getMpa() {
        return null;
    }

    @Override
    public Optional<MPA> getMpaById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Genre> getGenres() {
        return null;
    }

    @Override
    public Optional<Genre> getGenreById(int id) {
        return Optional.empty();
    }
}
