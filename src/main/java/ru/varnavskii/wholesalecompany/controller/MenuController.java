package ru.varnavskii.wholesalecompany.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.varnavskii.wholesalecompany.util.ControllerUtil;
import ru.varnavskii.wholesalecompany.util.ScenesPaths;

public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button goodsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button salesButton;

    @FXML
    private Button statsButton;

    @FXML
    private TextArea userInfo;

    @FXML
    private Button warehouseFirstButton;

    @FXML
    private Button warehouseSecondButton;

    @FXML
    void goGoods(ActionEvent event) {
        ControllerUtil.openScene(goodsButton, ScenesPaths.GOODS_PATH);
    }

    @FXML
    void goLogin(ActionEvent event) {
        ControllerUtil.openScene(logoutButton, ScenesPaths.LOGIN_PATH);
        log.info("Выход с аккаунта, login - {}", LoginController.getUser());
    }

    @FXML
    void goSales(ActionEvent event) {
        ControllerUtil.openScene(salesButton, ScenesPaths.SALES_PATH);
    }

    @FXML
    void goStats(ActionEvent event) {
        ControllerUtil.openScene(statsButton, ScenesPaths.STATS_PATH);
    }

    @FXML
    void goWarehouseFirst(ActionEvent event) {
        ControllerUtil.openScene(warehouseFirstButton, ScenesPaths.WAREHOUSE_FIRST_PATH);
    }

    @FXML
    void goWarehouseSecond(ActionEvent event) {
        ControllerUtil.openScene(warehouseSecondButton, ScenesPaths.WAREHOUSE_SECOND_PATH);
    }

    @FXML
    void initialize() {
        userInfo.setText("Пользователь: " + LoginController.getUser());
    }
}
