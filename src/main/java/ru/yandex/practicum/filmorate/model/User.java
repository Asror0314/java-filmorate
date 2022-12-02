package ru.yandex.practicum.filmorate.model;

import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Data
public class User {

    private int id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();

    public void setFriends(final int id) {
        friends.add(id);
    }

    public Set<Integer> getFriends() {
        return friends;
    }

}
