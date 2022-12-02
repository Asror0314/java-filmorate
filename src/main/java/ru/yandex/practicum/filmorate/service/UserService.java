package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getFriends(
            final int id
    ) {
        final List<User> friends = new ArrayList<>();

        for(Integer i: getFriendsIdList(id)) {
            friends.add(findUserById(i));
        }
        return friends;
    }

    public List<User> getCommonFriends(
            final int id,
            final int otherId
    ) {
        final List<User> commonFriends = new ArrayList<>();
        final List<Integer> userFriendsList = getFriendsIdList(id);
        final List<Integer> otherUserFriendsList = getFriendsIdList(otherId);

        List<Integer> commonIdList = userFriendsList
                .stream()
                .filter(p -> otherUserFriendsList.contains(p))
                .collect(Collectors.toList());

        if(!commonIdList.isEmpty()) {
            for (Integer i : commonIdList) {
                commonFriends.add(findUserById(i));
            }
        }

        return commonFriends;
    }

    public User addFriends(
            final int id,
            final int friendId
    ) {
        final User user = findUserById(id);
        findUserById(friendId).setFriends(id);

        user.setFriends(friendId);
        return user;
    }

    public User deleteFriends(
            final int id,
            final int friendId
    ) {
        final User user = findUserById(id);

        findUserById(friendId)
                .getFriends()
                .removeIf(p -> p.equals(id));

        user.getFriends().removeIf(p -> p.equals(friendId));
        return user;
    }

    private List<Integer> getFriendsIdList(final int id) {
        return findUserById(id)
                .getFriends()
                .stream()
                .collect(Collectors.toList());
    }

    public User findUserById(final int id) {
        return userStorage.getUsers()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("User id = %d not found", id)));
    }
}

