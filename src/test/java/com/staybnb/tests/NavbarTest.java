package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.data.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NavbarTest extends BaseTest {
    private LoginPage loginPage;
    private PropertyDetailsPage propertyDetailsPage;

    private static final String PROPERTY_ID = Constants.DEFAULT_PROPERTY_ID;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        propertyDetailsPage = new PropertyDetailsPage(driver);
    }

    // --- Authenticated User Tests ---

    @Test
    public void testNavbarLogoDisplayedAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        assertTrue(propertyDetailsPage.navbar().isLogoDisplayed(), ErrorMessages.NAVBAR_LOGO_SHOULD_BE_DISPLAYED);
    }

    @Test
    public void testNavbarUserAvatarDisplayedAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        assertTrue(propertyDetailsPage.navbar().isUserAvatarDisplayed(), ErrorMessages.NAVBAR_USER_AVATAR_SHOULD_BE_DISPLAYED_FOR_AUTHENTICATED_USER);
    }

    @Test
    public void testNavbarLoginLinkNotDisplayedAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        assertFalse(propertyDetailsPage.navbar().isLoginLinkDisplayed(), ErrorMessages.NAVBAR_LOGIN_LINK_SHOULD_NOT_BE_DISPLAYED_FOR_AUTHENTICATED_USER);
    }

    @Test
    public void testNavbarProfileLinkDisplayedInDropdown() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().openUserMenu();
        assertTrue(propertyDetailsPage.navbar().isProfileLinkDisplayed(), ErrorMessages.NAVBAR_PROFILE_LINK_SHOULD_BE_IN_DROPDOWN);
    }

    @Test
    public void testNavbarLogoutButtonDisplayedInDropdown() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().openUserMenu();
        assertTrue(propertyDetailsPage.navbar().isLogoutButtonDisplayed(), ErrorMessages.NAVBAR_LOGOUT_BUTTON_SHOULD_BE_IN_DROPDOWN);
    }

    @Test
    public void testClickProfileInDropdown() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().clickProfileAndWaitForRedirect();
        assertTrue(driver.getCurrentUrl().contains(Constants.PROFILE_URL), ErrorMessages.NAVBAR_SHOULD_NAVIGATE_TO_PROFILE_PAGE);
    }

    @Test
    public void testClickLogoutInDropdownRedirection() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().clickLogoutAndWaitForRedirectToHome();
        assertTrue(driver.getCurrentUrl().contains(Constants.HOME_URL), ErrorMessages.NAVBAR_SHOULD_REDIRECT_TO_HOME_AFTER_LOGOUT);
    }

    @Test
    public void testClickLogoutInDropdownLoginLinkVisibility() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().clickLogoutAndWaitForRedirectToHome();
        assertTrue(propertyDetailsPage.navbar().isLoginLinkDisplayed(), ErrorMessages.NAVBAR_SHOULD_SHOW_LOGIN_LINK_AFTER_LOGOUT);
    }

    @Test
    public void testNavbarHamburgerMenuDisplayedOnMobileAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().setMobileLayout();
        assertTrue(propertyDetailsPage.navbar().isHamburgerMenuDisplayed(), ErrorMessages.NAVBAR_HAMBURGER_MENU_SHOULD_BE_DISPLAYED_ON_MOBILE);
        propertyDetailsPage.navbar().setDesktopLayout();
    }

    @Test
    public void testNavbarUserAvatarDisplayedOnMobileAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().setMobileLayout();
        assertTrue(propertyDetailsPage.navbar().isUserAvatarDisplayed(), ErrorMessages.NAVBAR_USER_AVATAR_SHOULD_BE_VISIBLE_ON_MOBILE_MENU_BUTTON);
        propertyDetailsPage.navbar().setDesktopLayout();
    }

    // --- Visitor Tests ---

    @Test
    public void testNavbarLogoDisplayedVisitor() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        assertTrue(propertyDetailsPage.navbar().isLogoDisplayed(), ErrorMessages.NAVBAR_LOGO_SHOULD_BE_DISPLAYED);
    }

    @Test
    public void testNavbarLoginLinkDisplayedVisitor() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        assertTrue(propertyDetailsPage.navbar().isLoginLinkDisplayed(), ErrorMessages.NAVBAR_LOGIN_LINK_SHOULD_BE_DISPLAYED_FOR_VISITOR);
    }

    @Test
    public void testNavbarRegisterLinkDisplayedVisitor() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        assertTrue(propertyDetailsPage.navbar().isRegisterLinkDisplayed(), ErrorMessages.NAVBAR_REGISTER_LINK_SHOULD_BE_DISPLAYED_FOR_VISITOR);
    }

    @Test
    public void testNavbarUserAvatarNotDisplayedVisitor() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        assertFalse(propertyDetailsPage.navbar().isUserAvatarDisplayed(), ErrorMessages.NAVBAR_USER_AVATAR_SHOULD_NOT_BE_DISPLAYED_FOR_VISITOR);
    }

    @Test
    public void testClickLoginLink() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().clickLoginAndWaitForRedirect();
        assertTrue(driver.getCurrentUrl().contains(Constants.LOGIN_URL), ErrorMessages.NAVBAR_SHOULD_NAVIGATE_TO_LOGIN_PAGE);
    }

    @Test
    public void testClickRegisterLink() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().clickRegisterAndWaitForRedirect();
        assertTrue(driver.getCurrentUrl().contains(Constants.REGISTER_URL), ErrorMessages.NAVBAR_SHOULD_NAVIGATE_TO_REGISTER_PAGE);
    }

    @Test
    public void testNavbarUserAvatarNotDisplayedExclusion() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        assertFalse(propertyDetailsPage.navbar().isUserAvatarDisplayed(), ErrorMessages.NAVBAR_USER_AVATAR_SHOULD_NOT_BE_DISPLAYED_FOR_VISITOR);
    }

    @Test
    public void testNavbarDropdownNotDisplayedVisitor() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        assertFalse(propertyDetailsPage.navbar().isDropdownDisplayed(), ErrorMessages.NAVBAR_SHOULD_NOT_DISPLAY_DROPDOWN_FOR_VISITOR);
    }

    @Test
    public void testNavbarHamburgerMenuNotDisplayedOnMobileVisitor() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().setMobileLayout();
        assertFalse(propertyDetailsPage.navbar().isHamburgerMenuDisplayed(), ErrorMessages.NAVBAR_HAMBURGER_MENU_SHOULD_NOT_BE_DISPLAYED_ON_MOBILE_VISITOR);
        propertyDetailsPage.navbar().setDesktopLayout();
    }

    @Test
    public void testNavbarLoginLinkDisplayedOnMobileVisitor() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
        propertyDetailsPage.navbar().setMobileLayout();
        assertTrue(propertyDetailsPage.navbar().isLoginLinkDisplayed(), ErrorMessages.NAVBAR_LOGIN_LINK_SHOULD_BE_VISIBLE_ON_MOBILE_VISITOR);
        propertyDetailsPage.navbar().setDesktopLayout();
    }
}
