package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.LessThanZeroException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private int idUserGenerater = 0;
    private final Map<Integer, User> users = new HashMap();

    @Override
    public Collection<User> getUsers(){
        return users.values();
    }

    @Override
    public User getUserById(final int id){
        User user = users.get(id);
        if(user == null) {
            throw new NotFoundException(String.format("User id = '%d' not found", id));
        }
        return users.get(id);
    }

    @Override
    public User addUser(final User user){
        if(!users.containsKey(user.getId()) ) {
            if (isValid(user)) {
                user.setId(++idUserGenerater);
                nameIsBlank(user);
                users.put(user.getId(), user);
            }
            return user;
        } else {
            throw new NotFoundException(String.format("User id = '%d' not found", user.getId()));
        }
    }

    @Override
    public User updateUser(final User user){
        if(users.containsKey(user.getId()) ) {
            if (isValid(user)) {
                nameIsBlank(user);
                users.put(user.getId(), user);
            }
            return user;
        } else if(user.getId() >= 0){
            throw new NotFoundException(String.format("User id = '%d' not found", user.getId()));
        } else {
            throw new LessThanZeroException(String.format("id = '%d' less than zero", user.getId()));
        }
    }

    private boolean isValid(final User user){
        if(user.getLogin().isBlank()
                || user.getEmail().isBlank()
                || !user.getEmail().contains("@")
                || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException(String.format("Validation failed"));
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
