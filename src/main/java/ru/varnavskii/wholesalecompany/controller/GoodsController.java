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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.varnavskii.wholesalecompany.dao.GoodsDao;
import ru.varnavskii.wholesalecompany.entity.GoodsEntity;
import ru.varnavskii.wholesalecompany.util.ExceptionHandler;
import ru.varnavskii.wholesalecompany.util.ReportExcel;

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
    void downloadReport(ActionEvent event) throws IOException {
        try {
            ReportExcel report = new ReportExcel();
            ObservableList<GoodsEntity> list = GoodsDao.getInstance().select();
            report.writeGoods(list, "reportGoods.xlsx");
            log.info("Скачан отчёт");
        } catch (Exception e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.error(errorMessage);
        }
    }

    @FXML
    void addGood(ActionEvent event) {
        try {
            GoodsEntity good = new GoodsEntity(Integer.parseInt(idTextField.getText()), nameTextField.getText(), Integer.parseInt(priorityTextField.getText()));
            GoodsDao.getInstance().insert(good);
            initialize();
            clear();
            log.info("Добавлен товар: {}", good.toString());
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
            GoodsDao.getInstance().delete(id);
            initialize();
            clear();
            log.info("Удален товар, id - {}", id);
        } catch (SQLException e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.error(errorMessage);
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
            clear();
            log.info("Обновлён товар, id - {}", id);
        } catch (SQLException e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.info(errorMessage);
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
        clear();
        columnId.setCellValueFactory(new PropertyValueFactory<GoodsEntity, Integer>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<GoodsEntity, String>("name"));
        columnPriority.setCellValueFactory(new PropertyValueFactory<GoodsEntity, Integer>("priority"));

        ObservableList<GoodsEntity> list = null;
        try {
            list = GoodsDao.getInstance().select();
            tableGoods.setItems(list);
            log.info("SELECT запрос, таблица - goods");
        } catch (SQLException e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            infoText.setText(errorMessage);
            log.error(errorMessage);
        }
    }

    private void clear() {
        idTextField.clear();
        nameTextField.clear();
        priorityTextField.clear();
    }
}

