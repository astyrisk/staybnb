package com.staybnb.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;

public class TestConfig {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static final String BASE_URL        = findProperty("TEST_BASE_URL");
    public static final String TEST_USER_EMAIL = findProperty("TEST_USER_EMAIL");
    public static final String TEST_PASSWORD   = findProperty("TEST_PASSWORD");
    public static final String TEST_FIRST_NAME = findProperty("TEST_FIRST_NAME");
    public static final String TEST_LAST_NAME  = findProperty("TEST_LAST_NAME");

    // Environment-specific property and user IDs — override in .env to avoid recompile on env reset
    public static final String DEFAULT_PROPERTY_ID       = getProperty("TEST_DEFAULT_PROPERTY_ID", "227");
    public static final String PROPERTY_FEW_AMENITIES_ID = getProperty("TEST_PROPERTY_FEW_AMENITIES_ID", "1088");
    public static final String PROPERTY_NO_AMENITIES_ID  = getProperty("TEST_PROPERTY_NO_AMENITIES_ID", "1087");
    public static final String OTHER_USER_ID_1           = getProperty("TEST_OTHER_USER_ID_1", "2657");
    public static final String OTHER_USER_ID_2           = getProperty("TEST_OTHER_USER_ID_2", "2658");

    static {
        validate();
    }

    private static void validate() {
        List<String> missing = new ArrayList<>();
        if (BASE_URL == null)        missing.add("TEST_BASE_URL");
        if (TEST_USER_EMAIL == null) missing.add("TEST_USER_EMAIL");
        if (TEST_PASSWORD == null)   missing.add("TEST_PASSWORD");
        if (TEST_FIRST_NAME == null) missing.add("TEST_FIRST_NAME");
        if (TEST_LAST_NAME == null)  missing.add("TEST_LAST_NAME");
        if (!missing.isEmpty()) {
            throw new IllegalStateException(
                "Missing required configuration properties: " + String.join(", ", missing) +
                ". Set them via -D<key>=<value>, .env file, or environment variables."
            );
        }
    }

    /** Returns null if the key is absent or blank (all sources). */
    private static String findProperty(String key) {
        String v = System.getProperty(key);
        if (v != null && !v.isBlank()) return v;
        v = dotenv.get(key);
        if (v != null && !v.isBlank()) return v;
        v = System.getenv(key);
        if (v != null && !v.isBlank()) return v;
        return null;
    }

    private static String getProperty(String key, String defaultValue) {
        String v = findProperty(key);
        return v != null ? v : defaultValue;
    }
}
