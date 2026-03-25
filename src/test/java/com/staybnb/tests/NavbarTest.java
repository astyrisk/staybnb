package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.Navbar;
import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class NavbarTest extends BaseTest {
    private Navbar navbar;
    private LoginPage loginPage;
    private PropertyDetailsPage propertyPage;
    private final String BASE_URL = Constants.HOME_URL;
    private final String PROPERTY_URL = Constants.PROPERTY_DETAILS_BASE_URL + "202";

    @BeforeEach
    public void setup() {
        navbar = new Navbar(driver);
        loginPage = new LoginPage(driver);
        propertyPage = new PropertyDetailsPage(driver);
    }

    private void login() {
        loginPage.navigateTo();
        loginPage.login(Constants.VALID_EMAIL, Constants.VALID_PASSWORD);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(BASE_URL));
    }

    // --- Authenticated User Tests ---

    @Test
    public void testNavbarAuthenticatedUser() {
        login();
        driver.get(PROPERTY_URL);

        assertTrue(navbar.isLogoDisplayed(), "Logo should be displayed");
        assertTrue(navbar.getLogoHref().contains("/t/" + Constants.SLUG), "Logo should link to home");
        assertTrue(navbar.isUserAvatarDisplayed(), "User avatar should be displayed for logged-in user");
        assertFalse(navbar.isLoginLinkDisplayed(), "Login link should not be displayed for logged-in user");
    }

    @Test
    public void testAvatarDropdownItems() {
        login();
        driver.get(PROPERTY_URL);
        navbar.openUserMenu();

        assertTrue(navbar.isProfileLinkDisplayed(), "Profile link should be in dropdown");
        assertTrue(navbar.isLogoutButtonDisplayed(), "Logout button should be in dropdown");
    }

    @Test
    public void testClickProfileInDropdown() {
        login();
        driver.get(PROPERTY_URL);
        navbar.clickProfile();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/profile"));
        assertTrue(driver.getCurrentUrl().contains("/profile"), "Should navigate to profile page");
    }

    @Test
    public void testClickLogoutInDropdown() {
        login();
        driver.get(PROPERTY_URL);
        navbar.clickLogout();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(BASE_URL));
        assertEquals(BASE_URL, driver.getCurrentUrl(), "Should redirect to home page after logout");
        assertTrue(navbar.isLoginLinkDisplayed(), "Should show login link after logout");
    }

    @Test
    public void testMobileLayoutAuthenticated() {
        login();
        driver.get(PROPERTY_URL);
        navbar.setMobileLayout();

        assertTrue(navbar.isHamburgerMenuDisplayed(), "Hamburger menu should be displayed on mobile");
        // Based on the snippet, the avatar is inside the same button as the hamburger and remains visible.
        assertTrue(navbar.isUserAvatarDisplayed(), "User avatar should still be visible on mobile as part of the menu button");
        
        navbar.setDesktopLayout();
    }

    // --- Visitor Tests ---

    @Test
    public void testNavbarVisitor() {
        driver.get(PROPERTY_URL);

        assertTrue(navbar.isLogoDisplayed(), "Logo should be displayed");
        assertTrue(navbar.isLoginLinkDisplayed(), "Login link should be displayed for visitor");
        assertTrue(navbar.isRegisterLinkDisplayed(), "Register link should be displayed for visitor");
        assertFalse(navbar.isUserAvatarDisplayed(), "User avatar should not be displayed for visitor");
    }

    @Test
    public void testClickLoginLink() {
        driver.get(PROPERTY_URL);
        navbar.clickLogin();

        new WebDriverWait(driver, Duration.ofSeconds(Constants.SHORT_WAIT))
                .until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().endsWith("/login"), "Should navigate to login page");
    }

    @Test
    public void testClickRegisterLink() {
        driver.get(PROPERTY_URL);
        navbar.clickRegister();

        new WebDriverWait(driver, Duration.ofSeconds(Constants.SHORT_WAIT))
                .until(ExpectedConditions.urlContains("/register"));
        assertTrue(driver.getCurrentUrl().endsWith("/register"), "Should navigate to register page");
    }

    @Test
    public void testVisitorNavbarExclusions() {
        driver.get(PROPERTY_URL);
        
        assertFalse(navbar.isUserAvatarDisplayed(), "Visitor should not see user avatar");
        assertFalse(navbar.isDropdownDisplayed(), "Visitor should not see user dropdown");
    }

    @Test
    public void testMobileLayoutVisitor() {
        driver.get(PROPERTY_URL);
        navbar.setMobileLayout();

        assertFalse(navbar.isHamburgerMenuDisplayed(), "Hamburger menu should NOT be displayed for visitors on mobile");
        assertTrue(navbar.isLoginLinkDisplayed(), "Login link should be visible for visitors on mobile");
        
        navbar.setDesktopLayout();
    }
}
