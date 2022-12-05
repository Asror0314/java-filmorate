package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.LessThanZeroException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private int idUserGenerater = 0;
    private final Map<Integer, User> users = new HashMap();

    @Override
    public List<User> getUsers(){
        return users.values().stream().collect(Collectors.toList());
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
            user.setId(++idUserGenerater);
            users.put(user.getId(), user);
            return user;
        } else {
            throw new NotFoundException(String.format("User id = '%d' not found", user.getId()));
        }
    }

    @Override
    public User updateUser(final User user){
        if(users.containsKey(user.getId()) ) {
            users.put(user.getId(), user);
            return user;
        } else if(user.getId() >= 0){
            throw new NotFoundException(String.format("User id = '%d' not found", user.getId()));
        } else {
            throw new LessThanZeroException(String.format("id = '%d' less than zero", user.getId()));
        }
    }
}
