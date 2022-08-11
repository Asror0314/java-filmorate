package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @BeforeEach
    void beforeEach(){
        User user = User
                .builder()
                .id(1)
                .login("login1")
                .email("sa@gmail.com")
                .birthday(LocalDate.now())
                .build();
    }

    @Test
    void getUsers(){

    }

}