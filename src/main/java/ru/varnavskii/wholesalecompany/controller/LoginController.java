package ru.varnavskii.wholesalecompany.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.varnavskii.wholesalecompany.dao.UsersDao;
import ru.varnavskii.wholesalecompany.entity.UsersEntity;
import ru.varnavskii.wholesalecompany.util.ExceptionHandler;

public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

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
        String password = passwordTextField.getText();
        String passwordHash = DigestUtils.md5Hex(password);
        String login = loginTextField.getText();
        UsersEntity user = new UsersEntity(login, passwordHash);

        try {
            boolean res = UsersDao.getInstance().select(user.getLogin(), user.getPassword());
            if (res) {
                userLogin = login;
                loginButton.getScene().getWindow().hide();
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
                log.info("Выполнен вход, login - {}", login);
            } else {
                info.setText("Пользователь не найден!");
                log.info("Пользователь не найден, login - {}", login);
            }
        } catch (Exception e) {
            String errorMessage = ExceptionHandler.getMessage(e);
            info.setText(errorMessage);
            log.error(errorMessage);
        }
    }

    @FXML
    void goRegister(ActionEvent event) {
        registerButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/hello-view.fxml"));
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
    }

    public static String getUser() {
        return userLogin;
    }
}
