package ru.varnavskii.wholesalecompany.util;

public enum ScenesPaths {
    LOGIN_PATH("/login.fxml"),
    REGISTRATION_PATH("/registration-view.fxml"),
    MENU_PATH("/menu-view.fxml"),
    GOODS_PATH("/goods-view.fxml"),
    SALES_PATH("/sales-view.fxml"),
    WAREHOUSE_FIRST_PATH("/warehouse1-view.fxml"),
    WAREHOUSE_SECOND_PATH("/warehouse2-view.fxml"),
    STATS_PATH("/stats-view.fxml");

    ScenesPaths(String path) {
        pathScene = path;
    }

    private final String pathScene;

    public String getPath() {
        return pathScene;
    }
}
