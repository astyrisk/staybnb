package com.staybnb.assertions;

public final class ErrorMessages {
    private ErrorMessages() {}

    // Assertion messages (test diagnostics)
    public static final String JWT_TOKEN_SHOULD_EXIST_AFTER_LOGIN =
            "JWT token should exist after login.";
    public static final String JWT_TOKEN_SHOULD_BE_REMOVED_AFTER_LOGOUT =
            "JWT token should be removed from localStorage after logout.";
    public static final String AUTH_TOKEN_SHOULD_BE_PRESENT_IN_LOCAL_STORAGE =
            "Auth token should be present in localStorage.";
    public static final String EXPECTED_INVALID_CREDENTIALS_OR_UNAUTHORIZED =
            "Expected an 'invalid credentials' or 'unauthorized' error message.";
    public static final String SHOULD_BE_REDIRECTED_TO_HOMEPAGE_AFTER_LOGOUT =
            "Should be redirected to homepage after logout.";
    public static final String SHOULD_NAVIGATE_TO_EDIT_PROFILE_PAGE =
            "Should navigate to the edit profile page.";
    public static final String API_RESPONSE_SHOULD_NOT_BE_NULL =
            "API response should not be null.";

    // UI validation / inline error texts
    public static final String FIRST_NAME_REQUIRED =
            "First name is required";
    public static final String LAST_NAME_REQUIRED =
            "Last name is required";

    // --- Edit Profile test messages ---
    public static final String FULL_NAME_SHOULD_BE_UPDATED =
            "Full name should be updated.";
    public static final String PHONE_SHOULD_BE_UPDATED =
            "Phone should be updated.";
    public static final String BIO_SHOULD_BE_UPDATED =
            "Bio should be updated.";
    public static final String VALIDATION_ERROR_MESSAGE_SHOULD_MATCH =
            "Validation error message should match.";
    public static final String CHANGES_SHOULD_NOT_BE_SAVED_AFTER_CANCELLATION =
            "Changes should not be saved after cancellation.";
    public static final String EDIT_PROFILE_SHOULD_SHOW_401_WHEN_LOGGED_OUT =
            "401 error should be displayed when accessing edit profile while not logged in.";

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

    // --- Property Categories (Story 12) messages ---
    public static final String CATEGORIES_BAR_SHOULD_BE_HORIZONTALLY_SCROLLABLE =
            "Category bar should be horizontally scrollable.";
    public static final String CATEGORIES_BAR_SHOULD_INCLUDE_EXPECTED_CATEGORY_CHIPS =
            "Category bar should include the expected category chips (All, Apartment, House, Villa, etc.).";
    public static final String SELECTING_CATEGORY_SHOULD_MARK_CHIP_ACTIVE =
            "Selecting a category should mark the clicked chip as active.";
    public static final String SELECTING_CATEGORY_SHOULD_FILTER_PROPERTIES =
            "Selecting a category should filter the property grid to only matching properties.";
    public static final String CATEGORIES_API_SHOULD_RETURN_LIST_WITH_ID_NAME_ICON =
            "GET /api/t/{slug}/categories should return a non-empty list where each item includes id, name, and icon fields.";
    public static final String PROPERTY_DETAILS_SHOULD_SHOW_TYPE_AND_CATEGORY =
            "Property detail page should display the category alongside the property type (e.g., 'Entire place · Apartment').";
    public static final String CREATE_PROPERTY_CATEGORY_DROPDOWN_SHOULD_BE_POPULATED =
            "Create property Step 1 should allow selecting a category from a dropdown populated with categories.";

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
    public static final String AVATAR_URL_SHOULD_MATCH =
            "Avatar URL should match.";
    public static final String FULL_NAME_SHOULD_MATCH =
            "Full name should match.";
    public static final String FIRST_NAME_SHOULD_MATCH =
            "First name should match.";
    public static final String LAST_NAME_SHOULD_MATCH =
            "Last name should match.";
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
    public static final String NAVBAR_HOST_DASHBOARD_SHOULD_NOT_BE_VISIBLE_FOR_NON_HOST_USER =
            "My Properties should not be visible for a non-host authenticated user.";
    public static final String NAVBAR_HOST_DASHBOARD_SHOULD_BE_VISIBLE_FOR_HOST_USER =
            "My Properties should be visible for a host user.";
    public static final String NAVBAR_BECOME_HOST_SHOULD_NOT_BE_VISIBLE_FOR_HOST_USER =
            "Become a Host should not be visible for a host user.";
    public static final String SHOULD_NAVIGATE_TO_HOSTING_PAGE =
            "Should navigate to hosting page (/hosting).";
    public static final String BECOME_HOST_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN =
            "Become-host API should return 401 when not logged in.";
    public static final String BECOME_HOST_API_SHOULD_REFLECT_IS_HOST_TRUE =
            "Become-host API should return user object reflecting isHost: true.";
    public static final String BECOME_HOST_BUTTON_SHOULD_BE_VISIBLE_ON_PROFILE_PAGE =
            "Become a Host button should be visible on profile page for non-host.";

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
    public static final String RESPONSE_SHOULD_CONTAIN_EXPECTED_FIELD =
            "API response should contain the expected field.";
    public static final String RESPONSE_SHOULD_NOT_CONTAIN_EXCLUDED_FIELD =
            "API response should not contain the excluded field.";

    // --- Navbar test messages ---
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
    public static final String NAVBAR_AUTHENTICATED_CHECK_CONDITION_SHOULD_BE_MET =
            "The authenticated navbar check condition should be met.";
    public static final String NAVBAR_VISITOR_CHECK_CONDITION_SHOULD_BE_MET =
            "The visitor navbar check condition should be met.";
    public static final String NAVBAR_WISHLISTS_LINK_SHOULD_BE_IN_DROPDOWN =
            "Wishlists link should be in dropdown";
    public static final String NAVBAR_SHOULD_NAVIGATE_TO_WISHLISTS_PAGE =
            "Should navigate to wishlists page";

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
    public static final String CREATE_PROPERTY_STEP2_REQUIRED_FIELD_SHOULD_SHOW_VALIDATION =
            "Step 2 should show inline validation when a required field is missing.";
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
    public static final String CREATE_PROPERTY_STEP5_REORDER_SHOULD_UPDATE_SORT_ORDER =
            "Reordering images should update the preview order (sort_order) accordingly.";
    public static final String CREATE_PROPERTY_STEP5_REORDER_SHOULD_UPDATE_PRIMARY_COVER =
            "When the first image changes after reorder, the new first image should become the primary/cover image.";
    public static final String CREATE_PROPERTY_STEP5_DELETE_SHOULD_REMOVE_IMAGE =
            "Deleting an image should remove it from the uploaded images list.";
    public static final String CREATE_PROPERTY_STEP5_DELETE_PRIMARY_SHOULD_PROMOTE_NEXT =
            "Deleting the primary image should promote the next image as the new primary/cover.";
    public static final String CREATE_PROPERTY_STEP5_SHOULD_WARN_WHEN_DELETING_LAST_IMAGE =
            "When only one image exists, deleting it during create flow should warn that at least 1 image is required.";
    public static final String CREATE_PROPERTY_STEP6_PRICING_SECTION_SHOULD_LOAD =
            "Step 6 pricing section should load.";
    public static final String CREATE_PROPERTY_STEP6_PRICE_INPUT_SHOULD_USE_USD_LABEL =
            "Step 6 price input should use USD label.";
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
    public static final String CREATE_PROPERTY_API_SHOULD_RETURN_400_FOR_MISSING_REQUIRED_FIELDS =
            "Create property API should return 400 when required fields are missing.";
    public static final String CREATE_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_HOST =
            "Create property API should return 403 for an authenticated non-host user.";

    // --- Edit Property test messages ---
    public static final String EDIT_PROPERTY_PAGE_CONTAINER_AND_CONTROLS_SHOULD_BE_VISIBLE =
            "Edit property page container, header and save button should be visible.";
    public static final String EDIT_PROPERTY_PAGE_CORE_FIELDS_SHOULD_BE_PREPOPULATED =
            "Edit property page core fields (title, description, location, price, type, category) should be pre-populated.";
    public static final String EDIT_PROPERTY_PAGE_SHOULD_DISPLAY_ALL_SINGLE_PAGE_SECTIONS =
            "Edit property page should display all expected sections on a single page.";
    public static final String EDIT_PROPERTY_PAGE_SHOULD_DISPLAY_ALL_SECTIONS =
            "Edit property page should display all main sections on a single page.";
    public static final String EDIT_PROPERTY_PAGE_SHOULD_NOT_SHOW_WIZARD_NAVIGATION =
            "Edit property page should not show create-wizard progress or next/back navigation.";
    public static final String EDIT_PROPERTY_SAVE_SHOULD_RETURN_200_FOR_OWNER =
            "Saving a valid update as property owner should return 200.";
    public static final String EDIT_PROPERTY_REQUIRED_FIELD_SHOULD_SHOW_INLINE_VALIDATION =
            "Clearing a required field should show inline validation and prevent successful save.";
    public static final String EDIT_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER =
            "Edit property API should return 403 for authenticated non-owner.";
    public static final String EDIT_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY =
            "Edit property API should return 404 for non-existent property id.";
    public static final String EDIT_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT =
            "Edit property API should return 401 when not logged in.";
    public static final String EDIT_PROPERTY_PAGE_SHOULD_SHOW_DELETE_BUTTON =
            "Edit property page should show Delete Property button at the bottom area.";

    // --- Delete Property test messages ---
    public static final String DELETE_PROPERTY_EDIT_PAGE_SHOULD_SHOW_CONFIRMATION_MODAL =
            "Clicking Delete Property on edit page should show a confirmation modal with the expected warning message.";
    public static final String DELETE_PROPERTY_DASHBOARD_SHOULD_SHOW_CONFIRMATION_MODAL =
            "Clicking Delete on host dashboard should show a confirmation modal with the expected warning message.";
    public static final String DELETE_PROPERTY_CANCEL_SHOULD_KEEP_PROPERTY_UNCHANGED =
            "Canceling deletion should keep the property unchanged and still listed on host dashboard.";
    public static final String DELETE_PROPERTY_CONFIRM_SHOULD_REMOVE_PROPERTY_FROM_DASHBOARD =
            "Confirming deletion should remove the property from host dashboard listing.";
    public static final String DELETE_PROPERTY_API_SHOULD_RETURN_200_FOR_OWNER =
            "Delete property API should return 200 for the property owner.";
    public static final String DELETE_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER =
            "Delete property API should return 403 for authenticated non-owner.";
    public static final String DELETE_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY =
            "Delete property API should return 404 for non-existent property id.";
    public static final String DELETE_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT =
            "Delete property API should return 401 when not logged in.";

    // --- Publish / Unpublish Property test messages ---
    public static final String PUBLISH_PROPERTY_DASHBOARD_TOGGLE_SHOULD_SET_STATUS_TO_PUBLISHED =
            "Publish toggle from host dashboard should set property status badge to Published.";
    public static final String UNPUBLISHED_PROPERTY_SHOULD_NOT_APPEAR_ON_LISTING_PAGE =
            "Unpublished property should not appear in public property listing results.";
    public static final String UNPUBLISHED_PROPERTY_SHOULD_STAY_ON_DASHBOARD_AS_DRAFT =
            "Unpublished property should still appear on host dashboard with Draft status.";
    public static final String PUBLISHED_PROPERTY_SHOULD_APPEAR_ON_LISTING_PAGE =
            "Published property should appear in public property listing results.";
    public static final String PUBLISH_PROPERTY_API_200_SHOULD_SET_IS_PUBLISHED_TRUE =
            "Publishing a property as owner should return success and response should show isPublished true.";
    public static final String PUBLISH_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER =
            "Publish property API should return 403 for authenticated non-owner.";
    public static final String PUBLISH_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY =
            "Publish property API should return 404 for non-existent property id.";
    public static final String PUBLISH_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT =
            "Publish property API should return 401 when not logged in.";

    // --- Image Upload (API + UI) messages ---
    public static final String IMAGE_UPLOAD_API_SHOULD_RETURN_200_AND_INCLUDE_URL =
            "Uploading a supported image format should return 200 and the response should include a url field pointing to /uploads/t/{slug}/...";
    public static final String IMAGE_UPLOAD_API_SHOULD_RETURN_400_FOR_UNSUPPORTED_FILE_TYPE =
            "Uploading an unsupported image type should return 400.";
    public static final String IMAGE_UPLOAD_API_SHOULD_RETURN_400_WHEN_NO_FILE_ATTACHED =
            "Image upload API should return 400 when no file is attached.";
    public static final String IMAGE_UPLOAD_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN =
            "Image upload API should return 401 when not logged in.";
    public static final String IMAGE_UPLOAD_STEP5_SHOULD_ENFORCE_MAX_10_IMAGES =
            "Create property Step 5 should prevent adding more than 10 images.";

    // --- Property Amenity Display (Story 14) messages ---
    public static final String AMENITY_ITEMS_SHOULD_DISPLAY_ICON_AND_LABEL =
            "Each amenity item on the property detail page should display both an icon and a text label.";
    public static final String SHOW_ALL_AMENITIES_BUTTON_SHOULD_APPEAR_FOR_MORE_THAN_EIGHT_AMENITIES =
            "A 'Show all N amenities' button should appear when the property has more than 8 amenities.";
    public static final String ALL_AMENITIES_SHOULD_BE_VISIBLE_WITHOUT_MODAL_FOR_FEW_AMENITIES =
            "All amenities should be directly visible without a 'Show all' button when the property has 8 or fewer amenities.";
    public static final String AMENITIES_SECTION_SHOULD_BE_HIDDEN_FOR_PROPERTY_WITH_NO_AMENITIES =
            "The amenities section should be hidden when the property has no amenities.";

    // --- Filter by Price Range (Story F3.2-S1) messages ---
    public static final String PRICE_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME =
            "Setting a price range in the filter sidebar should update results in real-time.";
    public static final String PRICE_FILTER_SHOULD_SHOW_RESULTS_IN_RANGE =
            "Navigating with a price range filter should return at least one matching property.";
    public static final String PRICE_FILTER_ALL_RESULTS_SHOULD_BE_IN_RANGE =
            "Every displayed property must have a price per night within the applied min/max range.";
    public static final String PRICE_FILTER_SHOULD_SHOW_EMPTY_STATE_FOR_NO_MATCH =
            "A price range that matches no properties should display the empty state.";
    public static final String PRICE_FILTER_SHOULD_SHOW_ZERO_COUNT_FOR_NO_MATCH =
            "A price range that matches no properties should show zero results count.";

    // --- Search Properties by Dates and Guests (Story F3.1-S2) messages ---
    public static final String SEARCH_CHECK_IN_MIN_SHOULD_BE_TODAY =
            "Check-in date input should have a minimum value of today's date.";
    public static final String SEARCH_CHECK_OUT_MIN_SHOULD_BE_AFTER_CHECK_IN =
            "Check-out date input minimum should be set to the day after the selected check-in date.";
    public static final String SEARCH_SHOULD_NAVIGATE_TO_GUEST_FILTERED_URL =
            "Searching with a guest count should navigate to /properties?guests={n}.";
    public static final String SEARCH_WITH_GUEST_FILTER_SHOULD_SHOW_RESULTS =
            "Navigating with a guest filter should show properties that accommodate the requested number of guests.";
    public static final String SEARCH_WITH_DATE_FILTER_SHOULD_SHOW_RESULTS =
            "Navigating with a date filter should show properties available for the specified date range.";
    public static final String SEARCH_WITH_NO_FILTERS_SHOULD_RETURN_ALL_PROPERTIES =
            "Submitting the search form with no filters applied should return all published properties.";

    // --- Search Properties by Location (Story F3.1-S1) messages ---
    public static final String SEARCH_FORM_SHOULD_EXPAND_ON_COMPACT_BAR_CLICK =
            "Clicking the compact search bar should expand the search form with Location, Check-in, Check-out, Guests, and Search fields.";
    public static final String SEARCH_SHOULD_NAVIGATE_TO_CITY_FILTERED_URL =
            "Searching for a city should navigate to /properties?city={value}.";
    public static final String SEARCH_SHOULD_SHOW_RESULTS_FOR_KNOWN_CITY =
            "Search results should show at least one property for a city with matching properties.";
    public static final String SEARCH_SHOULD_SHOW_EMPTY_STATE_FOR_UNKNOWN_CITY =
            "Search results should display the empty state when no properties match the city.";
    public static final String SEARCH_SHOULD_SHOW_ZERO_COUNT_FOR_UNKNOWN_CITY =
            "Properties count should be zero when no properties match the city.";
    public static final String SEARCH_COMPACT_BAR_SHOULD_BE_VISIBLE_ON_MOBILE =
            "Compact search bar should be visible in the navbar on mobile viewport.";

    // --- Property Type and Category Filter (Story 4 — F3.2-S2) messages ---
    public static final String TYPE_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME =
            "Selecting a property type radio should update results in real-time to show matching properties.";
    public static final String TYPE_FILTER_SHOULD_SHOW_RESULTS =
            "Navigating with propertyType filter should return at least one matching property.";
    public static final String CATEGORY_FILTER_SHOULD_SHOW_RESULTS =
            "Selecting a category from the dropdown should update results to show at least one matching property.";
    public static final String CATEGORY_FILTER_SHOULD_SHOW_RESULTS_VIA_URL =
            "Navigating with categoryId filter should return at least one matching property.";
    public static final String COMBINED_FILTER_SHOULD_CONTAIN_CATEGORY_PARAM =
            "URL should contain 'categoryId=' when category filter is active alongside a property type filter.";
    public static final String COMBINED_FILTER_SHOULD_CONTAIN_TYPE_PARAM =
            "URL should contain 'propertyType=' when property type filter is active alongside a category filter.";
    public static final String CLEAR_FILTERS_SHOULD_RESTORE_ALL_PROPERTIES =
            "Clearing all filters should restore the full listing and show more properties than the filtered view.";

    // --- Filter by Amenities, Bedrooms, and Bathrooms (Story F3.2-S3) messages ---
    public static final String AMENITY_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME =
            "Checking an amenity checkbox should change the properties count without a page reload.";
    public static final String AMENITY_FILTER_SHOULD_SHOW_RESULTS =
            "Navigating with an amenities filter should return at least one matching property.";
    public static final String BEDROOM_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME =
            "Setting a minimum bedrooms value should change the properties count without a page reload.";
    public static final String BEDROOM_FILTER_SHOULD_SHOW_RESULTS =
            "Navigating with a bedrooms filter should return at least one matching property.";
    public static final String BATHROOM_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME =
            "Setting a minimum bathrooms value should change the properties count without a page reload.";
    public static final String BATHROOM_FILTER_SHOULD_SHOW_RESULTS =
            "Navigating with a bathrooms filter should return at least one matching property.";
    public static final String COMBINED_AMENITY_BEDROOM_BATHROOM_PRICE_FILTER_URL_SHOULD_CONTAIN_ALL_PARAMS =
            "URL should contain all active filter params (amenities, bedrooms, bathrooms, minPrice, maxPrice) when all filters are applied.";
    public static final String MOBILE_FILTER_BUTTON_SHOULD_BE_VISIBLE =
            "A 'Filters' button should be visible on mobile viewport.";
    public static final String MOBILE_FILTER_BUTTON_SHOULD_OPEN_MODAL =
            "Tapping the mobile Filters button should open the slide-out filter modal.";

    // --- Sort Search Results (Story F3.3-S1) messages ---
    public static final String SORT_DROPDOWN_SHOULD_HAVE_FOUR_OPTIONS =
            "Sort dropdown should display exactly four sort options.";
    public static final String SORT_DROPDOWN_SHOULD_CONTAIN_EXPECTED_OPTIONS =
            "Sort dropdown should contain the expected option label.";
    public static final String SORT_BY_NEWEST_SHOULD_RESET_URL =
            "Selecting 'Newest' after another sort option should remove the sort param from the URL (Newest is the default).";
    public static final String SORT_BY_PRICE_ASC_RESULTS_SHOULD_BE_IN_ASCENDING_ORDER =
            "Sort by 'Price: Low to High' should order all visible properties by price per night ascending.";
    public static final String SORT_BY_PRICE_DESC_RESULTS_SHOULD_BE_IN_DESCENDING_ORDER =
            "Sort by 'Price: High to Low' should order all visible properties by price per night descending.";
    public static final String SORT_BY_RATING_DESC_FIRST_RESULT_SHOULD_HAVE_HIGHEST_RATING =
            "Sort by 'Top Rated' should place the highest-rated property first.";
    public static final String SORT_AND_FILTER_URL_SHOULD_CONTAIN_SORT_PARAM =
            "URL should contain 'sort=' when a sort option is selected alongside an active filter.";
    public static final String SORT_AND_FILTER_URL_SHOULD_CONTAIN_FILTER_PARAM =
            "URL should contain the active filter param when a sort option is applied on top of a filter.";

    // --- Paginate Search Results (Story F3.4-S1) messages ---
    public static final String PAGINATION_FIRST_PAGE_SHOULD_SHOW_AT_MOST_PAGE_SIZE_CARDS =
            "First page of results should display at most 20 property cards.";
    public static final String PAGINATION_CLICKING_NEXT_SHOULD_LOAD_NEXT_PAGE =
            "Clicking 'Next' should navigate to the next page of results.";
    public static final String PAGINATION_CLICKING_PREVIOUS_SHOULD_LOAD_PREVIOUS_PAGE =
            "Clicking 'Previous' on page 2 should navigate back to page 1.";
    public static final String PAGINATION_INFO_SHOULD_MATCH_SHOWING_FORMAT =
            "Pagination info should display text matching 'Showing X–Y of Z results'.";
    public static final String PAGINATION_URL_SHOULD_REFLECT_CURRENT_PAGE =
            "Navigating to page 2 should reflect 'page=2' in the URL.";
    public static final String PAGINATION_PREVIOUS_BUTTON_SHOULD_BE_DISABLED_ON_FIRST_PAGE =
            "The 'Previous' pagination button should be disabled when on the first page.";
    public static final String PAGINATION_NEXT_BUTTON_SHOULD_BE_DISABLED_ON_LAST_PAGE =
            "The 'Next' pagination button should be disabled when on the last page.";
    public static final String PAGINATION_SHOULD_RESET_TO_PAGE_1_ON_FILTER_CHANGE =
            "Changing sort or filters while on page 2+ should reset pagination back to page 1.";

    // --- Add Property to Wishlist (Story F3.5-S1) messages ---
    public static final String WISHLIST_HEART_SHOULD_BECOME_FILLED_ON_CARD =
            "After clicking the outline heart on a property card, the heart icon should become filled.";
    public static final String WISHLIST_HEART_SHOULD_BECOME_FILLED_ON_DETAIL =
            "After clicking the outline heart on the property detail page, the heart icon should become filled.";
    public static final String WISHLIST_UNAUTHENTICATED_CLICK_SHOULD_REDIRECT_TO_LOGIN =
            "Clicking the heart icon when not authenticated should redirect to the login page.";
    public static final String WISHLIST_ALREADY_IN_WISHLIST_HEART_SHOULD_REMAIN_FILLED =
            "A property already in the wishlist should display a filled heart on the detail page.";
    public static final String WISHLIST_ADD_NON_EXISTENT_PROPERTY_SHOULD_RETURN_404 =
            "Adding a non-existent property to the wishlist should return HTTP 404.";

    // --- View Wishlist Page (Story F3.5-S3) messages ---
    public static final String WISHLIST_PAGE_SHOULD_DISPLAY_PROPERTY_CARDS =
            "Wishlist page should display property cards for an authenticated user with favorited properties.";
    public static final String WISHLIST_PAGE_CARDS_SHOULD_SHOW_FILLED_HEARTS =
            "All property cards on the wishlist page should show filled heart icons.";
    public static final String WISHLIST_PAGE_EMPTY_STATE_SHOULD_BE_VISIBLE =
            "Wishlist page should show an empty state when the user has no favorited properties.";
    public static final String WISHLIST_PAGE_EMPTY_STATE_TEXT_SHOULD_MATCH =
            "Wishlist page empty state should display the expected message text.";
    public static final String WISHLIST_PAGE_UNAUTHENTICATED_SHOULD_SHOW_LOGIN_PROMPT =
            "Navigating to the wishlist page while unauthenticated should display a login prompt.";
    public static final String WISHLIST_PAGE_UNAUTHENTICATED_LOGIN_PROMPT_TEXT_SHOULD_MATCH =
            "Unauthenticated wishlist page login prompt should contain the expected message text.";

    // --- Remove Property from Wishlist (Story F3.5-S2) messages ---
    public static final String WISHLIST_HEART_SHOULD_BECOME_OUTLINE_ON_CARD =
            "After clicking the filled heart on a property card, the heart icon should become an outline.";
    public static final String WISHLIST_HEART_SHOULD_BECOME_OUTLINE_ON_DETAIL =
            "After clicking the filled heart on the property detail page, the heart icon should become an outline.";
    public static final String WISHLIST_CARD_SHOULD_DISAPPEAR_AFTER_REMOVAL_ON_WISHLIST_PAGE =
            "Removing a property from the wishlists page should cause its card to disappear from the page.";
    public static final String WISHLIST_REMOVE_NON_WISHLISTED_PROPERTY_SHOULD_RETURN_404 =
            "Removing a property that is not in the wishlist should return HTTP 404.";

    // --- Auth UI validation (Story 1 & 2) ---
    public static final String REGISTER_PAGE_SHOULD_NAVIGATE_TO_LOGIN =
            "Clicking the 'Log in' link on the register page should navigate to the login page.";
    public static final String EMAIL_REQUIRED =
            "Email is required";
    public static final String PASSWORD_REQUIRED =
            "Password is required";
    public static final String PASSWORD_MINIMUM_LENGTH =
            "Password must be at least 8 characters";
    public static final String PASSWORDS_MUST_MATCH =
            "Passwords do not match";
    public static final String EMAIL_ALREADY_REGISTERED =
            "Email already registered";
    public static final String EXPECTED_INLINE_VALIDATION_ERROR =
            "Expected inline validation error.";
    public static final String LOGIN_PAGE_SHOULD_NAVIGATE_TO_REGISTER =
            "Clicking the 'Register' link on the login page should navigate to the register page.";

    // --- Register API (Story 1) ---
    public static final String REGISTER_API_SHOULD_RETURN_201_FOR_VALID_PAYLOAD =
            "Register API should return 201 for a valid payload.";
    public static final String REGISTER_API_SHOULD_RETURN_409_FOR_EXISTING_EMAIL =
            "Register API should return 409 when the email already exists in the tenant.";
    public static final String REGISTER_API_SHOULD_RETURN_400_FOR_MISSING_FIELDS =
            "Register API should return 400 when required fields are missing.";
    public static final String REGISTER_API_RESPONSE_SHOULD_CONTAIN_TOKEN =
            "Register API response should contain a token string.";

    // --- Login API (Story 2) ---
    public static final String LOGIN_API_SHOULD_RETURN_200_FOR_VALID_CREDENTIALS =
            "Login API should return 200 for valid credentials.";
    public static final String LOGIN_API_SHOULD_RETURN_401_FOR_INVALID_CREDENTIALS =
            "Login API should return 401 for invalid credentials.";
    public static final String LOGIN_API_SHOULD_RETURN_400_FOR_MISSING_FIELDS =
            "Login API should return 400 when required fields are missing.";
    public static final String LOGIN_API_RESPONSE_SHOULD_CONTAIN_TOKEN =
            "Login API response should contain a token string.";

    // --- Logout API (Story 3) ---
    public static final String LOGOUT_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN =
            "Logout API should return 401 when the caller is not authenticated.";
    public static final String LOGOUT_API_SHOULD_RETURN_200_WHEN_LOGGED_IN =
            "Logout API should return 200 when the user is authenticated.";
    public static final String LOGOUT_API_UNAUTHENTICATED_ENDPOINT_RETURNS_401 =
            "Accessing an authenticated endpoint without a token after logout should return 401.";

    // --- Property Amenities (Story 13) messages ---
    public static final String AMENITIES_API_SHOULD_RETURN_LIST_WITH_ID_NAME_ICON =
            "GET /api/t/{slug}/amenities should return a non-empty list where each item includes id, name, and icon fields.";
    public static final String EDIT_PROPERTY_AMENITIES_SECTION_SHOULD_SHOW_CHECKBOX_GRID =
            "Edit property amenities section should display a non-empty checkbox grid.";
    public static final String EDIT_PROPERTY_SHOULD_PRE_CHECK_PREVIOUSLY_SELECTED_AMENITIES =
            "Edit property amenities section should pre-check amenities previously associated with the property.";
    public static final String DESELECTING_AMENITY_AND_SAVING_SHOULD_REMOVE_IT_FROM_PROPERTY =
            "After deselecting an amenity and saving, reloading the edit page should show that amenity is no longer checked.";
}
