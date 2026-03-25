package com.staybnb.utils;

public class TestData {
    
    // Own Profile Test Data
    public static class OwnProfile {
        public static final String FULL_NAME = "HekoAPI NekoAPI";
        public static final String PHONE = "+1234567890";
        public static final String BIO = "Default bio"; // Bio might vary, but this is the current one in tests
    }

    // Edit Profile Test Data
    public static class EditProfile {
        public static final String NEW_FIRST_NAME = "HekoUpdated";
        public static final String NEW_LAST_NAME = "NekoUpdated";
        public static final String NEW_PHONE = "+201556638077";
        public static final String NEW_BIO = "Updated bio for testing persistence.";
        public static final String NEW_AVATAR_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCHo3CkaH0oRY3MvrEN0xgn-x_Lsn3Lm3lVQ&s";
        
        public static final String API_FIRST_NAME = "HekoAPI";
        public static final String API_LAST_NAME = "NekoAPI";
        public static final String API_PHONE = "+1234567890";
        public static final String API_BIO = "API Bio";
        
        public static final String ERROR_FIRST_NAME_REQUIRED = "First name is required";
        public static final String ERROR_LAST_NAME_REQUIRED = "Last name is required";
    }

    // Other Profile Test Data
    public static class OtherProfile {
        public static final String USER_101_NAME = "Bob J.";
        public static final String USER_101_BIO = "Adventure seeker and foodie.";
        public static final String USER_101_INITIAL = "B";
    }

    // Property Test Data
    public static class Property {
        public static final String PROPERTY_202_TITLE = "Ski Chalet in Zermatt";
        public static final String PROPERTY_202_LOCATION = "Zermatt, Switzerland";
    }
}
