package com.staybnb.tests.ui.navigation;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.AppConstants;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Navigation")
@Feature("Navbar")
@Tag("regression")
public class NavbarTest extends BaseTest {
    private LoginPage loginPage;
    private PropertyDetailsPage propertyDetailsPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        propertyDetailsPage = new PropertyDetailsPage(driver);
    }

    private void loginAndNavigateToPropertyDetails() {
        loginAsTestUserAndLandOnHome(loginPage);
        propertyDetailsPage.navigateTo(AppConstants.DEFAULT_PROPERTY_ID);
    }

    // --- Authenticated User Tests ---

    @ParameterizedTest(name = "Authenticated navbar condition: {0}")
    @MethodSource("provideAuthenticatedNavbarVisibilityCases")
    public void testAuthenticatedNavbarVisibility(String checkName) {
        loginAndNavigateToPropertyDetails();

        assertTrue(
                propertyDetailsPage.navbar().isAuthenticatedNavbarCheckMet(checkName),
                ErrorMessages.NAVBAR_AUTHENTICATED_CHECK_CONDITION_SHOULD_BE_MET
        );
    }

    @Test
    @DisplayName("Authenticated: Profile link is displayed in user dropdown")
    public void testNavbarProfileLinkDisplayedInDropdown() {
        loginAndNavigateToPropertyDetails();
        propertyDetailsPage.navbar().openUserMenu();

        assertTrue(propertyDetailsPage.navbar().isProfileLinkDisplayed(), ErrorMessages.NAVBAR_PROFILE_LINK_SHOULD_BE_IN_DROPDOWN);
    }

    @Test
    @DisplayName("Authenticated: Logout button is displayed in user dropdown")
    public void testNavbarLogoutButtonDisplayedInDropdown() {
        loginAndNavigateToPropertyDetails();
        propertyDetailsPage.navbar().openUserMenu();

        assertTrue(propertyDetailsPage.navbar().isLogoutButtonDisplayed(), ErrorMessages.NAVBAR_LOGOUT_BUTTON_SHOULD_BE_IN_DROPDOWN);
    }

    @Test
    @DisplayName("Clicking profile link in dropdown navigates to profile page")
    public void testClickProfileInDropdown() {
        loginAndNavigateToPropertyDetails();
        propertyDetailsPage.navbar().clickProfileAndWaitForRedirect();

        assertTrue(isUrlContains(AppConstants.PROFILE_URL), ErrorMessages.NAVBAR_SHOULD_NAVIGATE_TO_PROFILE_PAGE);
    }

    @Test
    @DisplayName("Clicking logout in dropdown redirects to home page")
    public void testClickLogoutInDropdownRedirection() {
        loginAndNavigateToPropertyDetails();
        propertyDetailsPage.navbar().clickLogoutAndWaitForRedirectToHome();

        assertTrue(isUrlContains(AppConstants.HOME_URL), ErrorMessages.NAVBAR_SHOULD_REDIRECT_TO_HOME_AFTER_LOGOUT);
    }

    @Test
    @DisplayName("After logout, login link is visible in navbar")
    public void testClickLogoutInDropdownLoginLinkVisibility() {
        loginAndNavigateToPropertyDetails();
        propertyDetailsPage.navbar().clickLogoutAndWaitForRedirectToHome();

        assertTrue(propertyDetailsPage.navbar().isLoginLinkDisplayed(), ErrorMessages.NAVBAR_SHOULD_SHOW_LOGIN_LINK_AFTER_LOGOUT);
    }

    @Test
    @DisplayName("Mobile: Hamburger menu is displayed when authenticated")
    public void testNavbarHamburgerMenuDisplayedOnMobileAuthenticated() {
        loginAndNavigateToPropertyDetails();
        propertyDetailsPage.navbar().setMobileLayout();

        assertTrue(propertyDetailsPage.navbar().isHamburgerMenuDisplayed(), ErrorMessages.NAVBAR_HAMBURGER_MENU_SHOULD_BE_DISPLAYED_ON_MOBILE);
        propertyDetailsPage.navbar().setDesktopLayout();
    }

    @Test
    @DisplayName("Mobile: User avatar is visible in menu button when authenticated")
    public void testNavbarUserAvatarDisplayedOnMobileAuthenticated() {
        loginAndNavigateToPropertyDetails();
        propertyDetailsPage.navbar().setMobileLayout();

        assertTrue(propertyDetailsPage.navbar().isUserAvatarDisplayed(), ErrorMessages.NAVBAR_USER_AVATAR_SHOULD_BE_VISIBLE_ON_MOBILE_MENU_BUTTON);
        propertyDetailsPage.navbar().setDesktopLayout();
    }

    // --- Visitor Tests ---

    @ParameterizedTest(name = "Visitor navbar condition: {0}")
    @MethodSource("provideVisitorNavbarVisibilityCases")
    public void testVisitorNavbarVisibility(String checkName) {
        propertyDetailsPage.navigateTo(AppConstants.DEFAULT_PROPERTY_ID);

        assertTrue(
                propertyDetailsPage.navbar().isVisitorNavbarCheckMet(checkName),
                ErrorMessages.NAVBAR_VISITOR_CHECK_CONDITION_SHOULD_BE_MET
        );
    }

    @Test
    @DisplayName("Visitor: Clicking login link navigates to login page")
    public void testClickLoginLink() {
        propertyDetailsPage.navigateTo(AppConstants.DEFAULT_PROPERTY_ID);
        propertyDetailsPage.navbar().clickLoginAndWaitForRedirect();

        assertTrue(isUrlContains(AppConstants.LOGIN_URL), ErrorMessages.NAVBAR_SHOULD_NAVIGATE_TO_LOGIN_PAGE);
    }

    @Test
    @DisplayName("Visitor: Clicking register link navigates to register page")
    public void testClickRegisterLink() {
        propertyDetailsPage.navigateTo(AppConstants.DEFAULT_PROPERTY_ID);
        propertyDetailsPage.navbar().clickRegisterAndWaitForRedirect();

        assertTrue(isUrlContains(AppConstants.REGISTER_URL), ErrorMessages.NAVBAR_SHOULD_NAVIGATE_TO_REGISTER_PAGE);
    }

    @Test
    @DisplayName("Visitor: User dropdown is not displayed")
    public void testNavbarDropdownNotDisplayedVisitor() {
        propertyDetailsPage.navigateTo(AppConstants.DEFAULT_PROPERTY_ID);

        assertFalse(propertyDetailsPage.navbar().isDropdownDisplayed(), ErrorMessages.NAVBAR_SHOULD_NOT_DISPLAY_DROPDOWN_FOR_VISITOR);
    }

    @Test
    @DisplayName("Mobile Visitor: Hamburger menu is not displayed")
    public void testNavbarHamburgerMenuNotDisplayedOnMobileVisitor() {
        propertyDetailsPage.navigateTo(AppConstants.DEFAULT_PROPERTY_ID);
        propertyDetailsPage.navbar().setMobileLayout();

        assertFalse(propertyDetailsPage.navbar().isHamburgerMenuDisplayed(), ErrorMessages.NAVBAR_HAMBURGER_MENU_SHOULD_NOT_BE_DISPLAYED_ON_MOBILE_VISITOR);
        propertyDetailsPage.navbar().setDesktopLayout();
    }

    @Test
    @DisplayName("Mobile Visitor: Login link is visible in navbar")
    public void testNavbarLoginLinkDisplayedOnMobileVisitor() {
        propertyDetailsPage.navigateTo(AppConstants.DEFAULT_PROPERTY_ID);
        propertyDetailsPage.navbar().setMobileLayout();

        assertTrue(propertyDetailsPage.navbar().isLoginLinkDisplayed(), ErrorMessages.NAVBAR_LOGIN_LINK_SHOULD_BE_VISIBLE_ON_MOBILE_VISITOR);
        propertyDetailsPage.navbar().setDesktopLayout();
    }

    private static Stream<String> provideAuthenticatedNavbarVisibilityCases() {
        return Stream.of(
                "logo displayed",
                "user avatar displayed",
                "login link hidden"
        );
    }

    private static Stream<String> provideVisitorNavbarVisibilityCases() {
        return Stream.of(
                "logo displayed",
                "login link displayed",
                "register link displayed",
                "user avatar hidden"
        );
    }
}
