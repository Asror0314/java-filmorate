package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    private Map<Integer, User> users = new HashMap();

    @GetMapping("/users")
    public Collection<User> getUsers(){
        log.debug("Получен запрос GET /users");
        return users.values();
    }

    @PostMapping("/users")
    public User addUser(@RequestBody final User user){
        log.debug("Получен запрос Post /user");
        if(!users.containsKey(user.getId()) ) {
            if(valid(user)) {
                if (user.getName().isBlank()) {
                    user.setName(user.getLogin());
                }
                users.put(user.getId(), user);
            }
        } else {
            throw new ContainsException("This user does not exist");
        }
        return user;
    }

    @PatchMapping("/users")
    public User updateUser(@RequestBody final User user){
        log.debug("Получен запрос Patch /user");
        if(users.containsKey(user.getId()) ) {
            if(valid(user)) {
                if (user.getName().isBlank()) {
                    user.setName(user.getLogin());
                }
                users.put(user.getId(), user);
            }
        } else {
            throw new ContainsException("This user already exists");
        }
        return user;
    }

    private boolean valid(final User user){
        if(user.getLogin().isBlank()
                || user.getEmail().isBlank()
                || user.getEmail().contains("@") || user.getBirthday().isBefore(LocalDate.now())) {
            throw new ValidationException("Validation failed");
        } else {
            return true;
        }

    }
}
