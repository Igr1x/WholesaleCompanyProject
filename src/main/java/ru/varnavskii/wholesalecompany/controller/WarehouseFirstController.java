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
import ru.varnavskii.wholesalecompany.dao.SalesDao;
import ru.varnavskii.wholesalecompany.dao.WarehouseFirstDao;
import ru.varnavskii.wholesalecompany.entity.SalesEntity;
import ru.varnavskii.wholesalecompany.entity.WarehouseFirstEntity;

public class WarehouseFirstController {

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
    void addGood(ActionEvent event) {
        WarehouseFirstEntity good = new WarehouseFirstEntity(Integer.parseInt(idTextField.getText()),
                                                            Integer.parseInt(goodIdTextField.getText()),
                                                            Integer.parseInt(goodCountTextField.getText()));
        WarehouseFirstDao.getInstance().insert(good);
        initialize();
    }

    @FXML
    void deleteGood(ActionEvent event) {
        WarehouseFirstDao.getInstance().delete(Integer.parseInt(idTextField.getText()));
        initialize();
    }

    @FXML
    void updateGood(ActionEvent event) {
        WarehouseFirstDao.getInstance().update(Integer.parseInt(goodCountTextField.getText()), Integer.parseInt(idTextField.getText()));
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
        columnId.setCellValueFactory(new PropertyValueFactory<WarehouseFirstEntity, Integer>("id"));
        columnGoodCount.setCellValueFactory(new PropertyValueFactory<WarehouseFirstEntity, Integer>("goodCount"));
        columnGoodId.setCellValueFactory(new PropertyValueFactory<WarehouseFirstEntity, Integer>("goodId"));

        ObservableList<WarehouseFirstEntity> list = WarehouseFirstDao.getInstance().select();
        tableWarehouse.setItems(list);
    }

}
