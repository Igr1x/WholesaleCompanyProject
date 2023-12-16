package ru.varnavskii.wholesalecompany.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public final class ControllerUtil {

    private ControllerUtil() {};

    public static final String INCORRECT_DATA = "Введены некорректные данные!";

    public static void openScene(Button button, ScenesPaths scene) {
        button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerUtil.class.getResource(scene.getPath()));
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

    public static void checkUserData(String... data) throws IOException {
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("")) {
                throw new IOException(INCORRECT_DATA);
            }
        }
    }
}
