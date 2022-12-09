package ru.yandex.practicum.filmorate.storage.Impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DBFilmStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public DBFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getFilms() {
        final String sql = "SELECT * FROM film";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        final String sql = "SELECT * FROM film where id = ?";
        final List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), id);

        if(films.isEmpty()) {
            throw new NotFoundException(String.format("Film id = '%d' not found", id));
        }

        return Optional.of(films.get(0));
    }

    @Override
    public Optional<Film> addFilm(Film film) {
        final String sql = "INSERT INTO public.film " +
                "(filmname, description, release_date, duration, mpa_id) " +
                "VALUES (?,?,?,?,?);";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(),
                film.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                film.getDuration(), film.getMpa().getId());

            film.setId(getIdFilmFromDB());
            addGenreInDB(film);

            return Optional.of(film);

    }

    @Override
    public Optional<Film> updateFilm(Film film) {
        final String sql = "UPDATE FILM SET FILMNAME=?, DESCRIPTION=?, RELEASE_DATE=?, " +
                "DURATION=?, MPA_ID=? WHERE id=?;";
        int resultQuery = jdbcTemplate.update(sql, film.getName(), film.getDescription(),
                film.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                film.getDuration(), film.getMpa().getId(), film.getId());
        if(resultQuery != 1) {
            throw new NotFoundException(String.format("Film id = '%d' not found", film.getId()));
        }

        deleteGenreFromDBByFilmId(film);
        addGenreInDB(film);
        return Optional.of(film);
    }

    @Override
    public Optional<Film> addLike(final Film film, final int userId) {
        final String sql = "INSERT INTO public.LIKES (film_id, user_id) VALUES (?,?);";
        jdbcTemplate.update(sql, film.getId(), userId);

        film.setLike(userId);
        return Optional.of(film);
    }

    @Override
    public Optional<Film> deleteLike(final Film film, final int userId) {

        final String sql = "DELETE FROM LIKES WHERE FILM_ID=? AND USER_ID=?;";
        jdbcTemplate.update(sql, film.getId(), userId);

        return getFilmById(film.getId());
    }

    public List<MPA> getMpa() {
        final String sql = "SELECT * FROM mpa";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMPA(rs));
    }

    public Optional<MPA> getMpaById(final int id) {
        final String sql = "SELECT * FROM MPA where ID=?;";
        final List<MPA> mpaList = jdbcTemplate.query(sql, (rs, rowNum) -> makeMPA(rs), id);

        if(mpaList.isEmpty()) {
            throw new NotFoundException(String.format("Mpa id = '%d' not found", id));
        }
        return Optional.of(mpaList.get(0));
    }

    public List<Genre> getGenres() {
        final String sql = "SELECT * FROM genre";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    public Optional<Genre> getGenreById(final int id) {
        final String sql = "SELECT * FROM genre where ID=?;";
        final List<Genre> genreList = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), id);

        if(genreList.isEmpty()) {
            throw new NotFoundException(String.format("Mpa id = '%d' not found", id));
        }
        return Optional.of(genreList.get(0));
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Film film = new Film(
                rs.getString("filmname"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                getMpaById(rs.getInt("mpa_id")).get()
        );

        film.setId(rs.getInt("id"));
        film = addLikeForFilm(film);
        return addGenreForFilm(film);
    }

    private MPA makeMPA(final ResultSet rs) throws SQLException {
        final MPA mpa = new MPA();

        mpa.setId(rs.getInt("id"));
        mpa.setName(rs.getString("mpaname"));
        return mpa;
    }

    private Genre makeGenre(ResultSet rs) throws SQLException{
        final Genre genre = new Genre();

        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("genrename"));
        return genre;
    }
    private Film addGenreForFilm(final Film film) {
        final String sql = "select g.*  from FILM_GENRE f inner join GENRE g " +
                "on g.ID = f.GENRE_ID where f.FILM_ID = ?;";
        final List<Genre> genres = jdbcTemplate
                .query(sql, (rs, rowNum) -> makeGenre(rs), film.getId());

        film.setGenres(genres.stream().collect(Collectors.toSet()));
        return film;
    }

    private Film addLikeForFilm(final Film film) {
        final String sql = "SELECT * FROM LIKES WHERE film_id=?;";
        final List<Integer> likes = jdbcTemplate
                .query(sql, (rs, rowNum) -> rs.getInt("user_id"), film.getId());

        film.setLikes(likes.stream().collect(Collectors.toSet()));
        return film;
    }

    private Integer getIdFilmFromDB() {
        final String sql = "select max(id) as id from public.film;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        if (rowSet.next()) {
            return rowSet.getInt("id");
        } else {
            throw new NotFoundException("id film from database not found");
        }
    }

    private void addGenreInDB(final Film film) {
        final String sql = "INSERT INTO public.film_genre (film_id, genre_id) VALUES (?,?);";

        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sql, film.getId(), genre.getId());
        }
    }

    private void deleteGenreFromDBByFilmId(final Film film) {
        final String sql = "DELETE FROM FILM_GENRE WHERE FILM_ID=?";
        jdbcTemplate.update(sql, film.getId());
    }


}
