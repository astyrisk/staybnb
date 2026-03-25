package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.Navbar;
import com.staybnb.pages.PropertyDetailsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class NavbarTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(NavbarTest.class);
    private Navbar navbar;
    private LoginPage loginPage;
    private PropertyDetailsPage propertyPage;
    private final String BASE_URL = TestConfig.BASE_URL;
    private final String PROPERTY_URL = TestConfig.BASE_URL + "/properties/202";

    @BeforeEach
    public void setup() {
        navbar = new Navbar(driver);
        loginPage = new LoginPage(driver);
        propertyPage = new PropertyDetailsPage(driver);
    }

    private void login() {
        loginPage.navigateTo(TestConfig.BASE_URL + "/login");
        loginPage.login(TestConfig.TEST_USERNAME, TestConfig.TEST_PASSWORD);
        getWait(5).until(ExpectedConditions.urlContains("/"));
    }

    // --- Authenticated User Tests ---

    @Test
    public void testNavbarLogoDisplayedAuthenticated() {
        login();
        driver.get(PROPERTY_URL);

        assertTrue(navbar.isLogoDisplayed(), "Logo should be displayed");
    }

    @Test
    public void testNavbarUserAvatarDisplayedAuthenticated() {
        login();
        driver.get(PROPERTY_URL);
        assertTrue(navbar.isUserAvatarDisplayed(), "User avatar should be displayed for logged-in user");
    }

    @Test
    public void testNavbarLoginLinkNotDisplayedAuthenticated() {
        login();
        driver.get(PROPERTY_URL);
        assertFalse(navbar.isLoginLinkDisplayed(), "Login link should not be displayed for logged-in user");
    }

    @Test
    public void testNavbarProfileLinkDisplayedInDropdown() {
        login();
        driver.get(PROPERTY_URL);
        navbar.openUserMenu();
        assertTrue(navbar.isProfileLinkDisplayed(), "Profile link should be in dropdown");
    }

    @Test
    public void testNavbarLogoutButtonDisplayedInDropdown() {
        login();
        driver.get(PROPERTY_URL);
        navbar.openUserMenu();
        assertTrue(navbar.isLogoutButtonDisplayed(), "Logout button should be in dropdown");
    }

    @Test
    public void testClickProfileInDropdown() {
        login();
        driver.get(PROPERTY_URL);
        navbar.clickProfile();
        getWait(5).until(ExpectedConditions.urlContains("/profile"));
        assertTrue(driver.getCurrentUrl().contains("/profile"), "Should navigate to profile page");
    }

    @Test
    public void testClickLogoutInDropdownRedirection() {
        login();
        driver.get(PROPERTY_URL);
        navbar.clickLogout();
        getWait(5).until(ExpectedConditions.urlContains("/"));
        assertTrue(driver.getCurrentUrl().contains("/"), "Should redirect to login page after logout");
    }

    @Test
    public void testClickLogoutInDropdownLoginLinkVisibility() {
        login();
        driver.get(PROPERTY_URL);
        navbar.clickLogout();
        getWait(5).until(ExpectedConditions.urlContains("/"));
        assertTrue(navbar.isLoginLinkDisplayed(), "Should show login link after logout");
    }

    @Test
    public void testNavbarHamburgerMenuDisplayedOnMobileAuthenticated() {
        login();
        driver.get(PROPERTY_URL);
        navbar.setMobileLayout();
        assertTrue(navbar.isHamburgerMenuDisplayed(), "Hamburger menu should be displayed on mobile");
        navbar.setDesktopLayout();
    }

    @Test
    public void testNavbarUserAvatarDisplayedOnMobileAuthenticated() {
        login();
        driver.get(PROPERTY_URL);
        navbar.setMobileLayout();
        assertTrue(navbar.isUserAvatarDisplayed(), "User avatar should still be visible on mobile as part of the menu button");
        navbar.setDesktopLayout();
    }

    // --- Visitor Tests ---

    @Test
    public void testNavbarLogoDisplayedVisitor() {
        driver.get(PROPERTY_URL);
        assertTrue(navbar.isLogoDisplayed(), "Logo should be displayed");
    }

    @Test
    public void testNavbarLoginLinkDisplayedVisitor() {
        driver.get(PROPERTY_URL);
        assertTrue(navbar.isLoginLinkDisplayed(), "Login link should be displayed for visitor");
    }

    @Test
    public void testNavbarRegisterLinkDisplayedVisitor() {
        driver.get(PROPERTY_URL);
        assertTrue(navbar.isRegisterLinkDisplayed(), "Register link should be displayed for visitor");
    }

    @Test
    public void testNavbarUserAvatarNotDisplayedVisitor() {
        driver.get(PROPERTY_URL);
        assertFalse(navbar.isUserAvatarDisplayed(), "User avatar should not be displayed for visitor");
    }

    @Test
    public void testClickLoginLink() {
        driver.get(PROPERTY_URL);
        navbar.clickLogin();
        getWait(5).until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().endsWith("/login"), "Should navigate to login page");
    }

    @Test
    public void testClickRegisterLink() {
        driver.get(PROPERTY_URL);
        navbar.clickRegister();
        getWait(5).until(ExpectedConditions.urlContains("/register"));
        assertTrue(driver.getCurrentUrl().endsWith("/register"), "Should navigate to register page");
    }

    @Test
    public void testNavbarUserAvatarNotDisplayedExclusion() {
        driver.get(PROPERTY_URL);
        assertFalse(navbar.isUserAvatarDisplayed(), "Visitor should not see user avatar");
    }

    @Test
    public void testNavbarDropdownNotDisplayedVisitor() {
        driver.get(PROPERTY_URL);
        assertFalse(navbar.isDropdownDisplayed(), "Visitor should not see user dropdown");
    }

    @Test
    public void testNavbarHamburgerMenuNotDisplayedOnMobileVisitor() {
        driver.get(PROPERTY_URL);
        navbar.setMobileLayout();
        assertFalse(navbar.isHamburgerMenuDisplayed(), "Hamburger menu should NOT be displayed for visitors on mobile");
        navbar.setDesktopLayout();
    }

    @Test
    public void testNavbarLoginLinkDisplayedOnMobileVisitor() {
        driver.get(PROPERTY_URL);
        navbar.setMobileLayout();
        assertTrue(navbar.isLoginLinkDisplayed(), "Login link should be visible for visitors on mobile");
        navbar.setDesktopLayout();
    }
}
