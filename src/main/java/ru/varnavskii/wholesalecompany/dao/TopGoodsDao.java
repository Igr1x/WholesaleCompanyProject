package ru.varnavskii.wholesalecompany.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.varnavskii.wholesalecompany.entity.TopGoodsEntity;
import ru.varnavskii.wholesalecompany.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TopGoodsDao {
    private static final TopGoodsDao INSTATNCE = new TopGoodsDao();

    private final Logger log = LoggerFactory.getLogger(TopGoodsDao.class);

    private static final String SELECT_SQL = """
            SELECT goods.name, SUM(good_count) AS good_count
            FROM sales
            JOIN goods ON sales.good_id = goods.id
            GROUP BY goods.name
            ORDER BY SUM(good_count) DESC
            LIMIT 5
            """;

    private TopGoodsDao() {};

    public static TopGoodsDao getInstance() {
        return INSTATNCE;
    }

    public ObservableList<TopGoodsEntity> select() {
        ObservableList<TopGoodsEntity> goodList = FXCollections.observableArrayList();
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SELECT_SQL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                goodList.add(new TopGoodsEntity(result.getString("name"), result.getInt("good_count")));
            }
            return goodList;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
