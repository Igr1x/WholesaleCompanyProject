package ru.varnavskii.wholesalecompany.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.varnavskii.wholesalecompany.dao.SalesDao;
import ru.varnavskii.wholesalecompany.entity.SalesEntity;
import ru.varnavskii.wholesalecompany.util.ReportExcel;

public class SalesController {

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
    void downloadReport(ActionEvent event) throws IOException {
        ReportExcel report = new ReportExcel();
        ObservableList<SalesEntity> list2 = SalesDao.getInstance().select();
        report.writeSales(list2,"reportSales.xlsx");
    }

    @FXML
    void addSale(ActionEvent event) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        SalesEntity sale = new SalesEntity(Integer.parseInt(idTextField.getText()), Integer.parseInt(goodCountTextField.getText()),
                                            currentTime, Integer.parseInt(goodIdTextField.getText()));
        SalesDao.getInstance().insert(sale);
        initialize();
    }

    @FXML
    void deleteSale(ActionEvent event) {
        SalesDao.getInstance().delete(Integer.parseInt(idTextField.getText()));
        initialize();
    }

    @FXML
    void updateSale(ActionEvent event) {
        SalesDao.getInstance().update(Integer.parseInt(goodCountTextField.getText()),Integer.parseInt(idTextField.getText()));
        initialize();
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
        columnId.setCellValueFactory(new PropertyValueFactory<SalesEntity, Integer>("id"));
        columnGoodCount.setCellValueFactory(new PropertyValueFactory<SalesEntity, Integer>("goodCount"));
        columnDate.setCellValueFactory(new PropertyValueFactory<SalesEntity, String>("createDate"));
        columnGoodId.setCellValueFactory(new PropertyValueFactory<SalesEntity, Integer>("goodId"));

        ObservableList<SalesEntity> list = SalesDao.getInstance().select();
        tableSales.setItems(list);
    }
}
