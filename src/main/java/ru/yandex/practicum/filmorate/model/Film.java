package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
public class Film {

    private int id;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @Size(max = 200, message = "Description should not be greater than 200 characters")
    private String description;

    @EqualsAndHashCode.Exclude
    private LocalDate releaseDate;

    @Min(value = 0, message = "duration should not be less than 0")
    private int duration;
    private final Set<Integer> likes = new HashSet<>();

    public void setLikes(final int id) {
        likes.add(id);
    }

    public Set<Integer> getLikes() {
        return likes;
    }
    public Integer getLikesSize() {
        return likes.size();
    }

}
