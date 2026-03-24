package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OtherProfilePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class OtherProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OtherProfilePage otherProfilePage;
    private final String SLUG = "automation-adel";

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        otherProfilePage = new OtherProfilePage(driver);
    }

    private void loginAsValidUser() {
        loginPage.navigateTo();
        loginPage.login("heko@gmail.com", "heko0109");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://qa-playground.nixdev.co/t/" + SLUG));
    }

    @Test
    public void testViewOtherUserProfilePublicInfo() {
        // Test case 5.1 & 5.4 (Accessible when not logged in)
        String userId = "101";
        otherProfilePage.navigateTo(userId);

        assertTrue(otherProfilePage.isAvatarDisplayed(), "Avatar should be displayed.");
        assertEquals("B", otherProfilePage.getAvatarText(), "Avatar should contain user's first initial.");
        assertEquals("Bob J.", otherProfilePage.getProfileName(), "Profile name should show first name and last initial.");
        
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Guest") || meta.contains("Host"), "Meta should contain user role.");
        assertTrue(meta.contains("Member since"), "Meta should contain 'Member since'.");
        
        assertEquals("Adventure seeker and foodie.", otherProfilePage.getBio(), "Bio should match.");
    }

    @Test
    public void testOtherUserProfileNoPrivateInfo() {
        // Test case 5.2 (Should NOT include email or phone)
        String userId = "102";
        otherProfilePage.navigateTo(userId);

        assertFalse(otherProfilePage.isPhoneSectionVisible(), "Phone number should not be visible on other's profile.");
        assertFalse(otherProfilePage.isEmailSectionVisible(), "Email should not be visible on other's profile.");
        
        // Verifying full name is masked (Alice B. instead of full last name)
        String name = otherProfilePage.getProfileName();
        assertTrue(name.matches("^[A-Za-z]+ [A-Z]\\.$"), "Profile name should only show first name and last initial.");
    }

    @Test
    public void testNonExistentUserProfile() {
        // Test case 5.3 (404 error)
        String nonExistentId = "999999";
        otherProfilePage.navigateTo(nonExistentId);
        
        assertTrue(otherProfilePage.is404Displayed(), "Page should indicate a 404 error for non-existent user.");
    }

    @Test
    public void testApiViewOtherUserNoPrivateData() {
        // Verify API data masking
        String userId = "101";
        driver.get("https://qa-playground.nixdev.co/t/" + SLUG);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object response = js.executeAsyncScript(
            "var callback = arguments[arguments.length - 1];" +
            "fetch('/api/t/" + SLUG + "/users/" + userId + "')" +
            ".then(res => res.json())" +
            ".then(data => callback(JSON.stringify(data)))" +
            ".catch(err => callback(err.message));"
        );

        String jsonResponse = (String) response;
        assertNotNull(jsonResponse, "API response should not be null.");

        // Public fields should be present
        assertTrue(jsonResponse.contains("\"firstName\""), "Response should contain 'firstName'.");
        assertTrue(jsonResponse.contains("\"lastName\""), "Response should contain 'lastName'."); // Might contain masked last name
        assertTrue(jsonResponse.contains("\"bio\""), "Response should contain 'bio'.");
        assertTrue(jsonResponse.contains("\"isHost\""), "Response should contain 'isHost'.");

        // Private fields should NOT be present
        assertFalse(jsonResponse.contains("\"email\""), "Response should NOT contain 'email'.");
        assertFalse(jsonResponse.contains("\"phone\""), "Response should NOT contain 'phone'.");
    }
}
