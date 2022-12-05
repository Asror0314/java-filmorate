package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.List;

public interface UserStorage {

    List<User> getUsers();
    User getUserById(final int id);
    User addUser(final User user);
    User updateUser(final User user);
}
