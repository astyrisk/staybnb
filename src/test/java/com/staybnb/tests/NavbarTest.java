package com.staybnb.tests;

import com.staybnb.utils.ErrorMessages;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.Navbar;
import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NavbarTest extends BaseTest {
    private Navbar navbar;
    private LoginPage loginPage;
    private PropertyDetailsPage propertyDetailsPage;

    private static final String PROPERTY_ID = Constants.DEFAULT_PROPERTY_ID;

    @BeforeEach
    public void setup() {
        navbar = new Navbar(driver);
        loginPage = new LoginPage(driver);
        propertyDetailsPage = new PropertyDetailsPage(driver);
    }

    // --- Authenticated User Tests ---

    @Test
    public void testNavbarLogoDisplayedAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        assertTrue(navbar.isLogoDisplayed(), ErrorMessages.NAVBAR_LOGO_SHOULD_BE_DISPLAYED);
    }

    @Test
    public void testNavbarUserAvatarDisplayedAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        assertTrue(navbar.isUserAvatarDisplayed(), ErrorMessages.NAVBAR_USER_AVATAR_SHOULD_BE_DISPLAYED_FOR_AUTHENTICATED_USER);
    }

    @Test
    public void testNavbarLoginLinkNotDisplayedAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        assertFalse(navbar.isLoginLinkDisplayed(), ErrorMessages.NAVBAR_LOGIN_LINK_SHOULD_NOT_BE_DISPLAYED_FOR_AUTHENTICATED_USER);
    }

    @Test
    public void testNavbarProfileLinkDisplayedInDropdown() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.openUserMenu();
        assertTrue(navbar.isProfileLinkDisplayed(), ErrorMessages.NAVBAR_PROFILE_LINK_SHOULD_BE_IN_DROPDOWN);
    }

    @Test
    public void testNavbarLogoutButtonDisplayedInDropdown() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.openUserMenu();
        assertTrue(navbar.isLogoutButtonDisplayed(), ErrorMessages.NAVBAR_LOGOUT_BUTTON_SHOULD_BE_IN_DROPDOWN);
    }

    @Test
    public void testClickProfileInDropdown() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.clickProfileAndWaitForRedirect();
        assertTrue(driver.getCurrentUrl().contains(Constants.PROFILE_URL), ErrorMessages.NAVBAR_SHOULD_NAVIGATE_TO_PROFILE_PAGE);
    }

    @Test
    public void testClickLogoutInDropdownRedirection() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.clickLogoutAndWaitForRedirectToHome();
        assertTrue(driver.getCurrentUrl().contains(Constants.HOME_URL), ErrorMessages.NAVBAR_SHOULD_REDIRECT_TO_HOME_AFTER_LOGOUT);
    }

    @Test
    public void testClickLogoutInDropdownLoginLinkVisibility() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.clickLogoutAndWaitForRedirectToHome();
        assertTrue(navbar.isLoginLinkDisplayed(), ErrorMessages.NAVBAR_SHOULD_SHOW_LOGIN_LINK_AFTER_LOGOUT);
    }

    @Test
    public void testNavbarHamburgerMenuDisplayedOnMobileAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.setMobileLayout();
        assertTrue(navbar.isHamburgerMenuDisplayed(), ErrorMessages.NAVBAR_HAMBURGER_MENU_SHOULD_BE_DISPLAYED_ON_MOBILE);
        navbar.setDesktopLayout();
    }

    @Test
    public void testNavbarUserAvatarDisplayedOnMobileAuthenticated() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.setMobileLayout();
        assertTrue(navbar.isUserAvatarDisplayed(), ErrorMessages.NAVBAR_USER_AVATAR_SHOULD_BE_VISIBLE_ON_MOBILE_MENU_BUTTON);
        navbar.setDesktopLayout();
    }

    // --- Visitor Tests ---

    @Test
    public void testNavbarLogoDisplayedVisitor() {
        propertyDetailsPage.load(PROPERTY_ID);
        assertTrue(navbar.isLogoDisplayed(), ErrorMessages.NAVBAR_LOGO_SHOULD_BE_DISPLAYED);
    }

    @Test
    public void testNavbarLoginLinkDisplayedVisitor() {
        propertyDetailsPage.load(PROPERTY_ID);
        assertTrue(navbar.isLoginLinkDisplayed(), ErrorMessages.NAVBAR_LOGIN_LINK_SHOULD_BE_DISPLAYED_FOR_VISITOR);
    }

    @Test
    public void testNavbarRegisterLinkDisplayedVisitor() {
        propertyDetailsPage.load(PROPERTY_ID);
        assertTrue(navbar.isRegisterLinkDisplayed(), ErrorMessages.NAVBAR_REGISTER_LINK_SHOULD_BE_DISPLAYED_FOR_VISITOR);
    }

    @Test
    public void testNavbarUserAvatarNotDisplayedVisitor() {
        propertyDetailsPage.load(PROPERTY_ID);
        assertFalse(navbar.isUserAvatarDisplayed(), ErrorMessages.NAVBAR_USER_AVATAR_SHOULD_NOT_BE_DISPLAYED_FOR_VISITOR);
    }

    @Test
    public void testClickLoginLink() {
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.clickLoginAndWaitForRedirect();
        assertTrue(driver.getCurrentUrl().contains(Constants.LOGIN_URL), ErrorMessages.NAVBAR_SHOULD_NAVIGATE_TO_LOGIN_PAGE);
    }

    @Test
    public void testClickRegisterLink() {
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.clickRegisterAndWaitForRedirect();
        assertTrue(driver.getCurrentUrl().contains(Constants.REGISTER_URL), ErrorMessages.NAVBAR_SHOULD_NAVIGATE_TO_REGISTER_PAGE);
    }

    @Test
    public void testNavbarUserAvatarNotDisplayedExclusion() {
        propertyDetailsPage.load(PROPERTY_ID);
        assertFalse(navbar.isUserAvatarDisplayed(), ErrorMessages.NAVBAR_USER_AVATAR_SHOULD_NOT_BE_DISPLAYED_FOR_VISITOR);
    }

    @Test
    public void testNavbarDropdownNotDisplayedVisitor() {
        propertyDetailsPage.load(PROPERTY_ID);
        assertFalse(navbar.isDropdownDisplayed(), ErrorMessages.NAVBAR_SHOULD_NOT_DISPLAY_DROPDOWN_FOR_VISITOR);
    }

    @Test
    public void testNavbarHamburgerMenuNotDisplayedOnMobileVisitor() {
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.setMobileLayout();
        assertFalse(navbar.isHamburgerMenuDisplayed(), ErrorMessages.NAVBAR_HAMBURGER_MENU_SHOULD_NOT_BE_DISPLAYED_ON_MOBILE_VISITOR);
        navbar.setDesktopLayout();
    }

    @Test
    public void testNavbarLoginLinkDisplayedOnMobileVisitor() {
        propertyDetailsPage.load(PROPERTY_ID);
        navbar.setMobileLayout();
        assertTrue(navbar.isLoginLinkDisplayed(), ErrorMessages.NAVBAR_LOGIN_LINK_SHOULD_BE_VISIBLE_ON_MOBILE_VISITOR);
        navbar.setDesktopLayout();
    }
}
