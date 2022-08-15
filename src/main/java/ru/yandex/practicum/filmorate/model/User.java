package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NonNull
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String login;
    private LocalDate birthday;

}
