package com.staybnb.config;

import io.github.cdimascio.dotenv.Dotenv;

public class TestConfig {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static final String BASE_URL = getProperty("TEST_BASE_URL");
    public static final String TEST_USERNAME = getProperty("TEST_USER");
    public static final String TEST_PASSWORD = getProperty("TEST_PASSWORD");

    private static String getProperty(String key) {
        // 1. Check System Property (set via -D flag or Jenkins)
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.trim().isEmpty()) {
            return systemProperty;
        }

        // 2. Check Dotenv (.env file)
        String dotenvProperty = dotenv.get(key);
        if (dotenvProperty != null && !dotenvProperty.trim().isEmpty()) {
            return dotenvProperty;
        }

        // 3. Check Environment Variable
        String envVariable = System.getenv(key);
        if (envVariable != null && !envVariable.trim().isEmpty()) {
            return envVariable;
        }

        // 4. Required - throw exception if not found
        throw new RuntimeException(
            "Required property '" + key + "' not found in environment variables or system properties. " +
            "Please set it via -D" + key + " or environment variable."
        );
    }
}
