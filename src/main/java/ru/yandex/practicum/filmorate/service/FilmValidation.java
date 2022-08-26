package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.exception.LessThanZeroException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FilmValidation {

    private int idFilmGenerated = 0;
    private final Map<Integer, Film> films = new HashMap();
    private static final LocalDate RELEASEDATE = LocalDate.of(1895,12,28);


    public Collection<Film> getFilms(){
        return films.values();
    }

    public Film addFilm(final Film film){
            if (valid(film)) {
                film.setId(++idFilmGenerated);
                films.put(film.getId(), film);
            }
            return film;
    }

    public Film updateFilm(final Film film){
        if(films.containsKey(film.getId()) ) {
            if (valid(film)) {
                films.put(film.getId(), film);
            }
            return film;
        } else if(film.getId() >= 0){
            throw new ContainsException("This film does not exist");
        } else {
            throw new LessThanZeroException("Less than zero");
        }
    }

    private boolean valid(final Film film){
        if(film.getName().isBlank()
                || film.getDescription().length()>200
                || film.getDuration()<0
                || film.getReleaseDate().isBefore(RELEASEDATE)) {
            throw new ValidationException("Validation failed");
        } else {
            return true;
        }
    }
}
