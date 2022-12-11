package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.*;

@Service
@Slf4j
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Optional<User> addUser(final User user){
        nameIsBlank(user);
        return userStorage.addUser(user);
    }

    public Optional<User> updateUser(final User user){
        userStorage.getUserById(user.getId());
        nameIsBlank(user);
        return userStorage.updateUser(user);
    }

    public List<User> getUsers(){
        return userStorage.getUsers();
    }

    public Optional<User> getUserById(final int id){
        return userStorage.getUserById(id);
    }

    public Optional<User> addFriends(
            final int id,
            final int friendId
    ) {
        userStorage.getUserById(id);
        userStorage.getUserById(friendId);

        return userStorage.addFriends(id, friendId);
    }

    public Optional<User> deleteFriends(
            final int id,
            final int friendId
    ) {
        userStorage.getUserById(id);
        userStorage.getUserById(friendId);

        return userStorage.deleteFriends(id, friendId);
    }

    public List<User> getFriends(
            final int id
    ) {
        final List<User> friends = new ArrayList<>();

        for(Integer i: getFriendsIdList(id)) {
            friends.add(userStorage.getUserById(i).get());
        }
        return friends;
    }

    public List<User> getCommonFriends(
            final int id,
            final int otherId
    ) {
        final List<User> commonFriends = new ArrayList<>();
        final Set<Integer> userFriendsList = getFriendsIdList(id);
        final Set<Integer> otherUserFriendsList = getFriendsIdList(otherId);

        userFriendsList.retainAll(otherUserFriendsList);
        userFriendsList.forEach(i -> commonFriends.add(userStorage.getUserById(i).get()));

        return commonFriends;
    }

    private Set<Integer> getFriendsIdList(final int id) {
        return userStorage
                .getUserById(id)
                .get()
                .getFriends();
    }

    private Optional<User> nameIsBlank(final User user){
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return Optional.of(user);
    }

}

