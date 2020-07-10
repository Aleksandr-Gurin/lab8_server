package ru.ifmo.se.jdbc;


import ru.ifmo.se.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO<User, String> {
    private final Connection connection;

    public UserDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public String create(final User user, String salt) {
        String result = "";

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT.QUERY);
             PreparedStatement statement2 = connection.prepareStatement(SQLUser.GET.QUERY)) {
            statement2.setString(1, user.getLogin());
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, salt);
            ResultSet resultSet = null;
            if(statement2.executeQuery().next()){
                result = "Пользователь с таким логином уже существует";
            } else {
                resultSet = statement.executeQuery();
            }

            if(resultSet != null && resultSet.next()){
                user.setId(resultSet.getInt("id"));
                result = "connect";
            }else if (result.equals("")){
                result = "Решистрация не прошла";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User read(final String login) {
        User result = new User();
        result.setLogin(login);
        result.setStatus(User.Status.UNREGISTER);
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET.QUERY)) {
            statement.setString(1, login);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setStatus(User.Status.UNLOGIN);
                result.setPassword(rs.getString("password"));
                result.setId(rs.getInt("id"));            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(final User user) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE.QUERY)) {
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(final User user) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.DELETE.QUERY)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String readSalt(User user){
        String salt = "";
        try(PreparedStatement statement = connection.prepareStatement(SQLUser.GETSALT.QUERY)){
            statement.setString(1, user.getLogin());
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                salt = rs.getString("salt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salt;
    }

    enum SQLUser {
        GET("SELECT password, id FROM users WHERE login = (?)"),
        INSERT("INSERT INTO users (id, login, password, salt) VALUES (DEFAULT, (?), (?), (?)) RETURNING id"),
        DELETE("DELETE FROM users WHERE id = (?) AND login = (?) AND password = (?) RETURNING id"),
        GETSALT("SELECT salt FROM users WHERE login = (?)"),
        UPDATE("UPDATE users SET password = (?) WHERE id = (?) RETURNING id");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
