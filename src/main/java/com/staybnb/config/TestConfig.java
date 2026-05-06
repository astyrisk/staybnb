package com.staybnb.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;

public class TestConfig {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static final String BASE_URL                  = findProperty("TEST_BASE_URL"                 );
    public static final String HOST_TEST_USER_EMAIL      = findProperty("TEST_USER_EMAIL"               );
    public static final String HOST_TEST_PASSWORD        = findProperty("TEST_PASSWORD"                 );
    public static final String HOST_TEST_USER_FIRST_NAME = findProperty("TEST_FIRST_NAME"               );
    public static final String HOST_TEST_USER_LAST_NAME  = findProperty("TEST_LAST_NAME"                );
    public static final String NON_HOST_TEST_USER_EMAIL  = findProperty("NON_HOST_TEST_USER_EMAIL"      );
    public static final String NON_HOST_TEST_PASSWORD    = findProperty("NON_HOST_TEST_PASSWORD"        );
    public static final String DEFAULT_PROPERTY_ID       = findProperty("TEST_DEFAULT_PROPERTY_ID"      );
    public static final String ONE_BOOKED_PROPERTY_ID    = findProperty("TEST_ONE_BOOKED_PROPERTY_ID"   );
    public static final String ZERO_BOOKED_PROPERTY_ID   = findProperty("TEST_ZERO_BOOKED_PROPERTY_ID"  );
    public static final String TO_BOOK_PROPERTY_ID       = findProperty("TEST_TO_BOOK_PROPERTY_ID"      );
    public static final String NOTIFY_BOOK_PROPERTY_ID   = findProperty("TEST_NOTIFY_BOOK_PROPERTY_ID"  );
    public static final String PROPERTY_FEW_AMENITIES_ID = findProperty("TEST_PROPERTY_FEW_AMENITIES_ID");
    public static final String PROPERTY_NO_AMENITIES_ID  = findProperty("TEST_PROPERTY_NO_AMENITIES_ID" );
    public static final String OTHER_USER_ID_1           = findProperty("TEST_OTHER_USER_ID_1"          );
    public static final String PROPERTY_SINGLE_GUEST_ID  = findProperty("TEST_PROPERTY_SINGLE_GUEST_ID" );

    static {
        validate();
    }

    private static void validate() {
        List<String> missing = new ArrayList<>();
        if (BASE_URL == null)        missing.add("TEST_BASE_URL");
        if (HOST_TEST_USER_EMAIL == null) missing.add("TEST_USER_EMAIL");
        if (HOST_TEST_PASSWORD == null)   missing.add("TEST_PASSWORD");
        if (HOST_TEST_USER_FIRST_NAME == null) missing.add("TEST_FIRST_NAME");
        if (HOST_TEST_USER_LAST_NAME == null)  missing.add("TEST_LAST_NAME");
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
}
