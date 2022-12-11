package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Data
public class User {

    private Integer id;
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Login cannot be null")
    private String login;

    @Past(message = "Date value cannot be in the future")
    private LocalDate birthday;

    private Set<Integer> friends = new HashSet<>();

    public void setFriend(final Integer id) {
        friends.add(id);
    }

    public void setFriends(Set<Integer> friends) {
        this.friends = friends;
    }

    public Set<Integer> getFriends() {
        return friends;
    }

}
