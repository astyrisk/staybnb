package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.utils.Constants;
import com.staybnb.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class OwnProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;
    private final String SLUG = Constants.SLUG;
    private final String EDIT_PROFILE_URL = TestConfig.BASE_URL + "/profile/edit";

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
    }

    private void loginAsValidUser() {
        loginPage.navigateTo(TestConfig.BASE_URL + "/login");
        loginPage.login(TestConfig.TEST_USERNAME, TestConfig.TEST_PASSWORD);
        WebDriverWait wait = getWait(Constants.MEDIUM_WAIT);
        wait.until(ExpectedConditions.urlToBe(TestConfig.BASE_URL));
    }

    private void navigateToOwnProfile() {
        ownProfilePage.navigateTo(TestConfig.BASE_URL + "/profile");
    }

    @Test
    public void testOwnProfileAvatarDisplayed() {
        loginAsValidUser();
        navigateToOwnProfile();
        assertTrue(ownProfilePage.isAvatarDisplayed(), "Avatar should be displayed.");
    }

    @Test
    public void testOwnProfileFullName() {
        loginAsValidUser();
        navigateToOwnProfile();
        assertEquals(TestData.OwnProfile.FULL_NAME, ownProfilePage.getFullName(), "Full name should match.");
    }

    @Test
    public void testOwnProfileMetaContainsMemberSince() {
        loginAsValidUser();
        navigateToOwnProfile();
        assertTrue(ownProfilePage.getProfileMeta().contains("Member since"), "Profile meta should contain 'Member since'.");
    }

    @Test
    public void testOwnProfileBioNotEmpty() {
        loginAsValidUser();
        navigateToOwnProfile();
        assertFalse(ownProfilePage.getBio().isEmpty(), "Bio should not be empty.");
    }

    @Test
    public void testOwnProfilePhone() {
        loginAsValidUser();
        navigateToOwnProfile();
        assertEquals(TestData.OwnProfile.PHONE, ownProfilePage.getPhone(), "Phone number should match.");
    }

    @Test
    public void testOwnProfileEditProfileButtonVisible() {
        loginAsValidUser();
        navigateToOwnProfile();
        assertTrue(ownProfilePage.isEditProfileButtonVisible(), "Edit Profile button should be visible.");
    }

    @Test
    public void testOwnProfileEditProfileButtonNavigation() {
        loginAsValidUser();
        navigateToOwnProfile();
        ownProfilePage.clickEditProfile();
        WebDriverWait wait = getWait(Constants.MEDIUM_WAIT);
        wait.until(ExpectedConditions.urlToBe(EDIT_PROFILE_URL));
        assertEquals(EDIT_PROFILE_URL, driver.getCurrentUrl(), "Should navigate to the edit profile page.");
    }

    private String getAuthMeApiResponse() {
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
        return (String) response;
    }

    @Test
    public void testAuthMeApiLoggedInResponseNotNull() {
        String jsonResponse = getAuthMeApiResponse();
        assertNotNull(jsonResponse, "API response should not be null.");
    }

    @Test
    public void testAuthMeApiLoggedInContainsId() {
        String jsonResponse = getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"id\""), "Response should contain 'id'.");
    }

    @Test
    public void testAuthMeApiLoggedInContainsEmail() {
        String jsonResponse = getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"email\""), "Response should contain 'email'.");
    }

    @Test
    public void testAuthMeApiLoggedInContainsFirstName() {
        String jsonResponse = getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"firstName\""), "Response should contain 'firstName'.");
    }

    @Test
    public void testAuthMeApiLoggedInContainsLastName() {
        String jsonResponse = getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"lastName\""), "Response should contain 'lastName'.");
    }

    @Test
    public void testAuthMeApiLoggedInContainsPhone() {
        String jsonResponse = getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"phone\""), "Response should contain 'phone'.");
    }

    @Test
    public void testAuthMeApiLoggedInContainsBio() {
        String jsonResponse = getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"bio\""), "Response should contain 'bio'.");
    }

    @Test
    public void testAuthMeApiLoggedInContainsAvatarUrl() {
        String jsonResponse = getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"avatarUrl\""), "Response should contain 'avatarUrl'.");
    }

    @Test
    public void testAuthMeApiLoggedInContainsIsHost() {
        String jsonResponse = getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"isHost\""), "Response should contain 'isHost'.");
    }

    @Test
    public void testAuthMeApiLoggedInContainsCreatedAt() {
        String jsonResponse = getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"createdAt\""), "Response should contain 'createdAt'.");
    }

    @Test
    public void testAuthMeApiLoggedOut() {
        driver.get(TestConfig.BASE_URL);
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
