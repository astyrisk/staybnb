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

    // ── Wait ──────────────────────────────────────────────────────────────────
    public static final int MEDIUM_WAIT = ConfigProperties.getInt("medium.wait.seconds", 10);

    // ── Viewports ─────────────────────────────────────────────────────────────
    public static final int MOBILE_WIDTH          = ConfigProperties.getInt("mobile.width", 375);
    public static final int WIDE_DESKTOP_WIDTH    = 1920;
    public static final int WIDE_DESKTOP_HEIGHT   = 1080;
    public static final int MEDIUM_DESKTOP_WIDTH  = 1025;
    public static final int MEDIUM_DESKTOP_HEIGHT = 1080;
    public static final int TABLET_TEST_WIDTH     = 770;
    public static final int TABLET_TEST_HEIGHT    = 1024;

    // ── Property IDs ──────────────────────────────────────────────────────────
    public static final String DEFAULT_PROPERTY_ID             = "227";
    public static final String NON_EXISTENT_ID                 = "999999";
    public static final String NON_EXISTENT_PROPERTY_ID        = "99999999";
    // Property with 1–8 amenities (Story 14 AC4). Must be published and accessible.
    public static final String PROPERTY_WITH_FEW_AMENITIES_ID  = "1088";
    // Property with 0 amenities (Story 14 AC5).
    public static final String PROPERTY_WITH_NO_AMENITIES_ID   = "1087";

    // ── User IDs ──────────────────────────────────────────────────────────────
    public static final String USER_ID_101 = "2657";
    public static final String USER_ID_102 = "2658";

    // ── Own Profile ───────────────────────────────────────────────────────────
    public static final class OwnProfile {
        public static final String FULL_NAME = "HekoUpdated NekoUpdated";
        public static final String PHONE     = "+201556638077";
    }

    // ── Edit Profile ──────────────────────────────────────────────────────────
    public static final class EditProfile {
        public static final String NEW_FIRST_NAME = "HekoUpdated";
        public static final String NEW_LAST_NAME  = "NekoUpdated";
        public static final String NEW_PHONE      = "+201556638077";
        public static final String NEW_BIO        = "Updated bio for testing persistence.";
        public static final String NEW_AVATAR_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCHo3CkaH0oRY3MvrEN0xgn-x_Lsn3Lm3lVQ&s";

        public static final String API_FIRST_NAME = "HekoAPI";
        public static final String API_LAST_NAME  = "NekoAPI";
        public static final String API_PHONE      = "+1234567890";
        public static final String API_BIO        = "API Bio";
    }

    // ── Edit Property ─────────────────────────────────────────────────────────
    public static final class EditProperty {
        public static final String EDITABLE_PROPERTY_ID = PROPERTY_WITH_FEW_AMENITIES_ID;
        public static final String UPDATED_TITLE        = "Automation Listing Updated";
    }

    // ── Delete Property ───────────────────────────────────────────────────────
    public static final class DeleteProperty {
        public static final String EDITABLE_PROPERTY_ID  = PROPERTY_WITH_FEW_AMENITIES_ID;
        public static final String CONFIRMATION_MESSAGE  = "Are you sure you want to delete";
    }

    // ── Publish Property ──────────────────────────────────────────────────────
    public static final class PublishProperty {
        public static final String OWNED_PROPERTY_ID = PROPERTY_WITH_FEW_AMENITIES_ID;
    }

    // ── Property Type & Category Filter ──────────────────────────────────────
    public static final class TypeCategoryFilter {
        // Property type radio values (match the HTML input value attributes)
        public static final String ENTIRE_PLACE = "ENTIRE_PLACE";

        // A type guaranteed to yield results in the test environment
        public static final String KNOWN_TYPE = ENTIRE_PLACE;

        // Bungalow is known to have exactly 1 published property in the test environment
        public static final String KNOWN_CATEGORY_ID   = "90";
    }

    // ── Price Filter ──────────────────────────────────────────────────────────
    public static final class PriceFilter {
        // Range that includes $120 test properties (all automation properties are priced at $120/night)
        public static final int KNOWN_MIN_PRICE = 50;
        public static final int KNOWN_MAX_PRICE = 150;
        // Range guaranteed to match no properties in the test environment
        public static final int NO_MATCH_MIN_PRICE = 9999;
        public static final int NO_MATCH_MAX_PRICE = 99999;
    }

    // ── Search ────────────────────────────────────────────────────────────────
    public static final class Search {
        // A city known to have at least one property in the test environment
        public static final String KNOWN_CITY   = "Zermatt";
        // A city confirmed to return zero results in the test environment
        public static final String UNKNOWN_CITY = "cairo";

        // Guest count used for guest-filter search tests
        public static final int GUEST_COUNT = 2;

        // Future check-in date (30 days from today), formatted as yyyy-MM-dd
        public static String futureCheckInDate() {
            return java.time.LocalDate.now().plusDays(30)
                    .format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
        }

        // Future check-out date (37 days from today), formatted as yyyy-MM-dd
        public static String futureCheckOutDate() {
            return java.time.LocalDate.now().plusDays(37)
                    .format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

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
