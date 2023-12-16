package ru.varnavskii.wholesalecompany.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import ru.varnavskii.wholesalecompany.dao.GoodsDao;
import ru.varnavskii.wholesalecompany.entity.GoodsEntity;
import ru.varnavskii.wholesalecompany.util.ControllerUtil;
import ru.varnavskii.wholesalecompany.util.ReportExcel;
import ru.varnavskii.wholesalecompany.util.ScenesPaths;

public class GoodsController {
    private final static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextArea infoText;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TableView<GoodsEntity> tableGoods;

    @FXML
    private TableColumn<GoodsEntity, Integer> columnId;

    @FXML
    private TableColumn<GoodsEntity, String> columnName;

    @FXML
    private TableColumn<GoodsEntity, Integer> columnPriority;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priorityTextField;

    @FXML
    private Button menuButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button reportButton;

    @FXML
    void downloadReport(ActionEvent event) {
        try {
            ReportExcel report = new ReportExcel();
            ObservableList<GoodsEntity> list = GoodsDao.getInstance().select();
            report.writeGoods(list, "reportGoods.xlsx");
            log.info("Скачан отчёт");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @FXML
    void addGood(ActionEvent event) {
        try {
            GoodsEntity good = new GoodsEntity(Integer.parseInt(idTextField.getText()), nameTextField.getText(), Integer.parseInt(priorityTextField.getText()));
            GoodsDao.getInstance().insert(good);
            initialize();
            log.info("Добавлен товар: {}", good.toString());
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
            GoodsDao.getInstance().delete(id);
            initialize();
            log.info("Удален товар, id - {}", id);
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
            String name = nameTextField.getText();
            Integer priority = Integer.parseInt(priorityTextField.getText());
            Integer id = Integer.parseInt(idTextField.getText());
            GoodsDao.getInstance().update(name, priority, id);
            initialize();
            log.info("Обновлён товар, id - {}", id);
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
        initializeTableGoods();
    }

    private void clear() {
        idTextField.clear();
        nameTextField.clear();
        priorityTextField.clear();
    }

    private void initializeTableGoods() {
        columnId.setCellValueFactory(new PropertyValueFactory<GoodsEntity, Integer>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<GoodsEntity, String>("name"));
        columnPriority.setCellValueFactory(new PropertyValueFactory<GoodsEntity, Integer>("priority"));

        ObservableList<GoodsEntity> list = GoodsDao.getInstance().select();

        tableGoods.setItems(list);
        log.info("SELECT запрос, таблица - goods");
    }
}

