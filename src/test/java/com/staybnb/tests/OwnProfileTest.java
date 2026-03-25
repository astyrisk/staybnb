package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.utils.Constants;
import com.staybnb.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class OwnProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;
    private final String SLUG = Constants.SLUG;
    private final String EDIT_PROFILE_URL = Constants.EDIT_PROFILE_URL;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
    }

    private void loginAsValidUser() {
        loginPage.navigateTo();
        loginPage.login(Constants.VALID_EMAIL, Constants.VALID_PASSWORD);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.MEDIUM_WAIT));
        wait.until(ExpectedConditions.urlToBe(Constants.HOME_URL));
    }

    @Test
    public void testViewFullProfileDetails() {
        loginAsValidUser();
        ownProfilePage.navigateTo();

        assertTrue(ownProfilePage.isAvatarDisplayed(), "Avatar should be displayed.");
        assertEquals(TestData.OwnProfile.FULL_NAME, ownProfilePage.getFullName(), "Full name should match.");
        assertTrue(ownProfilePage.getProfileMeta().contains("Member since"), "Profile meta should contain 'Member since'.");
        assertFalse(ownProfilePage.getBio().isEmpty(), "Bio should not be empty.");
        assertEquals(TestData.OwnProfile.PHONE, ownProfilePage.getPhone(), "Phone number should match.");
    }

    @Test
    public void testEditProfileButtonNavigation() {
        loginAsValidUser();
        ownProfilePage.navigateTo();
        assertTrue(ownProfilePage.isEditProfileButtonVisible(), "Edit Profile button should be visible.");

        ownProfilePage.clickEditProfile();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.MEDIUM_WAIT));
        wait.until(ExpectedConditions.urlToBe(EDIT_PROFILE_URL));
        assertEquals(EDIT_PROFILE_URL, driver.getCurrentUrl(), "Should navigate to the edit profile page.");
    }

    @Test
    public void testAuthMeApiLoggedIn() {
        loginAsValidUser();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        Object response = js.executeAsyncScript(
            "var callback = arguments[arguments.length - 1];" +
            "var token = localStorage.getItem('staybnb_token');" +
            "fetch('/api/t/" + SLUG + "/auth/me', {" +
            "  headers: { 'Authorization': 'Bearer ' + token }" +
            "})" +
            ".then(res => res.json())" +
            ".then(data => callback(JSON.stringify(data)))" +
            ".catch(err => callback(err.message));"
        );

        String jsonResponse = (String) response;
        assertNotNull(jsonResponse, "API response should not be null.");
        
        assertTrue(jsonResponse.contains("\"id\""), "Response should contain 'id'.");
        assertTrue(jsonResponse.contains("\"email\""), "Response should contain 'email'.");
        assertTrue(jsonResponse.contains("\"firstName\""), "Response should contain 'firstName'.");
        assertTrue(jsonResponse.contains("\"lastName\""), "Response should contain 'lastName'.");
        assertTrue(jsonResponse.contains("\"phone\""), "Response should contain 'phone'.");
        assertTrue(jsonResponse.contains("\"bio\""), "Response should contain 'bio'.");
        assertTrue(jsonResponse.contains("\"avatarUrl\""), "Response should contain 'avatarUrl'.");
        assertTrue(jsonResponse.contains("\"isHost\""), "Response should contain 'isHost'.");
        assertTrue(jsonResponse.contains("\"createdAt\""), "Response should contain 'createdAt'.");
    }

    @Test
    public void testAuthMeApiLoggedOut() {
        driver.get("https://qa-playground.nixdev.co/t/" + SLUG);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object responseStatus = js.executeAsyncScript(
            "var callback = arguments[arguments.length - 1];" +
            "fetch('/api/t/" + SLUG + "/auth/me')" +
            ".then(res => callback(res.status))" +
            ".catch(err => callback(err.message));"
        );

        assertEquals(401L, ((Number) responseStatus).longValue(), "API should return 401 when not logged in.");
    }
}
