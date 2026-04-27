package com.staybnb.locators;

import org.openqa.selenium.By;

/**
 * Centralized Selenium locators shared by page objects.
 */
public final class Locators {
    private Locators() {}

    public static final class Auth {
        private Auth() {}

        public static final By EMAIL_FIELD = By.id("email");
        public static final By PASSWORD_FIELD = By.id("password");
        public static final By PRIMARY_SUBMIT_BUTTON = By.cssSelector("button[type='submit'].btn-primary");

        public static final By INLINE_ERROR_MESSAGES = By.cssSelector(".error, .field-error, .auth-error");
    }

    public static final class Login {
        private Login() {}

        public static final By REGISTER_LINK = By.xpath("//div[@class='auth-link']/a[text()='Register']");
    }

    public static final class Register {
        private Register() {}

        public static final By FIRST_NAME_FIELD = By.id("firstName");
        public static final By LAST_NAME_FIELD = By.id("lastName");
        public static final By CONFIRM_PASSWORD_FIELD = By.id("confirmPassword");
        public static final By LOGIN_LINK = By.xpath("//div[@class='auth-link']/a[text()='Log in']");
    }

    public static final class Navbar {
        private Navbar() {}

        public static final By NAVBAR_LOGO = By.className("navbar-logo");
        public static final By USER_MENU_BUTTON = By.className("navbar-user-btn");
        public static final By USER_AVATAR = By.className("navbar-avatar");
        public static final By HAMBURGER_MENU = By.className("navbar-hamburger");

        public static final By LOGIN_LINK = By.xpath("//div[@class='navbar-auth-links']//a[text()='Log in']");
        public static final By REGISTER_LINK = By.xpath("//div[@class='navbar-auth-links']//a[text()='Sign up']");

        // Host / Hosting
        // In the UI, this can be either a <button> or <a>, but it consistently uses the same class.
        public static final By BECOME_A_HOST = By.xpath("//*[contains(@class,'navbar-host-link') and normalize-space()='Become a Host']");
        public static final By HOST_DASHBOARD = By.xpath("//div[@class='navbar-dropdown']//a[normalize-space()='Host Dashboard']");

        public static final By DROPDOWN_MENU = By.className("navbar-dropdown");
        public static final By PROFILE_LINK = By.xpath("//div[@class='navbar-dropdown']//a[contains(@href, '/profile')]");
        public static final By WISHLISTS_LINK = By.xpath("//div[@class='navbar-dropdown']//a[contains(@href, '/wishlists')]");
        public static final By LOGOUT_BUTTON = By.xpath("//div[@class='navbar-dropdown']//button[text()='Log out']");
    }

    public static final class Home {
        private Home() {}

        public static final By HERO_SECTION = By.className("home-hero");
        public static final By HERO_HEADLINE = By.cssSelector(".home-hero-content h1");
        public static final By CATEGORY_BAR = By.className("categories-bar");
        public static final By CATEGORY_CHIPS = By.cssSelector(".categories-bar .category-chip");
        public static final By ACTIVE_CATEGORY_CHIP = By.cssSelector(".categories-bar .category-chip.active");
        public static final By CATEGORY_ICONS = By.className("category-icon");
        public static final By PROPERTIES_COUNT = By.className("properties-count");
        public static final By PROPERTY_GRID = By.className("property-grid");
        public static final By PROPERTY_CARDS = By.className("property-card");

        public static final By CARD_IMAGE = By.cssSelector(".property-card-image img");
        public static final By CARD_TITLE = By.className("property-card-title");
        public static final By CARD_LOCATION = By.className("property-card-location");
        public static final By CARD_PRICE = By.className("property-card-price");
    }

    public static final class SearchBar {
        private SearchBar() {}

        public static final By COMPACT_SEARCH_BTN = By.className("search-bar-compact-btn");
        public static final By EXPANDED_FORM = By.className("search-bar-expanded");
        public static final By DESTINATION_INPUT = By.cssSelector(".search-bar-field--destination input");
        public static final By SEARCH_SUBMIT_BTN = By.cssSelector(".search-bar-actions button[type='submit']");

        public static final By CHECK_IN_INPUT = By.xpath(
                "//div[contains(@class,'search-bar-field')][.//label[text()='Check in']]//input[@type='date']"
        );
        public static final By CHECK_OUT_INPUT = By.xpath(
                "//div[contains(@class,'search-bar-field')][.//label[text()='Check out']]//input[@type='date']"
        );
        public static final By GUESTS_DISPLAY = By.cssSelector(".search-bar-guests span");
        public static final By GUESTS_INCREMENT_BTN = By.cssSelector(".search-bar-guests button:last-child");
        public static final By GUESTS_DECREMENT_BTN = By.cssSelector(".search-bar-guests button:first-child");
    }

    public static final class PropertyListing {
        private PropertyListing() {}

        public static final By PROPERTY_GRID = By.className("property-grid");
        public static final By PROPERTY_CARD = By.className("property-card");
        public static final By CARD_IMAGE = By.cssSelector(".property-card-image img");
        public static final By CARD_TITLE = By.className("property-card-title");
        public static final By CARD_LOCATION = By.className("property-card-location");
        public static final By CARD_PRICE = By.className("property-card-price");
        public static final By CARD_PRICE_AMOUNT = By.cssSelector(".property-card-price strong");
        public static final By CARD_RATING = By.className("property-card-rating");
        public static final By PROPERTY_LIST_CONTROLS = By.className("property-list-controls");
        public static final By EMPTY_STATE = By.className("empty-state");
        public static final By PROPERTIES_COUNT = By.className("properties-count");

        public static final By SEARCH_INPUT = By.cssSelector("input[type='search']");
        public static final By FILTER_BUTTONS = By.cssSelector("button.filter-btn");
        public static final By SORT_SELECT = By.cssSelector("select.sort-select");

        public static final By PAGINATION_INFO = By.className("pagination-info");
        public static final By PAGINATION_PREV_BTN = By.xpath(
                "//div[contains(@class,'pagination-controls')]//button[normalize-space()='Previous']");
        public static final By PAGINATION_NEXT_BTN = By.xpath(
                "//div[contains(@class,'pagination-controls')]//button[normalize-space()='Next']");
        public static final By PAGINATION_ACTIVE_PAGE = By.cssSelector(".pagination-controls button.btn-primary");

        public static final By CARD_FAVORITE_BTN = By.cssSelector("button.property-card-favorite");
        public static final By CARD_FAVORITE_FAVORITED_BTN = By.cssSelector("button.property-card-favorite.favorited");
    }

    public static final class FilterSidebar {
        private FilterSidebar() {}

        public static final By PRICE_MIN_INPUT = By.xpath(
                "//div[contains(@class,'filter-price-input')][.//label[text()='Min']]//input"
        );
        public static final By PRICE_MAX_INPUT = By.xpath(
                "//div[contains(@class,'filter-price-input')][.//label[text()='Max']]//input"
        );
        public static final By CLEAR_ALL_FILTERS_BTN = By.cssSelector(".active-filters .btn-link");

        public static final By CATEGORY_SELECT = By.cssSelector("select.filter-select");

        /** Returns a locator for the radio button matching the given propertyType value (e.g. "ENTIRE_PLACE"). */
        public static By propertyTypeRadio(String value) {
            return By.cssSelector("input[type='radio'][name='propertyType'][value='" + value + "']");
        }

        public static final By BEDROOMS_INPUT = By.xpath(
                "//div[contains(@class,'filter-section')][.//h4[text()='Bedrooms']]//input");
        public static final By BATHROOMS_INPUT = By.xpath(
                "//div[contains(@class,'filter-section')][.//h4[text()='Bathrooms']]//input");
        public static final By MOBILE_FILTER_BTN = By.cssSelector("button.mobile-filter-btn");
        public static final By ACTIVE_FILTERS_TEXT = By.cssSelector(".active-filters span");
        public static final By MOBILE_FILTER_MODAL = By.cssSelector(
                ".filter-sidebar-container.mobile-visible, .filter-modal-overlay, .filter-modal");

        /** Returns a locator for the amenity checkbox label with the given display name. */
        public static By amenityCheckbox(String name) {
            return By.xpath(
                    "//div[contains(@class,'filter-amenities')]//label[.//span[text()='" + name + "']]"
            );
        }
    }

    public static final class PropertyDetails {
        private PropertyDetails() {}

        public static final By DETAIL_TITLE = By.className("detail-title");
        public static final By DETAIL_LOCATION = By.className("detail-location");
        public static final By DETAIL_TYPE = By.className("detail-category");
        public static final By DETAIL_SPECS = By.cssSelector(".detail-main .detail-specs span");
        public static final By DETAIL_DESCRIPTION = By.className("detail-description");
        public static final By SHOW_MORE_BUTTON = By.className("detail-description-toggle");
        public static final By HOST_AVATAR = By.className("detail-host-avatar");
        public static final By HOST_NAME = By.className("detail-host-name");
        public static final By HOST_SINCE = By.className("detail-host-since");
        public static final By AMENITIES_SECTION = By.className("detail-amenities");
        public static final By AMENITY_ITEMS = By.className("detail-amenity");
        public static final By SHOW_ALL_AMENITIES_BUTTON = By.className("detail-amenities-show-all");
        public static final By PRICE_AMOUNT = By.className("detail-price-amount");
        public static final By AUTH_ERROR = By.className("auth-error");
        public static final By BOOKING_WIDGET = By.className("detail-booking-widget");
        public static final By REVIEWS_SECTION = By.className("detail-reviews");

        public static final By GALLERY_IMAGES = By.cssSelector(".detail-gallery img");
        public static final By SHOW_ALL_PHOTOS_BUTTON = By.className("show-all-photos-btn");
        public static final By GALLERY_MODAL = By.className("gallery-modal");
        public static final By GALLERY_CAROUSEL = By.className("detail-gallery-carousel");

        public static final By DETAIL_WISHLIST_BTN = By.cssSelector("button.detail-wishlist-btn");
        public static final By DETAIL_WISHLIST_FAVORITED_BTN = By.cssSelector("button.detail-wishlist-btn.favorited");
    }

    public static final class Wishlist {
        private Wishlist() {}

        public static final By PAGE_TITLE        = By.xpath("//h1[normalize-space()='My Wishlists']");
        public static final By PROPERTIES_COUNT  = By.className("properties-count");
        public static final By PROPERTY_CARDS    = By.className("property-card");
        public static final By CARD_FAVORITE_BTN = By.cssSelector("button.property-card-favorite");
        public static final By CARD_FAVORITED_BTN = By.cssSelector("button.property-card-favorite.favorited");
        public static final By EMPTY_STATE        = By.className("empty-state");
    }

    public static final class OwnProfile {
        private OwnProfile() {}

        public static final By PROFILE_AVATAR = By.className("profile-avatar");
        public static final By PROFILE_NAME = By.className("profile-name");
        public static final By PROFILE_META = By.className("profile-meta");
        public static final By EDIT_PROFILE_BUTTON = By.xpath("//div[@class='profile-actions']/a[contains(text(), 'Edit Profile')]");
        public static final By BECOME_HOST_BUTTON = By.xpath("//div[@class='profile-actions']/a[normalize-space()='Become a Host']");

        public static final By BIO_TEXT = By.xpath("//div[h2='About']/p");
        public static final By PHONE_TEXT = By.xpath("//div[h2='Phone']/p");
        public static final By EMAIL_TEXT = By.xpath("//div[h2='Email']/p");
    }

    public static final class OtherProfile {
        private OtherProfile() {}

        public static final By PROFILE_AVATAR = By.className("profile-avatar");
        public static final By PROFILE_NAME = By.className("profile-name");
        public static final By PROFILE_META = By.className("profile-meta");
        public static final By BIO_TEXT = By.xpath("//div[@class='profile-section']/p");
        public static final By PHONE_SECTION = By.xpath("//h2[text()='Phone']");
        public static final By EMAIL_SECTION = By.xpath("//h2[text()='Email']");
        public static final By ERROR_MESSAGE = By.xpath("//div[contains(@class, 'error')]|//h1[contains(text(), '404')]|//*[contains(text(), 'User not found')]");
    }

    public static final class EditProfile {
        private EditProfile() {}

        public static final By FIRST_NAME_FIELD = By.id("firstName");
        public static final By LAST_NAME_FIELD = By.id("lastName");
        public static final By PHONE_FIELD = By.id("phone");
        public static final By BIO_FIELD = By.id("bio");
        public static final By AVATAR_URL_FIELD = By.id("avatarUrl");
        public static final By SAVE_CHANGES_BUTTON = By.xpath("//button[text()='Save Changes']");
        public static final By CANCEL_BUTTON = By.xpath("//button[text()='Cancel']");
        public static final By VALIDATION_ERROR = By.cssSelector(".error, .field-error, .auth-error");

        public static By fieldError(String fieldId) {
            return By.xpath("//input[@id='" + fieldId + "']/following-sibling::span[@class='field-error']");
        }
    }

    public static final class Logout {
        private Logout() {}

        public static final By USER_MENU_BUTTON = By.className("navbar-user-btn");
        public static final By LOGOUT_BUTTON = By.xpath("//div[@class='navbar-dropdown']//button[text()='Log out']");
    }

    public static final class HostDashboard {
        private HostDashboard() {}

        public static final By CONTAINER = By.className("host-dashboard-container");
        public static final By SUMMARY_SUBTITLE = By.className("host-dashboard-subtitle");
        public static final By CREATE_NEW_PROPERTY_BUTTON = By.className("host-dashboard-create-btn");

        public static final By PROPERTY_CARD = By.className("host-dashboard-card");
        public static final By CARD_IMAGE = By.cssSelector(".host-dashboard-card-image img");
        public static final By CARD_TITLE = By.cssSelector(".host-dashboard-card-content h3");
        public static final By CARD_LOCATION = By.className("host-dashboard-card-location");
        public static final By CARD_PRICE = By.className("host-dashboard-card-price");
        public static final By CARD_STATUS = By.className("host-dashboard-card-status");
        public static final By CARD_RATING = By.cssSelector(".host-dashboard-card-rating, [class*='rating']");

        public static final By CARD_EDIT_BUTTON = By.xpath(".//a[contains(@class,'host-dashboard-card-btn') and normalize-space()='Edit']");
        public static final By CARD_DELETE_BUTTON = By.xpath(".//button[contains(@class,'host-dashboard-card-btn') and normalize-space()='Delete']");
        public static final By CARD_PUBLISH_TOGGLE = By.xpath(".//button[contains(@class,'host-dashboard-card-btn') and (normalize-space()='Publish' or normalize-space()='Unpublish')]");

        // Empty-state DOM:
        // <div class="host-dashboard-empty"> ... <h2>No properties yet</h2> <p>Start hosting by creating your first property listing</p> ...
        public static final By EMPTY_STATE_MESSAGE = By.xpath("//div[contains(@class,'host-dashboard-empty')]//p[contains(normalize-space(),'Start hosting by creating your first property listing')]");
    }

    public static final class CreateProperty {
        private CreateProperty() {}

        public static final By CONTAINER = By.className("create-property-container");
        public static final By PROGRESS_TEXT = By.className("create-property-progress-text");

        public static final By NEXT_BUTTON = By.cssSelector(".create-property-btn.primary");
        public static final By BACK_BUTTON = By.cssSelector(".create-property-btn.secondary");

        public static final By STEP_1_PROPERTY_TYPE_SELECT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='Property Type']]//select");
        public static final By STEP_1_CATEGORY_SELECT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='Category']]//select");
        public static final By STEP_1_TITLE_INPUT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='Title']]//input");
        public static final By STEP_1_DESCRIPTION_TEXTAREA =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='Description']]//textarea");

        public static final By STEP_2_COUNTRY_INPUT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='Country']]//input");
        public static final By STEP_2_CITY_INPUT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='City']]//input");
        public static final By STEP_2_ADDRESS_INPUT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[contains(normalize-space(),'Address')]]//input");

        public static final By STEP_3_MAX_GUESTS_INPUT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='Max Guests']]//input");
        public static final By STEP_3_BEDROOMS_INPUT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='Bedrooms']]//input");
        public static final By STEP_3_BEDS_INPUT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='Beds']]//input");
        public static final By STEP_3_BATHROOMS_INPUT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[normalize-space()='Bathrooms']]//input");

        public static final By STEP_4_AMENITIES_TITLE =
                By.xpath("//div[contains(@class,'create-property-step')]//h2[normalize-space()='Amenities']");
        public static final By STEP_4_AMENITIES_GRID =
                By.className("create-property-amenities-grid");
        public static final By STEP_4_AMENITY_CHECKBOXES =
                By.cssSelector(".create-property-amenities-grid input[type='checkbox']");
        public static final By STEP_4_AMENITY_ITEMS =
                By.cssSelector(".create-property-amenity-item");
        public static final By STEP_4_GROUP_HEADERS =
                By.cssSelector(".create-property-amenities-group h3, .create-property-amenities-group-title");

        /** Returns a locator for the amenity checkbox whose label contains the given text (exact case). */
        public static By amenityCheckbox(String labelText) {
            return By.xpath(
                    "//label[contains(@class,'create-property-amenity-item')]"
                    + "[contains(normalize-space(),'" + labelText + "')]"
                    + "//input[@type='checkbox']"
            );
        }

        public static final By STEP_5_PHOTOS_TITLE =
                By.xpath("//div[contains(@class,'create-property-step')]//h2[normalize-space()='Photos']");
        public static final By STEP_5_UPLOAD_DROPZONE =
                By.cssSelector(".image-upload-dropzone");
        public static final By STEP_5_UPLOAD_FILE_INPUT =
                By.cssSelector(".image-upload-dropzone input[type='file']");
        public static final By STEP_5_UPLOAD_TEXT =
                By.cssSelector(".image-upload-text");
        public static final By STEP_5_IMAGE_PREVIEWS =
                By.cssSelector(
                        ".image-preview-item, .image-upload-preview-item, .create-property-image-item, .uploaded-image-item"
                );
        public static final By STEP_5_IMAGE_MOVE_UP_BUTTONS =
                By.cssSelector(".image-upload-preview-actions button[title='Move up']");
        public static final By STEP_5_IMAGE_MOVE_DOWN_BUTTONS =
                By.cssSelector(".image-upload-preview-actions button[title='Move down']");
        public static final By STEP_5_IMAGE_DELETE_BUTTONS =
                By.cssSelector(".image-upload-preview-actions button.delete, .image-upload-preview-actions button[title='Delete']");

        public static final By STEP_6_PRICING_TITLE =
                By.xpath("//div[contains(@class,'create-property-step')]//h2[normalize-space()='Pricing']");
        public static final By STEP_6_PRICE_INPUT =
                By.xpath("//div[contains(@class,'create-property-field')][.//label[contains(normalize-space(),'Price per Night')]]//input[@type='number']");

        public static final By STEP_7_REVIEW_TITLE =
                By.xpath("//div[contains(@class,'create-property-step')]//h2[normalize-space()='Review & Submit']");
        public static final By STEP_7_REVIEW_SECTIONS =
                By.cssSelector(".create-property-review-section");
        public static final By STEP_7_CREATE_PROPERTY_BUTTON =
                By.xpath("//button[contains(@class,'create-property-btn') and contains(@class,'primary') and (normalize-space()='Create Property' or normalize-space()='Create Listing')]");
        public static final By STEP_7_SUCCESS_MESSAGE =
                By.cssSelector(".alert, .alert-success, .toast-success, .create-property-success");

        public static final By FIELD_ERRORS = By.cssSelector(".create-property-error, .error, .field-error, .auth-error");
    }

    public static final class EditProperty {
        private EditProperty() {}

        public static final By CONTAINER = By.className("edit-property-container");
        public static final By HEADER_TITLE = By.xpath("//div[contains(@class,'edit-property-header')]//h1[normalize-space()='Edit Property']");
        public static final By DELETE_PROPERTY_BUTTON = By.className("edit-property-delete-btn");

        public static final By SECTION_HEADERS = By.cssSelector(".edit-property-section h2");
        public static final By SAVE_CHANGES_BUTTON = By.xpath("//button[contains(@class,'edit-property-btn') and contains(@class,'primary') and normalize-space()='Save Changes']");
        public static final By FIELD_ERRORS = By.cssSelector(".edit-property-error, .error, .field-error, .auth-error");

        public static final By PROPERTY_TYPE_SELECT =
                By.xpath("//div[contains(@class,'edit-property-field')][.//label[normalize-space()='Property Type']]//select");
        public static final By CATEGORY_SELECT =
                By.xpath("//div[contains(@class,'edit-property-field')][.//label[normalize-space()='Category']]//select");
        public static final By TITLE_INPUT =
                By.xpath("//div[contains(@class,'edit-property-field')][.//label[normalize-space()='Title']]//input");
        public static final By DESCRIPTION_TEXTAREA =
                By.xpath("//div[contains(@class,'edit-property-field')][.//label[normalize-space()='Description']]//textarea");
        public static final By COUNTRY_INPUT =
                By.xpath("//div[contains(@class,'edit-property-field')][.//label[normalize-space()='Country']]//input");
        public static final By CITY_INPUT =
                By.xpath("//div[contains(@class,'edit-property-field')][.//label[normalize-space()='City']]//input");
        public static final By PRICE_INPUT =
                By.xpath("//div[contains(@class,'edit-property-field')][.//label[contains(normalize-space(),'Price per Night')]]//input[@type='number']");

        public static final By AMENITY_GRID = By.className("edit-property-amenities-grid");
        public static final By AMENITY_CHECKBOXES = By.cssSelector(".edit-property-amenities-grid input[type='checkbox']");

        /** Returns a locator for the amenity checkbox whose label contains the given text (exact case). */
        public static By amenityCheckbox(String labelText) {
            return By.xpath(
                    "//label[contains(@class,'edit-property-amenity-item')]"
                    + "[contains(normalize-space(),'" + labelText + "')]"
                    + "//input[@type='checkbox']"
            );
        }
    }

    public static final class DeleteProperty {
        private DeleteProperty() {}

        public static final By EDIT_PAGE_DELETE_BUTTON = By.className("edit-property-delete-btn");
        public static final By DASHBOARD_DELETE_BUTTONS = By.cssSelector(".host-dashboard-card-actions .host-dashboard-card-btn.danger");

        public static final By CONFIRM_BUTTON = By.xpath(
                "//button[contains(@class,'danger') and (normalize-space()='Delete' or normalize-space()='Confirm')]"
                        + " | //button[normalize-space()='Delete' or normalize-space()='Confirm' or normalize-space()='Yes, Delete']"
        );
        public static final By CANCEL_BUTTON = By.xpath(
                "//button[normalize-space()='Cancel' or normalize-space()='Keep' or normalize-space()='No']"
        );

        public static final By DASHBOARD_PROPERTY_TITLE =
                By.cssSelector(".host-dashboard-card-content h3");
    }
}

