package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.ContainsException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserValidation {

    private int idUserGenerated = 0;
    private final Map<Integer, User> users = new HashMap();

    public Collection<User> getUsers(){
        return users.values();
    }

    public User addUser(final User user){
        if(!users.containsKey(user.getId()) ) {
            if (valid(user)) {
                user.setId(++idUserGenerated);
                nameIsBlank(user);
                users.put(user.getId(), user);
            }
        } else {
            throw new ContainsException("This user already exists");
        }
        return user;
    }

    public User updateUser(final User user){
        if(users.containsKey(user.getId()) ) {
            if (valid(user)) {
                nameIsBlank(user);
                users.put(user.getId(), user);
            }
        } else {
            throw new ContainsException("This user does not exist");
        }
        return user;
    }

    private boolean valid(final User user){
        if(user.getLogin().isBlank()
                || user.getEmail().isBlank()
                || !user.getEmail().contains("@")
                || user.getBirthday().isAfter(LocalDate.now())) {
            return false;
        } else {
            return true;
        }
    }

    private User nameIsBlank(final User user){
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}

