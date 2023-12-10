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
import ru.varnavskii.wholesalecompany.dao.UsersDao;
import ru.varnavskii.wholesalecompany.entity.UsersEntity;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private TextArea info;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    void addUser(ActionEvent event) {
        String loginStr = login.getText().trim();
        String passwordStr = password.getText().trim();
        if (loginStr.equals("") || passwordStr.equals("")) {
            info.setText("Введите корректные данные для регистрации");
        } else {
            String hashPassword = DigestUtils.md5Hex(passwordStr);
            if (UsersDao.getInstance().selectLogin(loginStr)) {
                info.setText("Пользователь с таким логином уже существует");
            } else {
                UsersEntity user = new UsersEntity(loginStr, hashPassword);
                UsersDao.getInstance().insert(user);
                info.setText("Пользователь добавлен!");
            }
        }
    }

    @FXML
    void goLogin(ActionEvent event) {
        loginButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
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
}

