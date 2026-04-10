package com.staybnb.data;

public final class Constants {
    private Constants() {}

    // Invalid Credentials
    public static final String INVALID_EMAIL = "wronguser@gmail.com";
    public static final String INVALID_PASSWORD = "WrongPassword123!";

    // Test Data
    public static final String DEFAULT_PASSWORD = "password123";

    // Common IDs
//    public static final String DEFAULT_PROPERTY_ID = "202";
    public static final String DEFAULT_PROPERTY_ID = "1084";
    public static final String NON_EXISTENT_ID = "999999";
    // Property with 1–8 amenities (for Story 14 AC4). Must be published and accessible.
//    public static final String PROPERTY_WITH_FEW_AMENITIES_ID = "960";
    public static final String PROPERTY_WITH_FEW_AMENITIES_ID = "1088";
    // Property with 0 amenities (for Story 14 AC5).
//    public static final String PROPERTY_WITH_NO_AMENITIES_ID = "959";
    public static final String PROPERTY_WITH_NO_AMENITIES_ID = "1087";
//    public static final String USER_ID_101 = "101";
    public static final String USER_ID_101 = "2657";
//    public static final String USER_ID_102 = "102";
    public static final String USER_ID_102 = "2658";

    // Responsive Dimensions
    public static final int DESKTOP_WIDTH = 1200;
    public static final int TABLET_WIDTH = 768;
    public static final int DEFAULT_HEIGHT = 800;

    // Test viewport dimensions (explicit sizes used in UI tests)
    public static final int WIDE_DESKTOP_WIDTH = 1920;
    public static final int WIDE_DESKTOP_HEIGHT = 1080;
    public static final int MEDIUM_DESKTOP_WIDTH = 1025;
    public static final int MEDIUM_DESKTOP_HEIGHT = 1080;
    public static final int TABLET_TEST_WIDTH = 770;
    public static final int TABLET_TEST_HEIGHT = 1024;

    // Error Messages & Codes
    public static final String PROPERTY_NOT_FOUND_CODE = "PROPERTY_NOT_FOUND";

    // Own Profile Test Data
    public static final class OwnProfile {
        public static final String FULL_NAME = "HekoAPI NekoAPI";
        public static final String PHONE = "+1234567890";
    }

    // Edit Profile Test Data
    public static final class EditProfile {
        public static final String NEW_FIRST_NAME = "HekoUpdated";
        public static final String NEW_LAST_NAME = "NekoUpdated";
        public static final String NEW_PHONE = "+201556638077";
        public static final String NEW_BIO = "Updated bio for testing persistence.";
        public static final String NEW_AVATAR_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCHo3CkaH0oRY3MvrEN0xgn-x_Lsn3Lm3lVQ&s";

        public static final String API_FIRST_NAME = "HekoAPI";
        public static final String API_LAST_NAME = "NekoAPI";
        public static final String API_PHONE = "+1234567890";
        public static final String API_BIO = "API Bio";
    }

    public static final class EditProperty {
        public static final String EDITABLE_PROPERTY_ID = "1088";
        public static final String NON_EXISTENT_PROPERTY_ID = "99999999";
        public static final String UPDATED_TITLE = "Automation Listing Updated";
    }

    public static final class DeleteProperty {
        public static final String EDITABLE_PROPERTY_ID = "1088";
        public static final String NON_EXISTENT_PROPERTY_ID = "99999999";
        public static final String CONFIRMATION_MESSAGE = "Are you sure you want to delete";
    }

    public static final class PublishProperty {
        public static final String OWNED_PROPERTY_ID = "1088";
        public static final String NON_EXISTENT_PROPERTY_ID = "99999999";
    }

    public static final class Search {
        // A city known to have at least one property in the test environment
        public static final String KNOWN_CITY = "Zermatt";
        // A city confirmed to return zero results in the test environment
        public static final String UNKNOWN_CITY = "cairo";
    }
}
