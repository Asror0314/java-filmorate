package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.LessThanZeroException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Film getFilmById(final int id){
        Film film = films.get(id);
        if(film == null) {
            throw new NotFoundException(String.format("User id = '%d' not found", id));
        }

        return films.get(id);
    }

    @Override
    public Film addFilm(final Film film){
        film.setId(++idFilmGenerater);
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film updateFilm(final Film film){
        if(films.containsKey(film.getId()) ) {
            films.put(film.getId(), film);

            return film;
        } else if(film.getId() >= 0){
            throw new NotFoundException(String.format("Film id = '%d' not found", film.getId()));

        } else {
            throw new LessThanZeroException(String.format("Film id = '%d' less than zero", film.getId()));
        }
    }

}
