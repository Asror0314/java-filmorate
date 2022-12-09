package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorage {

    Optional<User> addUser(final User user);
    Optional<User> updateUser(final User user);
    List<User> getUsers();
    Optional<User> getUserById(final int id);
    Optional<User> addFriends(final int id, final int friendId);
    Optional<User> deleteFriends(final int id, final int friendId);

}
