package ru.varnavskii.wholesalecompany.dao;

import ru.varnavskii.wholesalecompany.entity.UsersEntity;
import ru.varnavskii.wholesalecompany.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDao {
    private final static UsersDao INSTANCE = new UsersDao();
    private static final String INSERT_SQL = "INSERT INTO users VALUES (?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM users WHERE login = ? AND password = ?";
    private static final String SELECT_LOGIN_SQL = "SELECT * FROM users WHERE login = ?";

    private UsersDao(){};

    public static UsersDao getInstance() {
        return INSTANCE;
    }

    public boolean select(String login, String password) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SELECT_SQL)) {
            statement.setString(1, login);
            statement.setString(2, password);
            var result = statement.executeQuery();
            if (!result.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean selectLogin(String login) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SELECT_LOGIN_SQL)) {
            statement.setString(1, login);
            var result = statement.executeQuery();
            if (!result.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UsersEntity insert(UsersEntity user) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
