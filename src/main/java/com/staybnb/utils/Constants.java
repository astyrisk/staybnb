package com.staybnb.utils;

public class Constants {
    // Base URLs
    public static final String SLUG = "automation-adel";
    public static final String BASE_URL = "https://qa-playground.nixdev.co/t/" + SLUG;
    public static final String DOMAIN = "https://qa-playground.nixdev.co";

    // Page URLs
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String REGISTER_URL = BASE_URL + "/register";
    public static final String HOME_URL = BASE_URL;
    public static final String PROFILE_URL = BASE_URL + "/profile";
    public static final String EDIT_PROFILE_URL = BASE_URL + "/profile/edit";
    public static final String PROPERTY_DETAILS_BASE_URL = BASE_URL + "/properties/";
    public static final String OTHER_PROFILE_BASE_URL = BASE_URL + "/users/";

    // Valid Credentials
    public static final String VALID_EMAIL = "tezo@gmail.com";
    public static final String VALID_PASSWORD = "tezo0105";

    // Invalid Credentials
    public static final String INVALID_EMAIL = "wronguser@gmail.com";
    public static final String INVALID_PASSWORD = "WrongPassword123!";
    
    // Test Data
    public static final String TEST_USER_FIRST_NAME = "Test";
    public static final String TEST_USER_LAST_NAME = "User";
    public static final String DEFAULT_PASSWORD = "password123";

    // Common IDs
    public static final String DEFAULT_PROPERTY_ID = "202";
    public static final String NON_EXISTENT_ID = "999999";
    public static final String USER_ID_101 = "101";
    public static final String USER_ID_102 = "102";

    // Wait Durations
    public static final int SHORT_WAIT = 5;
    public static final int MEDIUM_WAIT = 10;
    public static final int LONG_WAIT = 20;

    // Responsive Dimensions
    public static final int DESKTOP_WIDTH = 1200;
    public static final int TABLET_WIDTH = 768;
    public static final int MOBILE_WIDTH = 375;
    public static final int DEFAULT_HEIGHT = 800;

    // Error Messages & Codes
    public static final String PROPERTY_NOT_FOUND_CODE = "PROPERTY_NOT_FOUND";
}
