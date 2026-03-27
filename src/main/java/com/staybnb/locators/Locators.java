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
        public static final By GLOBAL_ERROR_MESSAGE = By.cssSelector(".alert, .alert-danger, .toast-message, .auth-error");
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

        public static final By DROPDOWN_MENU = By.className("navbar-dropdown");
        public static final By PROFILE_LINK = By.xpath("//div[@class='navbar-dropdown']//a[contains(@href, '/profile')]");
        public static final By LOGOUT_BUTTON = By.xpath("//div[@class='navbar-dropdown']//button[text()='Log out']");
    }

    public static final class Home {
        private Home() {}

        public static final By HERO_SECTION = By.className("home-hero");
        public static final By HERO_HEADLINE = By.cssSelector(".home-hero-content h1");
        public static final By CATEGORY_BAR = By.className("categories-bar");
        public static final By CATEGORY_ICONS = By.className("category-icon");
        public static final By PROPERTY_GRID = By.className("property-grid");
        public static final By PROPERTY_CARDS = By.className("property-card");

        public static final By CARD_IMAGE = By.cssSelector(".property-card-image img");
        public static final By CARD_TITLE = By.className("property-card-title");
        public static final By CARD_LOCATION = By.className("property-card-location");
        public static final By CARD_PRICE = By.className("property-card-price");
    }

    public static final class PropertyListing {
        private PropertyListing() {}

        public static final By PROPERTY_GRID = By.className("property-grid");
        public static final By PROPERTY_CARD = By.className("property-card");
        public static final By CARD_IMAGE = By.cssSelector(".property-card-image img");
        public static final By CARD_TITLE = By.className("property-card-title");
        public static final By CARD_LOCATION = By.className("property-card-location");
        public static final By CARD_PRICE = By.className("property-card-price");
        public static final By CARD_RATING = By.className("property-card-rating");
        public static final By PROPERTY_LIST_CONTROLS = By.className("property-list-controls");

        public static final By SEARCH_INPUT = By.cssSelector("input[type='search']");
        public static final By FILTER_BUTTONS = By.cssSelector("button.filter-btn");
        public static final By SORT_SELECT = By.tagName("select");
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
    }

    public static final class OwnProfile {
        private OwnProfile() {}

        public static final By PROFILE_AVATAR = By.className("profile-avatar");
        public static final By PROFILE_NAME = By.className("profile-name");
        public static final By PROFILE_META = By.className("profile-meta");
        public static final By EDIT_PROFILE_BUTTON = By.xpath("//div[@class='profile-actions']/a[contains(text(), 'Edit Profile')]");

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
    }

    public static final class Logout {
        private Logout() {}

        public static final By USER_MENU_BUTTON = By.className("navbar-user-btn");
        public static final By LOGOUT_BUTTON = By.xpath("//div[@class='navbar-dropdown']//button[text()='Log out']");
    }
}

