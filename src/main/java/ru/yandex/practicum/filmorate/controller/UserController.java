package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.*;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        log.debug("Получен запрос GET /users");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(
            @PathVariable final int id
    ){
        log.debug("Получен запрос GET /users/{id}");
        return userService.getUserById(id);
    }

    @PostMapping
    public Optional<User> addUser(
            @Valid @RequestBody final User user,
            BindingResult result
    ){
        if(result.hasErrors()) {
            throw new ValidationException(result.getFieldError().getDefaultMessage());
        }
        log.debug("Получен запрос Post /users");
        return userService.addUser(user);
    }

    @PutMapping
    public Optional<User> updateUser(
            @Valid @RequestBody final User user,
            BindingResult result
    ){
        if(result.hasErrors()) {
            throw new ValidationException(result.getFieldError().getDefaultMessage());
        }
        log.debug("Получен запрос Patch /users");
        return userService.updateUser(user);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(
            @PathVariable final int id
    ) {
        log.debug("Получен запрос Get /users/{id}/friends/{friendId}");
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(
            @PathVariable final int id,
            @PathVariable final int otherId
    ) {
        log.debug("Получен запрос Get /users/{id}/friends/common/{friendId}");
        return userService.getCommonFriends(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Optional<User> addFriends(
            @PathVariable final int id,
            @PathVariable final int friendId
    ) {
        log.debug("Получен запрос Post /users/{id}/friends/{friendId}");
        return userService.addFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Optional<User> deleteFriends(
            @PathVariable final int id,
            @PathVariable final int friendId
    ) {
        log.debug("Получен запрос Delete /users/{id}/friends/{friendId}");
        return userService.deleteFriends(id, friendId);
    }

}
