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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.varnavskii.wholesalecompany.dao.GoodsDao;
import ru.varnavskii.wholesalecompany.entity.GoodsEntity;
import ru.varnavskii.wholesalecompany.util.ReportExcel;

public class GoodsController {

    @FXML
    private ResourceBundle resources;

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
        ReportExcel report = new ReportExcel();
        ObservableList<GoodsEntity> list = GoodsDao.getInstance().select();
        report.writeGoods(list, "reportGoods.xlsx");
    }

    @FXML
    void addGood(ActionEvent event) {
        GoodsEntity good = new GoodsEntity(Integer.parseInt(idTextField.getText()), nameTextField.getText(), Integer.parseInt(priorityTextField.getText()));
        GoodsDao.getInstance().insert(good);
        initialize();
    }

    @FXML
    void deleteGood(ActionEvent event) {
        GoodsDao.getInstance().delete(Integer.parseInt(idTextField.getText()));
        initialize();
    }

    @FXML
    void updateGood(ActionEvent event) {
        GoodsDao.getInstance().update(nameTextField.getText(), Integer.parseInt(priorityTextField.getText()),Integer.parseInt(idTextField.getText()));
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
        columnId.setCellValueFactory(new PropertyValueFactory<GoodsEntity, Integer>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<GoodsEntity, String>("name"));
        columnPriority.setCellValueFactory(new PropertyValueFactory<GoodsEntity, Integer>("priority"));

        ObservableList<GoodsEntity> list = GoodsDao.getInstance().select();
        tableGoods.setItems(list);
    }
}

