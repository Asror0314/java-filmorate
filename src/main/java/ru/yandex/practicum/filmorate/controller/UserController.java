package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserValidation;
import java.util.Collection;

@RestController
@Slf4j
public class UserController {

    private final UserValidation userValid = new UserValidation();
    @GetMapping("/users")
    public Collection<User> getUsers(){
        log.debug("Получен запрос GET /users");
        return userValid.getUsers();
    }

    @PostMapping("/users")
    public User addUser(@RequestBody final User user){
        log.debug("Получен запрос Post /user");
        return userValid.addUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody final User user){
        log.debug("Получен запрос Patch /user");
        return userValid.updateUser(user);
    }
}
