package com.staybnb.config;

public final class AppConstants {
    private AppConstants() {}

    // ── URLs ──────────────────────────────────────────────────────────────────
    public static final String BASE_URL                  = TestConfig.BASE_URL;
    public static final String SLUG                      = resolveSlug();
    public static final String LOGIN_URL                 = BASE_URL + "/login";
    public static final String REGISTER_URL              = BASE_URL + "/register";
    public static final String HOME_URL                  = BASE_URL;
    public static final String PROFILE_URL               = BASE_URL + "/profile";
    public static final String EDIT_PROFILE_URL          = BASE_URL + "/profile/edit";
    public static final String HOSTING_URL               = BASE_URL + "/hosting";
    public static final String HOSTING_CREATE_URL        = BASE_URL + "/hosting/create";
    public static final String PROPERTY_LISTING_URL      = BASE_URL + "/properties";
    public static final String PROPERTY_DETAILS_BASE_URL = BASE_URL + "/properties/";
    public static final String OTHER_PROFILE_BASE_URL    = BASE_URL + "/users/";
    public static final String WISHLIST_URL              = BASE_URL + "/wishlists";

    // ─────────────────────────────────────────────────────────────────────────

    private static String resolveSlug() {
        if (AppConstants.BASE_URL == null) {
            throw new IllegalStateException("TEST_BASE_URL must not be null.");
        }
        String marker = "/t/";
        int markerIndex = AppConstants.BASE_URL.indexOf(marker);
        if (markerIndex < 0 || markerIndex + marker.length() >= AppConstants.BASE_URL.length()) {
            throw new IllegalStateException(
                    "TEST_BASE_URL must contain '/t/<slug>' so scripts can resolve tenant slug. Found: " + AppConstants.BASE_URL
            );
        }
        return AppConstants.BASE_URL.substring(markerIndex + marker.length());
    }
}
