package ru.varnavskii.wholesalecompany.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.varnavskii.wholesalecompany.entity.WarehouseSecondEntity;
import ru.varnavskii.wholesalecompany.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WarehouseSecondDao {
    private static final WarehouseSecondDao INSTANCE = new WarehouseSecondDao();
    private static final String DELETE_SQL = "DELETE FROM warehouse2 WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO warehouse2 VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE warehouse2 SET good_count = ? WHERE id = ?";
    private static final String SELECT_SQL = "SELECT * FROM warehouse2";

    private WarehouseSecondDao() {};

    public static WarehouseSecondDao getInstance() {
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

    public WarehouseSecondEntity insert (WarehouseSecondEntity warehouse) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setInt(1, warehouse.getId());
            statement.setInt(2, warehouse.getGoodId());
            statement.setInt(3, warehouse.getGoodCount());

            statement.executeUpdate();
            return warehouse;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Integer newGoodCount, Integer id) throws SQLException {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, newGoodCount);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList select() throws SQLException {
        ObservableList<WarehouseSecondEntity> warehouseList = FXCollections.observableArrayList();
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SELECT_SQL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                warehouseList.add(new WarehouseSecondEntity(result.getInt("id"), result.getInt("good_id"), result.getInt("good_count")));
            }
            return warehouseList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
