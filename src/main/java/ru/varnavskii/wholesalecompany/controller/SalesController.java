package ru.varnavskii.wholesalecompany.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.varnavskii.wholesalecompany.Exception.TriggerException;
import ru.varnavskii.wholesalecompany.dao.SalesDao;
import ru.varnavskii.wholesalecompany.entity.SalesEntity;
import ru.varnavskii.wholesalecompany.util.ControllerUtil;
import ru.varnavskii.wholesalecompany.util.ReportExcel;
import ru.varnavskii.wholesalecompany.util.ScenesPaths;

public class SalesController {

    private final static Logger log = LoggerFactory.getLogger(SalesController.class);


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<SalesEntity, String> columnDate;

    @FXML
    private TableColumn<SalesEntity, Integer> columnGoodCount;

    @FXML
    private TableColumn<SalesEntity, Integer> columnGoodId;

    @FXML
    private TableColumn<SalesEntity, Integer> columnId;

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
    private TableView<SalesEntity> tableSales;

    @FXML
    private Button updateButton;

    @FXML
    private Button reportButon;

    @FXML
    private TextArea infoText;


    @FXML
    void downloadReport(ActionEvent event) {
        try {
            ReportExcel report = new ReportExcel();
            ObservableList<SalesEntity> list2 = SalesDao.getInstance().select();
            report.writeSales(list2, "reportSales.xlsx");
            log.info("Скачан отчёт");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @FXML
    void addSale(ActionEvent event) {
        try {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            SalesEntity sale = new SalesEntity(Integer.parseInt(idTextField.getText()), Integer.parseInt(goodCountTextField.getText()),
                    currentTime, Integer.parseInt(goodIdTextField.getText()));
            SalesDao.getInstance().insert(sale);
            initialize();
            log.info("Добавлена новая заявка");
        } catch (NumberFormatException e) {
            infoText.setText(ControllerUtil.INCORRECT_DATA);
            log.warn(ControllerUtil.INCORRECT_DATA);
        } catch (TriggerException e) {
            infoText.setText(e.getMessage());
            log.warn(e.getMessage());
        } finally {
            clear();
        }
    }

    @FXML
    void deleteSale(ActionEvent event) {
        try {
            Integer id = Integer.parseInt(idTextField.getText());
            SalesDao.getInstance().delete(id);
            initialize();
            log.info("Удалена заявка, id - {}", id);
        } catch (NumberFormatException e) {
            infoText.setText(ControllerUtil.INCORRECT_DATA);
            log.warn(ControllerUtil.INCORRECT_DATA);
        } finally {
            clear();
        }
    }

    @FXML
    void updateSale(ActionEvent event) {
        try {
            Integer id = Integer.parseInt(idTextField.getText());
            SalesDao.getInstance().update(Integer.parseInt(goodCountTextField.getText()), id);
            initialize();
            log.info("Обновлена заявка, id - {}", id);
        } catch (NumberFormatException e) {
            infoText.setText(ControllerUtil.INCORRECT_DATA);
            log.warn(ControllerUtil.INCORRECT_DATA);
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
        initializeSalesTable();
    }

    private void clear() {
        idTextField.clear();
        goodCountTextField.clear();
        goodIdTextField.clear();
    }

    private void initializeSalesTable() {
        columnId.setCellValueFactory(new PropertyValueFactory<SalesEntity, Integer>("id"));
        columnGoodCount.setCellValueFactory(new PropertyValueFactory<SalesEntity, Integer>("goodCount"));
        columnDate.setCellValueFactory(new PropertyValueFactory<SalesEntity, String>("createDate"));
        columnGoodId.setCellValueFactory(new PropertyValueFactory<SalesEntity, Integer>("goodId"));

        ObservableList<SalesEntity> list = SalesDao.getInstance().select();

        tableSales.setItems(list);
        log.info("SELECT запрос, таблица - sales");
    }
}
