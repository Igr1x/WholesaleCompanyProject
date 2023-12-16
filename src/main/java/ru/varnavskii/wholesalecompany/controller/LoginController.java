package ru.varnavskii.wholesalecompany.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.varnavskii.wholesalecompany.dao.UsersDao;
import ru.varnavskii.wholesalecompany.entity.UsersEntity;
import ru.varnavskii.wholesalecompany.util.ControllerUtil;
import ru.varnavskii.wholesalecompany.util.ScenesPaths;

public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final String USER_NOT_EXIST = "Пользователь не найден!";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea info;

    @FXML
    private TextField loginTextField;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button registerButton;

    private static String userLogin;

    @FXML
    void goMenu(ActionEvent event) {
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        try {
            ControllerUtil.checkUserData(login, password);
            String passwordHash = DigestUtils.md5Hex(password);
            UsersEntity user = new UsersEntity(login, passwordHash);
            boolean res = UsersDao.getInstance().select(user.getLogin(), user.getPassword());
            if (!res) {
                throw new IOException(USER_NOT_EXIST);
            }
            userLogin = login;
            ControllerUtil.openScene(loginButton, ScenesPaths.MENU_PATH);
            log.info("Выполнен вход, login - {}", login);
        } catch (IOException e) {
            log.warn(e.getMessage() + " login - {}", login);
            info.setText(e.getMessage());
        }
    }

    @FXML
    void goRegister(ActionEvent event) {
        ControllerUtil.openScene(registerButton, ScenesPaths.REGISTRATION_PATH);
    }

    @FXML
    void initialize() {
    }

    public static String getUser() {
        return userLogin;
    }
}
