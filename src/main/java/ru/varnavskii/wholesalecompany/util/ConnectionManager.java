package ru.varnavskii.wholesalecompany.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.List;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool.size";

    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;

    private final static Logger log = LoggerFactory.getLogger(ConnectionManager.class);

    private ConnectionManager() {};

    static {
        initConnectionPool();
    }

    private static Connection open() {
        try {
            return DriverManager.getConnection(PropertiesUtil.getProperties(URL_KEY),
                    PropertiesUtil.getProperties(USERNAME_KEY),
                    PropertiesUtil.getProperties(PASSWORD_KEY));
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void initConnectionPool() {
        var poolSize = PropertiesUtil.getProperties(POOL_SIZE_KEY);
        int size = poolSize == null ? 10 : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Connection connection = open();
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.
                    getClassLoader(), new Class[] {Connection.class},
                    (proxy, method, args) -> method.getName().equals("close")
                    ? pool.add((Connection) proxy)
                    : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void closePool() {
        for (Connection sourceConnection : sourceConnections) {
            try {
                sourceConnection.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
