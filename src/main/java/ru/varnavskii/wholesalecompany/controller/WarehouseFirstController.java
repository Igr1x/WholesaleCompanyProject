package ru.varnavskii.wholesalecompany.controller;

import java.io.IOException;
import java.net.URL;
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
import ru.varnavskii.wholesalecompany.dao.WarehouseFirstDao;
import ru.varnavskii.wholesalecompany.entity.WarehouseFirstEntity;
import ru.varnavskii.wholesalecompany.util.ExceptionHandler;

public class WarehouseFirstController {

    private final static Logger log = LoggerFactory.getLogger(WarehouseFirstController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<WarehouseFirstEntity, Integer> columnGoodCount;

    @FXML
    private TableColumn<WarehouseFirstEntity, Integer> columnGoodId;

    @FXML
    private TableColumn<WarehouseFirstEntity, Integer> columnId;

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
    private TableView<WarehouseFirstEntity> tableWarehouse;

    @FXML
    private Button updateButton;

    @FXML
    private TextArea infoText;

    @FXML
    void addGood(ActionEvent event) {
        try {
            WarehouseFirstEntity good = new WarehouseFirstEntity(Integer.parseInt(idTextField.getText()),
                    Integer.parseInt(goodIdTextField.getText()),
                    Integer.parseInt(goodCountTextField.getText()));
            WarehouseFirstDao.getInstance().insert(good);
            initialize();
            log.info("Добавлен новый товар на склад 1: {}", good.toString());
        } catch (Exception e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.error(errorMessage);
        }
    }

    @FXML
    void deleteGood(ActionEvent event) {
        try {
            Integer id = Integer.parseInt(idTextField.getText());
            WarehouseFirstDao.getInstance().delete(id);
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
            WarehouseFirstDao.getInstance().update(Integer.parseInt(goodCountTextField.getText()), id);
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
        columnId.setCellValueFactory(new PropertyValueFactory<WarehouseFirstEntity, Integer>("id"));
        columnGoodCount.setCellValueFactory(new PropertyValueFactory<WarehouseFirstEntity, Integer>("goodCount"));
        columnGoodId.setCellValueFactory(new PropertyValueFactory<WarehouseFirstEntity, Integer>("goodId"));

        try {
            ObservableList<WarehouseFirstEntity> list = WarehouseFirstDao.getInstance().select();
            tableWarehouse.setItems(list);
            log.info("SELECT запрос, таблица - warehouse1");
        } catch (Exception e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.error(errorMessage);
        }
    }

}
