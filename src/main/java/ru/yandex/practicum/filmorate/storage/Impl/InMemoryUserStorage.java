package ru.yandex.practicum.filmorate.storage.Impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.LessThanZeroException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private int idUserGenerater = 0;
    private final Map<Integer, User> users = new HashMap();

    @Override
    public Optional<User> addUser(final User user){
        if(!users.containsKey(user.getId()) ) {
            user.setId(++idUserGenerater);
            users.put(user.getId(), user);
            return Optional.of(user);
        } else {
            throw new NotFoundException(String.format("User id = '%d' not found", user.getId()));
        }
    }

    @Override
    public Optional<User> updateUser(final User user){
        if(users.containsKey(user.getId()) ) {
            users.put(user.getId(), user);
            return Optional.of(user);
        } else if(user.getId() >= 0){
            throw new NotFoundException(String.format("User id = '%d' not found", user.getId()));
        } else {
            throw new LessThanZeroException(String.format("id = '%d' less than zero", user.getId()));
        }
    }

    @Override
    public List<User> getUsers(){
        return users.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUserById(final int id){
        User user = users.get(id);
        if(user == null) {
            throw new NotFoundException(String.format("User id = '%d' not found", id));
        }
        return Optional.of(users.get(id));
    }

    @Override
    public Optional<User> addFriends(int id, int friendId) {
        users.get(id).setFriend(friendId);

        final User user = users.get(id);
        user.setFriend(friendId);
        return Optional.of(user);
    }

    @Override
    public Optional<User> deleteFriends(int id, int friendId) {
        users.get(friendId)
                .getFriends()
                .removeIf(p -> p.equals(id));

        final User user = users.get(id);
        user.getFriends().removeIf(p -> p.equals(friendId));
        return Optional.of(user);
    }

}
