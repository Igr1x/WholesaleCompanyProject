package ru.varnavskii.wholesalecompany.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.varnavskii.wholesalecompany.entity.GoodsEntity;
import ru.varnavskii.wholesalecompany.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsDao {
    private static final GoodsDao INSTANCE = new GoodsDao();
    private static final String DELETE_SQL = "DELETE FROM goods WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO goods VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE goods SET name = ?, priority = ? WHERE id = ?";
    private static final String SELECT_SQL = "SELECT * FROM goods";

    private GoodsDao() {};

    public static GoodsDao getInstance() {
        return INSTANCE;
    }

    public boolean delete(Integer id) throws SQLException {
        try (Connection connection = ConnectionManager.get();
            var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GoodsEntity insert (GoodsEntity good) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setInt(1, good.getId());
            statement.setString(2, good.getName());
            statement.setInt(3, good.getPriority());
            statement.executeUpdate();
            return good;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String newName, Integer newPriority, Integer id) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, newName);
            statement.setInt(2, newPriority);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList select() throws SQLException {
        ObservableList<GoodsEntity> goodsList = FXCollections.observableArrayList();
        try (Connection connection = ConnectionManager.get();
            var statement = connection.prepareStatement(SELECT_SQL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                goodsList.add(new GoodsEntity(result.getInt("id"), result.getString("name"), result.getInt("priority")));
            }
            return goodsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
