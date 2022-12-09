package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode
public class Film {

    private Integer id;
    @NotBlank(message = "Name cannot be null")
    private String name;
    @EqualsAndHashCode.Exclude
    private LocalDate releaseDate;

    @Size(max = 200, message = "Description should not be greater than 200 characters")
    private String description;

    @Min(value = 0, message = "duration should not be less than 0")
    private Integer duration;
    private MPA mpa;
    private List<Genre> genres = new ArrayList<>();
    private Set<Integer> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, Integer duration, MPA mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }

    public void setLike(final Integer likeId) {
        likes.add(likeId);
    }
    public void setLikes(Set<Integer> likes) {
        this.likes = likes;
    }
    public Set<Integer> getLikes() {
        return likes;
    }
    public Integer getLikesSize() {
        return likes.size();
    }
    public void setGenre(final Genre genre) {
        genres.add(genre);
    }
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
    public List<Genre> getGenres() {
        return genres;
    }

}
