package ru.yandex.practicum.filmorate.storage.Impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DBUserStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public DBUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() {
        final String sql = "SELECT * FROM users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public Optional<User> getUserById(final int id) {
        final String sql = "SELECT * FROM users where id = ?";
        final List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), id);

        if(users.isEmpty()) {
            throw new NotFoundException(String.format("User id = '%d' not found", id));
        }

        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> addUser(final User user){
        final String sql = "INSERT INTO public.USERS " +
                "(LOGIN, USERNAME, EMAIL, BIRTHDAY) VALUES (?,?,?,?);";
        jdbcTemplate.update(sql, user.getLogin(), user.getName(), user.getEmail(),
                user.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        user.setId(getIdUserFromDB());

        return Optional.of(user);
    }

    @Override
    public Optional<User> updateUser(final User user) {
        final String sql = "UPDATE USERS SET LOGIN = ?, USERNAME = ?, " +
                "EMAIL =?, BIRTHDAY =? WHERE id = ?;";
        jdbcTemplate.update(sql, user.getLogin(), user.getName(), user.getEmail(),
                user.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), user.getId());

        return Optional.of(user);
    }

    @Override
    public Optional<User> addFriends(final int id, final int friendId) {
        final String sql = "INSERT INTO public.FRIENDS (USER_ID, FRIEND_ID) VALUES (?,?);";
        jdbcTemplate.update(sql, id, friendId);

        return getUserById(id);
    }

    @Override
    public Optional<User> deleteFriends(final int id, final int friendId) {
        final String sql = "DELETE FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?;";
        jdbcTemplate.update(sql, id, friendId);

        return getUserById(id);
    }

    private Integer getIdUserFromDB() {
        final String sql = "select max(id) as id from public.users;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        if (rowSet.next()) {
            return rowSet.getInt("id");
        } else {
            throw new NotFoundException("id film from database not found");
        }
    }

    private User makeUser(ResultSet rs) throws SQLException {
        final User user = new User();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());

        return addFriendsInUser(user);
    }

    private User addFriendsInUser(final User user) throws SQLException {
        final String sql = "SELECT * FROM friends WHERE user_id =?";
        final List<Integer> friendsList = jdbcTemplate
                .query(sql, (rs, rowNum) -> rs.getInt("friend_id"), user.getId());

        user.setFriends(friendsList.stream().collect(Collectors.toSet()));

        return user;
    }
}
