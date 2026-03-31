package com.staybnb.assertions;

public final class ErrorMessages {
    private ErrorMessages() {}

    // Assertion messages (test diagnostics)
    public static final String JWT_TOKEN_NOT_RETRIEVED =
            "JWT token was not retrieved after successful login.";
    public static final String JWT_TOKEN_SHOULD_EXIST_AFTER_LOGIN =
            "JWT token should exist after login.";
    public static final String JWT_TOKEN_SHOULD_BE_REMOVED_AFTER_LOGOUT =
            "JWT token should be removed from localStorage after logout.";
    public static final String AUTH_TOKEN_SHOULD_BE_PRESENT_IN_LOCAL_STORAGE =
            "Auth token should be present in localStorage.";
    public static final String EXPECTED_INVALID_CREDENTIALS_OR_UNAUTHORIZED =
            "Expected an 'invalid credentials' or 'unauthorized' error message.";
    public static final String EXPECTED_EMAIL_ALREADY_EXISTS =
            "Expected 'email already exists' error message.";
    public static final String SHOULD_BE_REDIRECTED_TO_HOMEPAGE_AFTER_LOGOUT =
            "Should be redirected to homepage after logout.";
    public static final String SHOULD_NAVIGATE_TO_EDIT_PROFILE_PAGE =
            "Should navigate to the edit profile page.";
    public static final String API_RESPONSE_SHOULD_NOT_BE_NULL =
            "API response should not be null.";

    // UI validation / inline error texts
    public static final String EMAIL_AND_PASSWORD_REQUIRED =
            "Email and password are required";
    public static final String REQUIRED =
            "required";
    public static final String FIRST_NAME_REQUIRED =
            "First name is required";
    public static final String LAST_NAME_REQUIRED =
            "Last name is required";

    // --- Home test messages ---
    public static final String HOME_HERO_SECTION_SHOULD_BE_VISIBLE =
            "Hero section should be visible.";
    public static final String HOME_HERO_HEADLINE_TEXT_SHOULD_MATCH =
            "Hero headline text should match.";
    public static final String HOME_HERO_SECTION_SHOULD_HAVE_BACKGROUND_IMAGE =
            "Hero section should have a background image.";
    public static final String HOME_CATEGORY_BAR_SHOULD_BE_VISIBLE =
            "Horizontal, scrollable category bar should be visible.";
    public static final String HOME_CATEGORY_BAR_SHOULD_CONTAIN_ICONS =
            "Category bar should contain icons.";
    public static final String HOME_GRID_SHOULD_DISPLAY_8_TO_12_FEATURED_CARDS =
            "Grid should display 8-12 featured property cards.";
    public static final String HOME_PROPERTY_CARDS_SHOULD_BE_PRESENT =
            "Property cards should be present.";
    public static final String HOME_EACH_PROPERTY_CARD_SHOULD_DISPLAY_ALL_DETAILS =
            "Each property card should display all details: image, title, location, price.";
    public static final String HOME_GRID_SHOULD_HAVE_4_COLUMNS_ON_DESKTOP =
            "Grid should have 4 columns on desktop.";
    public static final String HOME_GRID_SHOULD_HAVE_3_COLUMNS_ON_DESKTOP =
            "Grid should have 3 columns on desktop.";
    public static final String HOME_GRID_SHOULD_HAVE_2_COLUMNS_ON_TABLET =
            "Grid should have 2 columns on tablet.";

    // --- Property Listing test messages ---
    public static final String PROPERTY_LISTING_SHOULD_DISPLAY_PROPERTY_CARDS =
            "Property listing page should display property cards.";
    public static final String PROPERTY_CARD_SHOULD_HAVE_AN_IMAGE =
            "Property card should have an image.";
    public static final String PROPERTY_CARD_SHOULD_HAVE_A_TITLE =
            "Property card should have a title.";
    public static final String LOCATION_SHOULD_BE_CITY_AND_COUNTRY_SEPARATED_BY_COMMA =
            "Location should display city and country separated by comma.";
    public static final String PRICE_SHOULD_CONTAIN_PER_NIGHT =
            "Price should contain '/ night'.";
    public static final String SHOULD_NAVIGATE_TO_PROPERTY_DETAIL_PAGE =
            "Should navigate to the property detail page.";
    public static final String LISTING_GRID_SHOULD_HAVE_4_COLUMNS_ON_LARGE_DESKTOP =
            "Grid should have 4 columns on large desktop.";
    public static final String LISTING_GRID_SHOULD_HAVE_3_COLUMNS_ON_MEDIUM_DESKTOP =
            "Grid should have 3 columns on medium desktop.";
    public static final String LISTING_GRID_SHOULD_HAVE_2_COLUMNS_ON_TABLET =
            "Grid should have 2 columns on tablet.";
    public static final String PROPERTY_LIST_CONTROLS_SHOULD_BE_EMPTY =
            "Property list controls should be empty.";
    public static final String THERE_SHOULD_BE_NO_SEARCH_OR_FILTERS =
            "There should be no search or filters.";

    // --- Property Details test messages ---
    public static final String PROPERTY_TITLE_SHOULD_MATCH =
            "Property title should match.";
    public static final String PROPERTY_LOCATION_SHOULD_MATCH =
            "Property location should match.";
    public static final String SHOULD_DISPLAY_NUMBER_OF_GUESTS =
            "Should display number of guests.";
    public static final String SHOULD_DISPLAY_NUMBER_OF_BEDROOMS =
            "Should display number of bedrooms.";
    public static final String SHOULD_DISPLAY_NUMBER_OF_BEDS =
            "Should display number of beds.";
    public static final String SHOULD_DISPLAY_NUMBER_OF_BATHROOMS =
            "Should display number of bathrooms.";
    public static final String DESCRIPTION_SHOULD_NOT_BE_EMPTY =
            "Description should not be empty.";
    public static final String HOST_AVATAR_SHOULD_BE_DISPLAYED =
            "Host avatar should be displayed.";
    public static final String HOST_NAME_SHOULD_INCLUDE_HOSTED_BY =
            "Host name should include 'Hosted by'.";
    public static final String HOST_NAME_SHOULD_MATCH_JOHN_D =
            "Host name should match 'John D.'.";
    public static final String HOST_MEMBER_SINCE_SHOULD_BE_DISPLAYED =
            "Host member since date should be displayed.";
    public static final String HOST_MEMBER_SINCE_SHOULD_MATCH_MARCH_2026 =
            "Host member since date should match 'March 2026'.";
    public static final String THERE_SHOULD_BE_9_AMENITIES =
            "There should be 9 amenities.";
    public static final String WIFI_SHOULD_BE_LISTED =
            "WiFi should be listed.";
    public static final String SKI_ACCESS_SHOULD_BE_LISTED =
            "Ski Access should be listed.";
    public static final String PRICE_SHOULD_BE_A_POSITIVE_NUMBER =
            "Price should be a positive number.";
    public static final String BOOKING_WIDGET_SHOULD_BE_ABSENT =
            "Booking widget should be absent.";
    public static final String REVIEWS_SECTION_SHOULD_BE_ABSENT =
            "Reviews section should be absent.";
    public static final String THERE_SHOULD_BE_AT_LEAST_ONE_IMAGE =
            "There should be at least one image.";
    public static final String FIRST_IMAGE_SHOULD_BE_LIVING_ROOM =
            "First image should be 'Living Room'.";

    // --- Own Profile test messages ---
    public static final String AVATAR_SHOULD_BE_DISPLAYED =
            "Avatar should be displayed.";
    public static final String FULL_NAME_SHOULD_MATCH =
            "Full name should match.";
    public static final String PROFILE_META_SHOULD_CONTAIN_MEMBER_SINCE =
            "Profile meta should contain 'Member since'.";
    public static final String BIO_SHOULD_NOT_BE_EMPTY =
            "Bio should not be empty.";
    public static final String PHONE_NUMBER_SHOULD_MATCH =
            "Phone number should match.";
    public static final String EDIT_PROFILE_BUTTON_SHOULD_BE_VISIBLE =
            "Edit Profile button should be visible.";
    public static final String RESPONSE_SHOULD_CONTAIN_ID =
            "Response should contain 'id'.";
    public static final String RESPONSE_SHOULD_CONTAIN_EMAIL =
            "Response should contain 'email'.";
    public static final String RESPONSE_SHOULD_CONTAIN_FIRST_NAME =
            "Response should contain 'firstName'.";
    public static final String RESPONSE_SHOULD_CONTAIN_LAST_NAME =
            "Response should contain 'lastName'.";
    public static final String RESPONSE_SHOULD_CONTAIN_PHONE =
            "Response should contain 'phone'.";
    public static final String RESPONSE_SHOULD_CONTAIN_BIO =
            "Response should contain 'bio'.";
    public static final String RESPONSE_SHOULD_CONTAIN_AVATAR_URL =
            "Response should contain 'avatarUrl'.";
    public static final String RESPONSE_SHOULD_CONTAIN_IS_HOST =
            "Response should contain 'isHost'.";
    public static final String RESPONSE_SHOULD_CONTAIN_CREATED_AT =
            "Response should contain 'createdAt'.";
    public static final String API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN =
            "API should return 401 when not logged in.";

    // --- Become Host (hosting enablement) messages ---
    public static final String NAVBAR_BECOME_HOST_SHOULD_BE_VISIBLE_FOR_GUEST_USER =
            "Become a Host should be visible for a non-host authenticated user.";
    public static final String NAVBAR_MY_PROPERTIES_SHOULD_NOT_BE_VISIBLE_FOR_NON_HOST_USER =
            "My Properties should not be visible for a non-host authenticated user.";
    public static final String NAVBAR_MY_PROPERTIES_SHOULD_BE_VISIBLE_FOR_HOST_USER =
            "My Properties should be visible for a host user.";
    public static final String NAVBAR_BECOME_HOST_SHOULD_NOT_BE_VISIBLE_FOR_HOST_USER =
            "Become a Host should not be visible for a host user.";
    public static final String SHOULD_NAVIGATE_TO_HOSTING_PAGE =
            "Should navigate to hosting page (/hosting).";
    public static final String BECOME_HOST_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN =
            "Become-host API should return 401 when not logged in.";
    public static final String BECOME_HOST_API_SHOULD_REFLECT_IS_HOST_TRUE =
            "Become-host API should return user object reflecting isHost: true.";

    // --- Other Profile test messages ---
    public static final String AVATAR_SHOULD_CONTAIN_USERS_FIRST_INITIAL =
            "Avatar should contain user's first initial.";
    public static final String PROFILE_NAME_SHOULD_SHOW_FIRST_NAME_AND_LAST_INITIAL =
            "Profile name should show first name and last initial.";
    public static final String META_SHOULD_CONTAIN_USER_ROLE =
            "Meta should contain user role.";
    public static final String META_SHOULD_CONTAIN_MEMBER_SINCE =
            "Meta should contain 'Member since'.";
    public static final String BIO_SHOULD_MATCH =
            "Bio should match.";
    public static final String PHONE_NUMBER_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE =
            "Phone number should not be visible on other's profile.";
    public static final String EMAIL_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE =
            "Email should not be visible on other's profile.";
    public static final String PROFILE_NAME_SHOULD_ONLY_SHOW_FIRST_NAME_AND_LAST_INITIAL =
            "Profile name should only show first name and last initial.";
    public static final String PAGE_SHOULD_INDICATE_404_FOR_NON_EXISTENT_USER =
            "Page should indicate a 404 error for non-existent user.";
    public static final String RESPONSE_SHOULD_NOT_CONTAIN_EMAIL =
            "Response should NOT contain 'email'.";
    public static final String RESPONSE_SHOULD_NOT_CONTAIN_PHONE =
            "Response should NOT contain 'phone'.";

    // --- Navbar test messages ---
    public static final String NAVBAR_LOGO_SHOULD_BE_DISPLAYED =
            "Logo should be displayed";
    public static final String NAVBAR_USER_AVATAR_SHOULD_BE_DISPLAYED_FOR_AUTHENTICATED_USER =
            "User avatar should be displayed for logged-in user";
    public static final String NAVBAR_LOGIN_LINK_SHOULD_NOT_BE_DISPLAYED_FOR_AUTHENTICATED_USER =
            "Login link should not be displayed for logged-in user";
    public static final String NAVBAR_PROFILE_LINK_SHOULD_BE_IN_DROPDOWN =
            "Profile link should be in dropdown";
    public static final String NAVBAR_LOGOUT_BUTTON_SHOULD_BE_IN_DROPDOWN =
            "Logout button should be in dropdown";
    public static final String NAVBAR_SHOULD_NAVIGATE_TO_PROFILE_PAGE =
            "Should navigate to profile page";
    public static final String NAVBAR_SHOULD_REDIRECT_TO_HOME_AFTER_LOGOUT =
            "Should redirect to homepage after logout";
    public static final String NAVBAR_SHOULD_SHOW_LOGIN_LINK_AFTER_LOGOUT =
            "Should show login link after logout";
    public static final String NAVBAR_HAMBURGER_MENU_SHOULD_BE_DISPLAYED_ON_MOBILE =
            "Hamburger menu should be displayed on mobile";
    public static final String NAVBAR_USER_AVATAR_SHOULD_BE_VISIBLE_ON_MOBILE_MENU_BUTTON =
            "User avatar should still be visible on mobile as part of the menu button";
    public static final String NAVBAR_LOGIN_LINK_SHOULD_BE_DISPLAYED_FOR_VISITOR =
            "Login link should be displayed for visitor";
    public static final String NAVBAR_REGISTER_LINK_SHOULD_BE_DISPLAYED_FOR_VISITOR =
            "Register link should be displayed for visitor";
    public static final String NAVBAR_USER_AVATAR_SHOULD_NOT_BE_DISPLAYED_FOR_VISITOR =
            "User avatar should not be displayed for visitor";
    public static final String NAVBAR_SHOULD_NOT_DISPLAY_DROPDOWN_FOR_VISITOR =
            "Visitor should not see user dropdown";
    public static final String NAVBAR_HAMBURGER_MENU_SHOULD_NOT_BE_DISPLAYED_ON_MOBILE_VISITOR =
            "Hamburger menu should NOT be displayed for visitors on mobile";
    public static final String NAVBAR_LOGIN_LINK_SHOULD_BE_VISIBLE_ON_MOBILE_VISITOR =
            "Login link should be visible for visitors on mobile";
    public static final String NAVBAR_SHOULD_NAVIGATE_TO_LOGIN_PAGE =
            "Should navigate to login page";
    public static final String NAVBAR_SHOULD_NAVIGATE_TO_REGISTER_PAGE =
            "Should navigate to register page";

    // --- Host Dashboard test messages ---
    public static final String HOST_DASHBOARD_SHOULD_DISPLAY_PROPERTY_CARDS_FOR_HOST_WITH_PROPERTIES =
            "Host dashboard should display property cards for a host with properties.";
    public static final String HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_REQUIRED_DETAILS =
            "Property card should display thumbnail, title, location, price, status, and rating data/placeholder.";
    public static final String HOST_DASHBOARD_SUMMARY_SHOULD_INCLUDE_TOTAL_PROPERTIES_COUNT =
            "Host dashboard summary should include total properties count.";
    public static final String HOST_DASHBOARD_SHOULD_DISPLAY_CREATE_NEW_PROPERTY_BUTTON =
            "Host dashboard should display a prominent Create New Property button.";
    public static final String HOST_DASHBOARD_CREATE_NEW_PROPERTY_LINK_SHOULD_POINT_TO_CREATE_PAGE =
            "Create New Property button should link to /hosting/create.";
    public static final String HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_EDIT_DELETE_AND_PUBLISH_TOGGLE_ACTIONS =
            "Each property card should provide Edit, Delete, and Publish/Unpublish actions.";
    public static final String HOST_DASHBOARD_EMPTY_STATE_SHOULD_BE_VISIBLE_FOR_HOST_WITH_NO_PROPERTIES =
            "Host with no properties should see the expected empty state message.";
    public static final String HOST_DASHBOARD_API_RESPONSE_SHOULD_INCLUDE_BOTH_PUBLISHED_AND_UNPUBLISHED_PROPERTIES =
            "Hosting properties API response should include both published and unpublished host properties.";
    public static final String HOST_DASHBOARD_API_SHOULD_RETURN_403_FOR_NON_HOST =
            "Hosting properties API should return 403 for authenticated non-host users.";
    public static final String HOST_DASHBOARD_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN =
            "Hosting properties API should return 401 when not logged in.";

    // --- Create Property (Steps 1-3) test messages ---
    public static final String CREATE_PROPERTY_STEP1_SHOULD_DISPLAY_BASICS_FIELDS =
            "Step 1 should display property type, category, title, and description fields.";
    public static final String CREATE_PROPERTY_STEP1_SHOULD_REQUIRE_TITLE =
            "Step 1 should show inline validation when title is missing.";
    public static final String CREATE_PROPERTY_STEP1_SHOULD_REQUIRE_DESCRIPTION =
            "Step 1 should show inline validation when description is missing.";
    public static final String CREATE_PROPERTY_STEP2_SHOULD_DISPLAY_LOCATION_FIELDS =
            "Step 2 should display country, city, and optional address fields.";
    public static final String CREATE_PROPERTY_STEP2_SHOULD_REQUIRE_COUNTRY =
            "Step 2 should show inline validation when country is missing.";
    public static final String CREATE_PROPERTY_STEP2_SHOULD_REQUIRE_CITY =
            "Step 2 should show inline validation when city is missing.";
    public static final String CREATE_PROPERTY_STEP3_SHOULD_DISPLAY_DETAILS_FIELDS =
            "Step 3 should display max guests, bedrooms, beds, and bathrooms fields.";
    public static final String CREATE_PROPERTY_MAX_GUESTS_MIN_SHOULD_BE_1 =
            "Max guests input minimum should be 1.";
    public static final String CREATE_PROPERTY_BEDROOMS_MIN_SHOULD_BE_0 =
            "Bedrooms input minimum should be 0.";
    public static final String CREATE_PROPERTY_BEDS_MIN_SHOULD_BE_1 =
            "Beds input minimum should be 1.";
    public static final String CREATE_PROPERTY_BATHROOMS_MIN_SHOULD_BE_0 =
            "Bathrooms input minimum should be 0.";
    public static final String CREATE_PROPERTY_BATHROOMS_STEP_SHOULD_BE_HALF =
            "Bathrooms input should allow 0.5 increments.";
    public static final String CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_TITLE =
            "Back navigation should preserve Step 1 title.";
    public static final String CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_DESCRIPTION =
            "Back navigation should preserve Step 1 description.";
    public static final String CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_COUNTRY =
            "Back navigation should preserve Step 2 country.";
    public static final String CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_CITY =
            "Back navigation should preserve Step 2 city.";
    public static final String CREATE_PROPERTY_PROGRESS_SHOULD_SHOW_STEP_1_OF_7 =
            "Progress indicator should show Step 1 of 7 on create property page.";
    public static final String CREATE_PROPERTY_SHOULD_BLOCK_NON_HOST_WITH_403 =
            "Non-host should be blocked with a 403 error on create property page.";
    public static final String CREATE_PROPERTY_STEP4_SHOULD_DISPLAY_AMENITIES_GRID =
            "Step 4 should display amenities title and a non-empty checkbox grid.";
    public static final String CREATE_PROPERTY_STEP4_SHOULD_GROUP_AMENITIES_BY_ESSENTIALS =
            "Step 4 amenities should include an Essentials group.";
    public static final String CREATE_PROPERTY_STEP4_SHOULD_GROUP_AMENITIES_BY_FEATURES =
            "Step 4 amenities should include a Features group.";
    public static final String CREATE_PROPERTY_STEP4_SHOULD_GROUP_AMENITIES_BY_SAFETY =
            "Step 4 amenities should include a Safety group.";
    public static final String CREATE_PROPERTY_STEP4_NEXT_SHOULD_ADVANCE_TO_STEP5_WITH_SELECTED_AMENITIES =
            "After selecting amenities, Next should advance to Step 5.";
    public static final String CREATE_PROPERTY_STEP4_NEXT_SHOULD_ALLOW_EMPTY_AMENITIES =
            "Step 4 should allow proceeding to Step 5 with no amenities selected.";
    public static final String CREATE_PROPERTY_STEP4_BACK_AND_RETURN_SHOULD_PRESERVE_AMENITIES =
            "Amenity selection should be preserved when navigating back to Step 3 and returning to Step 4.";
    public static final String CREATE_PROPERTY_STEP5_SHOULD_DISPLAY_UPLOAD_AREA_WITH_DRAG_DROP_AND_BROWSE =
            "Step 5 should display an upload area supporting drag and drop or click-to-browse.";
    public static final String CREATE_PROPERTY_STEP5_UPLOAD_SHOULD_DISPLAY_PREVIEW_THUMBNAILS =
            "After upload completes, Step 5 should show preview thumbnails for uploaded images.";
    public static final String CREATE_PROPERTY_STEP5_UPLOADED_IMAGE_SHOULD_SHOW_SORT_HANDLE_AND_DELETE =
            "Each uploaded image should show both a sort handle and a delete button.";
    public static final String CREATE_PROPERTY_STEP5_FIRST_IMAGE_SHOULD_BE_MARKED_PRIMARY_OR_COVER =
            "When multiple images are uploaded, the first image should be marked as primary/cover.";
    public static final String CREATE_PROPERTY_STEP5_NEXT_SHOULD_REQUIRE_MINIMUM_ONE_IMAGE =
            "Step 5 should block Next when fewer than one image is uploaded and show 'Minimum 1 image required'.";
    public static final String CREATE_PROPERTY_STEP5_BACK_AND_RETURN_SHOULD_PRESERVE_UPLOADED_IMAGES =
            "Uploaded images should be preserved when navigating back to Step 4 and returning to Step 5.";
    public static final String CREATE_PROPERTY_STEP6_SHOULD_DISPLAY_PRICE_INPUT_IN_USD =
            "Step 6 should display a price-per-night number input in USD.";
    public static final String CREATE_PROPERTY_STEP6_SHOULD_REQUIRE_PRICE_GREATER_THAN_ZERO =
            "Step 6 should show validation when price is zero or negative.";
    public static final String CREATE_PROPERTY_STEP6_NEXT_SHOULD_ADVANCE_TO_STEP7_REVIEW =
            "With valid pricing, Next should advance to Step 7 review.";
    public static final String CREATE_PROPERTY_STEP7_SHOULD_SUMMARIZE_ALL_PREVIOUS_STEPS =
            "Step 7 should summarize property type, category, title, description, location, capacity, amenities, images, and price.";
    public static final String CREATE_PROPERTY_STEP7_BACK_AND_RETURN_SHOULD_WORK =
            "From Step 7, Back should allow editing previous steps and returning to review.";
    public static final String CREATE_PROPERTY_STEP7_SUBMIT_SHOULD_REDIRECT_TO_HOST_DASHBOARD =
            "After creating property, user should be redirected to the host dashboard.";
    public static final String CREATE_PROPERTY_STEP7_SUBMIT_SHOULD_SHOW_SUCCESS_MESSAGE =
            "After successful creation, a success message should be visible.";
    public static final String CREATE_PROPERTY_API_SHOULD_RETURN_201_FOR_VALID_HOST_PAYLOAD =
            "Create property API should return 201 for a valid host payload.";
    public static final String CREATE_PROPERTY_API_SHOULD_CREATE_DRAFT_IS_PUBLISHED_FALSE =
            "Create property API response should indicate draft creation with is_published false.";
    public static final String CREATE_PROPERTY_API_SHOULD_RETURN_400_FOR_MISSING_REQUIRED_FIELDS =
            "Create property API should return 400 when required fields are missing.";
    public static final String CREATE_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_HOST =
            "Create property API should return 403 for an authenticated non-host user.";
}
