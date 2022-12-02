package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(
            final UserStorage userStorage,
            final UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers(){
        log.debug("Получен запрос GET /users");
        return userStorage.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(
            @PathVariable final int id
    ){
        log.debug("Получен запрос GET /users/{id}");
        return userStorage.getUserById(id);
    }

    @PostMapping
    public User addUser(
            @RequestBody final User user){
        log.debug("Получен запрос Post /user");
        return userStorage.addUser(user);
    }

    @PutMapping
    public User updateUser(
            @RequestBody final User user){
        log.debug("Получен запрос Patch /user");
        return userStorage.updateUser(user);
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
    public User addFriends(
            @PathVariable final int id,
            @PathVariable final int friendId
    ) {
        log.debug("Получен запрос Post /users/{id}/friends/{friendId}");
        return userService.addFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriends(
            @PathVariable final int id,
            @PathVariable final int friendId
    ) {
        log.debug("Получен запрос Delete /users/{id}/friends/{friendId}");
        return userService.deleteFriends(id, friendId);
    }

}
