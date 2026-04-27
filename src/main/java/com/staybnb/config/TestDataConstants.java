package com.staybnb.config;

import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;

public final class TestDataConstants {
    private TestDataConstants() {}

    // ── Property IDs ──────────────────────────────────────────────────────────
    public static final String DEFAULT_PROPERTY_ID            = TestConfig.DEFAULT_PROPERTY_ID;
    public static final String NON_EXISTENT_ID                = "999999";
    public static final String NON_EXISTENT_PROPERTY_ID       = "99999999";
    // Property with 1–8 amenities (Story 14 AC4). Must be published and accessible.
    public static final String PROPERTY_WITH_FEW_AMENITIES_ID = TestConfig.PROPERTY_FEW_AMENITIES_ID;
    // Property with 0 amenities (Story 14 AC5).
    public static final String PROPERTY_WITH_NO_AMENITIES_ID  = TestConfig.PROPERTY_NO_AMENITIES_ID;

    // ── User IDs ──────────────────────────────────────────────────────────────
    public static final String OTHER_USER_ID_1 = TestConfig.OTHER_USER_ID_1;

    // ── Other Profile (user 3005) ─────────────────────────────────────────────
    public static final class OtherProfile {
        public static final String USER_ID     = TestConfig.OTHER_USER_ID_1;
        public static final String NAME        = "Jon E.";
        public static final String META        = "Guest · Member since April 2026";
        public static final String BIO         = "Adventure seeker and foodie.";
        public static final String AVATAR_SRC  = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFLA5gZ352Mtmj5OMlzW9FEIVV5n3dDbSgeg&s";
    }

    // ── Own Profile ───────────────────────────────────────────────────────────
    public static final class OwnProfile {
        public static final String FIRST_NAME = "Heiko";
        public static final String LAST_NAME  = "Neko";
        public static final String PHONE      = "+201556638077";
        public static final String BIO        = "Default bio";
        public static final String AVATAR_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCHo3CkaH0oRY3MvrEN0xgn-x_Lsn3Lm3lVQ&s";
    }

    // ── Edit Profile ──────────────────────────────────────────────────────────
    public static final class EditProfile {
        public static final String NEW_FIRST_NAME = "HeikoUpdated";
        public static final String NEW_LAST_NAME  = "NekoUpdated";
        public static final String NEW_PHONE      = "+201556638077";
        public static final String NEW_BIO        = "Updated bio for testing persistence.";
        public static final String NEW_AVATAR_URL = "https://www.shutterstock.com/image-photo/beautiful-golden-retriever-cute-puppy-600nw-2526542701.jpg";

        public static final String API_FIRST_NAME = "HeikoAPI";
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
        public static final String ENTIRE_PLACE    = "ENTIRE_PLACE";
        public static final String KNOWN_TYPE      = ENTIRE_PLACE;
        public static final String KNOWN_CATEGORY_ID = "90";
    }

    // ── Price Filter ──────────────────────────────────────────────────────────
    public static final class PriceFilter {
        public static final int KNOWN_MIN_PRICE    = 50;
        public static final int KNOWN_MAX_PRICE    = 150;
        public static final int NO_MATCH_MIN_PRICE = 9999;
        public static final int NO_MATCH_MAX_PRICE = 99999;
    }

    // ── Search ────────────────────────────────────────────────────────────────
    public static final class Search {
        public static final String KNOWN_CITY   = "Zermatt";
        public static final String UNKNOWN_CITY = "cairo";
        public static final int    GUEST_COUNT  = 2;

        public static String futureCheckInDate() {
            return java.time.LocalDate.now().plusDays(30)
                    .format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
        }

        public static String futureCheckOutDate() {
            return java.time.LocalDate.now().plusDays(37)
                    .format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    // ── Amenities, Bedrooms & Bathrooms Filter ────────────────────────────────
    public static final class AmenitiesFilter {
        public static final String KNOWN_AMENITY_ID    = "173";
        public static final String KNOWN_AMENITY_NAME  = "Beach Access";
        public static final int    KNOWN_MIN_BEDROOMS  = 2;
        public static final int    KNOWN_MIN_BATHROOMS = 1;
    }

    // ── Pagination ────────────────────────────────────────────────────────────
    public static final class Pagination {
        public static final int PAGE_SIZE = 20;
        public static final int LAST_PAGE = 3;
    }

    // ── Sort Search Results ───────────────────────────────────────────────────
    public static final class SortFilter {
        public static final String SORT_NEWEST      = "newest";
        public static final String SORT_PRICE_ASC   = "price_asc";
        public static final String SORT_PRICE_DESC  = "price_desc";
        public static final String SORT_RATING_DESC = "rating_desc";
    }
}
