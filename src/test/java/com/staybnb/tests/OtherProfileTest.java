package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OtherProfilePage;
import com.staybnb.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class OtherProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OtherProfilePage otherProfilePage;
    private final String SLUG = Constants.SLUG;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        otherProfilePage = new OtherProfilePage(driver);
    }

    private void loginAsValidUser() {
        loginPage.navigateTo(TestConfig.BASE_URL + "/login");
        loginPage.login(TestConfig.TEST_USERNAME, TestConfig.TEST_PASSWORD);
        WebDriverWait wait = getWait(Constants.MEDIUM_WAIT);
        wait.until(ExpectedConditions.urlToBe(TestConfig.BASE_URL));
    }

    private void navigateToOtherUser(String userId) {
        otherProfilePage.navigateToUrl(TestConfig.BASE_URL + "/users/" + userId);
    }

    @Test
    public void testOtherUserProfileAvatarDisplayed() {
        navigateToOtherUser(Constants.USER_ID_101);
        assertTrue(otherProfilePage.isAvatarDisplayed(), "Avatar should be displayed.");
    }

    @Test
    public void testOtherUserProfileAvatarText() {
        navigateToOtherUser(Constants.USER_ID_101);
        assertEquals("B", otherProfilePage.getAvatarText(), "Avatar should contain user's first initial.");
    }

    @Test
    public void testOtherUserProfileName() {
        navigateToOtherUser(Constants.USER_ID_101);
        assertEquals("Bob J.", otherProfilePage.getProfileName(), "Profile name should show first name and last initial.");
    }

    @Test
    public void testOtherUserProfileMetaContainsRole() {
        navigateToOtherUser(Constants.USER_ID_101);
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Guest") || meta.contains("Host"), "Meta should contain user role.");
    }

    @Test
    public void testOtherUserProfileMetaContainsMemberSince() {
        navigateToOtherUser(Constants.USER_ID_101);
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Member since"), "Meta should contain 'Member since'.");
    }

    @Test
    public void testOtherUserProfileBio() {
        navigateToOtherUser(Constants.USER_ID_101);
        assertEquals("Adventure seeker and foodie.", otherProfilePage.getBio(), "Bio should match.");
    }

    @Test
    public void testOtherUserProfilePhoneNotVisible() {
        navigateToOtherUser(Constants.USER_ID_102);
        assertFalse(otherProfilePage.isPhoneSectionVisible(), "Phone number should not be visible on other's profile.");
    }

    @Test
    public void testOtherUserProfileEmailNotVisible() {
        navigateToOtherUser(Constants.USER_ID_102);
        assertFalse(otherProfilePage.isEmailSectionVisible(), "Email should not be visible on other's profile.");
    }

    @Test
    public void testOtherUserProfileNameMasked() {
        navigateToOtherUser(Constants.USER_ID_102);
        String name = otherProfilePage.getProfileName();
        assertTrue(name.matches("^[A-Za-z]+ [A-Z]\\.$"), "Profile name should only show first name and last initial.");
    }

    @Test
    public void testNonExistentUserProfile() {
        navigateToOtherUser(Constants.NON_EXISTENT_ID);
        assertTrue(otherProfilePage.is404Displayed(), "Page should indicate a 404 error for non-existent user.");
    }

    private String getOtherUserApiResponse(String userId) {
        driver.get(TestConfig.BASE_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object response = js.executeAsyncScript(
            "var callback = arguments[arguments.length - 1];" +
            "fetch('/api/t/" + SLUG + "/users/" + userId + "')" +
            ".then(res => res.json())" +
            ".then(data => callback(JSON.stringify(data)))" +
            ".catch(err => callback(err.message));"
        );
        return (String) response;
    }

    @Test
    public void testApiViewOtherUserResponseNotNull() {
        String jsonResponse = getOtherUserApiResponse(Constants.USER_ID_101);
        assertNotNull(jsonResponse, "API response should not be null.");
    }

    @Test
    public void testApiViewOtherUserContainsFirstName() {
        String jsonResponse = getOtherUserApiResponse(Constants.USER_ID_101);
        assertTrue(jsonResponse.contains("\"firstName\""), "Response should contain 'firstName'.");
    }

    @Test
    public void testApiViewOtherUserContainsLastName() {
        String jsonResponse = getOtherUserApiResponse(Constants.USER_ID_101);
        assertTrue(jsonResponse.contains("\"lastName\""), "Response should contain 'lastName'.");
    }

    @Test
    public void testApiViewOtherUserContainsBio() {
        String jsonResponse = getOtherUserApiResponse(Constants.USER_ID_101);
        assertTrue(jsonResponse.contains("\"bio\""), "Response should contain 'bio'.");
    }

    @Test
    public void testApiViewOtherUserContainsIsHost() {
        String jsonResponse = getOtherUserApiResponse(Constants.USER_ID_101);
        assertTrue(jsonResponse.contains("\"isHost\""), "Response should contain 'isHost'.");
    }

    @Test
    public void testApiViewOtherUserDoesNotContainEmail() {
        String jsonResponse = getOtherUserApiResponse(Constants.USER_ID_101);
        assertFalse(jsonResponse.contains("\"email\""), "Response should NOT contain 'email'.");
    }

    @Test
    public void testApiViewOtherUserDoesNotContainPhone() {
        String jsonResponse = getOtherUserApiResponse(Constants.USER_ID_101);
        assertFalse(jsonResponse.contains("\"phone\""), "Response should NOT contain 'phone'.");
    }
}
