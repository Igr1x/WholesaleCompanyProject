package ru.varnavskii.wholesalecompany.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.varnavskii.wholesalecompany.Exception.TriggerException;
import ru.varnavskii.wholesalecompany.entity.SalesEntity;
import ru.varnavskii.wholesalecompany.util.ConnectionManager;
import ru.varnavskii.wholesalecompany.util.ExceptionHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SalesDao {
    private static final SalesDao INSTANCE = new SalesDao();

    private final Logger log = LoggerFactory.getLogger(SalesDao.class);

    private static final String DELETE_SQL = "DELETE FROM sales WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO sales VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE sales SET good_count = ? WHERE id = ?";
    private static final String SELECT_SQL = "SELECT * FROM sales";
    private static final String SELECT_DEMAIN_SQL = """
            SELECT * FROM sales
            WHERE good_id = ? AND create_date BETWEEN ? AND ?
            ORDER BY create_date;
            """;
    private static final String SELECT_DEMAIN_CHART_SQL = """
            SELECT good_count, TO_CHAR(create_date, 'YYYY-MM-DD') AS formated_date
            FROM sales
            WHERE good_id = ?
            ORDER BY create_date;
            """;

    private SalesDao() {};

    public static SalesDao getInstance() {
        return INSTANCE;
    }

    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SalesEntity insert(SalesEntity sale) throws TriggerException {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setInt(1, sale.getId());
            statement.setInt(2, sale.getGoodCount());
            statement.setTimestamp(3, sale.getCreateDate());
            statement.setInt(4, sale.getGoodId());
            statement.executeUpdate();
            return sale;
        } catch (PSQLException e) {
            throw new TriggerException(ExceptionHandler.getMessage(e));
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(Integer newGoodCount, Integer id) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, newGoodCount);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ObservableList<SalesEntity> select() {
        ObservableList<SalesEntity> salesList = FXCollections.observableArrayList();
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SELECT_SQL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                salesList.add(new SalesEntity(result.getInt("id"), result.getInt("good_count"),
                        result.getTimestamp("create_date"), result.getInt("good_id")));
            }
            return salesList;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Integer> selectDemain(Integer id, Timestamp startDate, Timestamp endDate) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SELECT_DEMAIN_SQL)) {
            statement.setInt(1, id);
            statement.setTimestamp(2, startDate);
            statement.setTimestamp(3, endDate);
            ResultSet result = statement.executeQuery();
            List<Integer> goodCountList = new ArrayList<>();
            while (result.next()) {
                goodCountList.add(result.getInt("good_count"));
            }
            return goodCountList;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Map<String, Integer> selectMapDemain(Integer id) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SELECT_DEMAIN_CHART_SQL)) {
            statement.setInt(1, id);
            Map<String, Integer> map = new LinkedHashMap<>();
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                map.put(result.getString("formated_date"), result.getInt("good_count"));
            }
            return map;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
