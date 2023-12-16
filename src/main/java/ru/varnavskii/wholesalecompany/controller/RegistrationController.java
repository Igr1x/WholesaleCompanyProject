package ru.varnavskii.wholesalecompany.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
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

public class RegistrationController {

    private final static Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private final String EXISTING_USER = "Пользователь с таким логином уже существует!";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextArea info;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    void addUser(ActionEvent event) {
        try {
            String login = loginTextField.getText().trim();
            String password = passwordField.getText().trim();
            ControllerUtil.checkUserData(login, password);
            checkDatabase(login);
            addNewUser(login, DigestUtils.md5Hex(password));
        } catch (IOException e) {
            info.setText(e.getMessage());
            log.warn(e.getMessage());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @FXML
    void goLogin(ActionEvent event) {
        ControllerUtil.openScene(loginButton, ScenesPaths.LOGIN_PATH);
    }

    @FXML
    void initialize() {
    }

    private void checkDatabase(String loginStr) throws SQLException, IOException {
        if (UsersDao.getInstance().selectLogin(loginStr)) {
            throw new IOException(EXISTING_USER);
        }
    }

    private void addNewUser(String login, String password) throws SQLException {
        UsersEntity user = new UsersEntity(login, password);
        UsersDao.getInstance().insert(user);
        info.setText("Пользователь добавлен!");
        log.info("Добавлен новый пользователь - {}", user.toString());
    }
}

