package ru.varnavskii.wholesalecompany.Exception;

import java.sql.SQLException;

public class TriggerException extends SQLException {
    public TriggerException(String msg) {
        super(msg);
    }
}
