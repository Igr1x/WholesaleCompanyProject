package ru.varnavskii.wholesalecompany.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private PropertiesUtil() {};

    private final static Properties PROPERTIES = new Properties();

    private final static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    static {
        loadProperties();
    }

    public static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }
}


