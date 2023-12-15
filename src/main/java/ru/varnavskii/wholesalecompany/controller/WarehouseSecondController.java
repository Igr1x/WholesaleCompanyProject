package ru.varnavskii.wholesalecompany.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.varnavskii.wholesalecompany.dao.WarehouseSecondDao;
import ru.varnavskii.wholesalecompany.entity.WarehouseSecondEntity;
import ru.varnavskii.wholesalecompany.util.ExceptionHandler;

public class WarehouseSecondController {

    private final static Logger log = LoggerFactory.getLogger(WarehouseSecondController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<WarehouseSecondEntity, Integer> columnGoodCount;

    @FXML
    private TableColumn<WarehouseSecondEntity, Integer> columnGoodId;

    @FXML
    private TableColumn<WarehouseSecondEntity, Integer> columnId;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField goodCountTextField;

    @FXML
    private TextField goodIdTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private Button menuButton;

    @FXML
    private TableView<WarehouseSecondEntity> tableWarehouse;

    @FXML
    private Button updateButton;

    @FXML
    private TextArea infoText;

    @FXML
    void addGood(ActionEvent event) {
        try {
            WarehouseSecondEntity good = new WarehouseSecondEntity(Integer.parseInt(idTextField.getText()),
                    Integer.parseInt(goodIdTextField.getText()),
                    Integer.parseInt(goodCountTextField.getText()));
            WarehouseSecondDao.getInstance().insert(good);
            initialize();
            log.info("Добавлен новый товар на склад 2: {}", good.toString());
        } catch (SQLException e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.error(errorMessage);
        }
    }

    @FXML
    void deleteGood(ActionEvent event) {
        try {
            Integer id = Integer.parseInt(idTextField.getText());
            WarehouseSecondDao.getInstance().delete(id);
            initialize();
            log.info("Удален товар со склада 1, id - {}", id);
        } catch (Exception e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.error(errorMessage);
        }
    }

    @FXML
    void updateGood(ActionEvent event) {
        try {
            Integer id = Integer.parseInt(idTextField.getText());
            WarehouseSecondDao.getInstance().update(Integer.parseInt(goodCountTextField.getText()), id);
            initialize();
            log.info("Со скалад 1 удалён товар, id - {}", id);
        } catch (Exception e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.error(errorMessage);
        }
    }

    @FXML
    void goMenu(ActionEvent event) {
        menuButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/menu-view.fxml"));
        try {
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<WarehouseSecondEntity, Integer>("id"));
        columnGoodCount.setCellValueFactory(new PropertyValueFactory<WarehouseSecondEntity, Integer>("goodCount"));
        columnGoodId.setCellValueFactory(new PropertyValueFactory<WarehouseSecondEntity, Integer>("goodId"));
        try {
            ObservableList<WarehouseSecondEntity> list = WarehouseSecondDao.getInstance().select();
            tableWarehouse.setItems(list);
            log.info("SELECT запрос, таблица - warehouse2");
        } catch (Exception e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.error(errorMessage);
        }
    }

}
