package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
public class Film {

    private int id;
    private String name;
    private String description;
    @EqualsAndHashCode.Exclude
    private LocalDate releaseDate;
    private int duration;
    private final Set<Integer> likes = new HashSet<>();

    public void setLikes(final int id) {
        likes.add(id);
    }

    public Set<Integer> getLikes() {
        return likes;
    }

}
