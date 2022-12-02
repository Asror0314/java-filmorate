package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.LessThanZeroException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private int idFilmGenerater = 0;
    private final Map<Integer, Film> films = new HashMap();
    private static final LocalDate RELEASEDATE = LocalDate.of(1895,12,28);

    @Override
    public Collection<Film> getFilms(){
        return films.values();
    }

    @Override
    public Film getFilmById(final int id){
        Film film = films.get(id);
        if(film == null) {
            throw new NotFoundException(String.format("User id = '%d' not found", id));
        }
        return films.get(id);
    }

    @Override
    public Film addFilm(final Film film){
        if (valid(film)) {
            film.setId(++idFilmGenerater);
            films.put(film.getId(), film);
        }
        return film;
    }

    @Override
    public Film updateFilm(final Film film){
        if(films.containsKey(film.getId()) ) {
            if (valid(film)) {
                films.put(film.getId(), film);
            }
            return film;
        } else if(film.getId() >= 0){
            throw new NotFoundException(String.format("Film id = '%d' not found", film.getId()));

        } else {
            throw new LessThanZeroException(String.format("Film id = '%d' less than zero", film.getId()));
        }
    }

    private boolean valid(final Film film){
        if(film.getName().isBlank()
                || film.getDescription().length() > 200
                || film.getDuration() < 0
                || film.getReleaseDate().isBefore(RELEASEDATE)) {
            throw new ValidationException(String.format("Validation failed"));
        } else {
            return true;
        }
    }
}
