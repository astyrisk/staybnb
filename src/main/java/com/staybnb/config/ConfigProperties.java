package com.staybnb.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigProperties {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = ConfigProperties.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties from classpath.", e);
        }
    }

    private ConfigProperties() {}

    public static String get(String key, String defaultValue) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) return sysProp;
        return props.getProperty(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
