package com.staybnb.config;

public final class AppConstants {
    private AppConstants() {}

    public static final String BASE_URL = TestConfig.BASE_URL;
    public static final String SLUG = resolveSlug(BASE_URL);

    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String REGISTER_URL = BASE_URL + "/register";
    public static final String HOME_URL = BASE_URL;
    public static final String PROFILE_URL = BASE_URL + "/profile";
    public static final String EDIT_PROFILE_URL = BASE_URL + "/profile/edit";
    public static final String HOSTING_URL = BASE_URL + "/hosting";
    public static final String HOSTING_CREATE_URL = BASE_URL + "/hosting/create";
    public static final String PROPERTY_LISTING_URL = BASE_URL + "/properties";
    public static final String PROPERTY_DETAILS_BASE_URL = BASE_URL + "/properties/";
    public static final String OTHER_PROFILE_BASE_URL = BASE_URL + "/users/";

    public static final int SHORT_WAIT = 5;
    public static final int MEDIUM_WAIT = 10;
    public static final int LONG_WAIT = 20;

    public static final int MOBILE_WIDTH = 375;

    private static String resolveSlug(String baseUrl) {
        if (baseUrl == null) {
            throw new IllegalStateException("TEST_BASE_URL must not be null.");
        }
        String marker = "/t/";
        int markerIndex = baseUrl.indexOf(marker);
        if (markerIndex < 0 || markerIndex + marker.length() >= baseUrl.length()) {
            throw new IllegalStateException(
                    "TEST_BASE_URL must contain '/t/<slug>' so scripts can resolve tenant slug. Found: " + baseUrl
            );
        }
        return baseUrl.substring(markerIndex + marker.length());
    }
}
