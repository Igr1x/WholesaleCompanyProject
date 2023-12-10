module ru.varnavskii.wholesalecompany {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires lombok;
    requires fastexcel;
    requires org.apache.commons.codec;

    opens ru.varnavskii.wholesalecompany to javafx.fxml;
    exports ru.varnavskii.wholesalecompany;
    exports  ru.varnavskii.wholesalecompany.controller;
    opens ru.varnavskii.wholesalecompany.controller to javafx.fxml;
    opens ru.varnavskii.wholesalecompany.entity to javafx.fxml;
    exports ru.varnavskii.wholesalecompany.entity;
    exports  ru.varnavskii.wholesalecompany.dao;
    opens ru.varnavskii.wholesalecompany.dao to javafx.fxml;
}