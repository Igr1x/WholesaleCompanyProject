package ru.varnavskii.wholesalecompany.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.varnavskii.wholesalecompany.dao.WarehouseFirstDao;
import ru.varnavskii.wholesalecompany.entity.WarehouseFirstEntity;
import ru.varnavskii.wholesalecompany.util.ControllerUtil;
import ru.varnavskii.wholesalecompany.util.ScenesPaths;

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
        } catch (NumberFormatException e) {
            log.warn(ControllerUtil.INCORRECT_DATA);
            infoText.setText(ControllerUtil.INCORRECT_DATA);
        } finally {
            clear();
        }
    }

    @FXML
    void deleteGood(ActionEvent event) {
        try {
            Integer id = Integer.parseInt(idTextField.getText());
            WarehouseFirstDao.getInstance().delete(id);
            initialize();
            log.info("Удален товар со склада 1, id - {}", id);
        } catch (NumberFormatException e) {
            log.warn(ControllerUtil.INCORRECT_DATA);
            infoText.setText(ControllerUtil.INCORRECT_DATA);
        } finally {
            clear();
        }
    }

    @FXML
    void updateGood(ActionEvent event) {
        try {
            Integer id = Integer.parseInt(idTextField.getText());
            WarehouseFirstDao.getInstance().update(Integer.parseInt(goodCountTextField.getText()), id);
            initialize();
            log.info("Со скалад 1 удалён товар, id - {}", id);
        } catch (NumberFormatException e) {
            log.warn(ControllerUtil.INCORRECT_DATA);
            infoText.setText(ControllerUtil.INCORRECT_DATA);
        } finally {
            clear();
        }
    }

    @FXML
    void goMenu(ActionEvent event) {
        ControllerUtil.openScene(menuButton, ScenesPaths.MENU_PATH);
    }

    @FXML
    void initialize() {
        clear();
        initializeWarehouseTable();
    }

    private void clear() {
        idTextField.clear();
        goodCountTextField.clear();
        goodIdTextField.clear();
    }

    private void initializeWarehouseTable() {
        columnId.setCellValueFactory(new PropertyValueFactory<WarehouseFirstEntity, Integer>("id"));
        columnGoodCount.setCellValueFactory(new PropertyValueFactory<WarehouseFirstEntity, Integer>("goodCount"));
        columnGoodId.setCellValueFactory(new PropertyValueFactory<WarehouseFirstEntity, Integer>("goodId"));

        ObservableList<WarehouseFirstEntity> list = WarehouseFirstDao.getInstance().select();

        tableWarehouse.setItems(list);
        log.info("SELECT запрос, таблица - warehouse1");
    }
}
