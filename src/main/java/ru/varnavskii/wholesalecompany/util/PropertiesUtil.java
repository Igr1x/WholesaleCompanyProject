package ru.varnavskii.wholesalecompany.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private PropertiesUtil() {};

    private final static Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }
}
