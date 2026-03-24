package com.staybnb.tests;

import com.staybnb.pages.EditProfilePage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class EditProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;
    private EditProfilePage editProfilePage;
    private final String SLUG = "automation-adel";

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
        editProfilePage = new EditProfilePage(driver);
    }

    private void loginAsValidUser() {
        loginPage.navigateTo();
        loginPage.login("heko@gmail.com", "heko0109");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/t/" + SLUG));
    }

    @Test
    public void testEditProfilePersistence() {
        loginAsValidUser();
        editProfilePage.navigateTo();

        String newFirstName = "HekoUpdated";
        String newLastName = "NekoUpdated";
        String newPhone = "+201556638077";
        String newBio = "Updated bio for testing persistence.";
        String newAvatarUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCHo3CkaH0oRY3MvrEN0xgn-x_Lsn3Lm3lVQ&s";

        editProfilePage.enterFirstName(newFirstName);
        editProfilePage.enterLastName(newLastName);
        editProfilePage.enterPhone(newPhone);
        editProfilePage.enterBio(newBio);
        editProfilePage.enterAvatarUrl(newAvatarUrl);
        editProfilePage.clickSaveChanges();

        // Verify reflection on OwnProfilePage
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/profile"));

        System.out.println(ownProfilePage.getFullName());
        System.out.println(ownProfilePage.getPhone());
        System.out.println(ownProfilePage.getBio());

        assertEquals(newFirstName + " " + newLastName, ownProfilePage.getFullName(), "Full name should be updated.");
        assertEquals(newPhone, ownProfilePage.getPhone(), "Phone should be updated.");
        assertEquals(newBio, ownProfilePage.getBio(), "Bio should be updated.");
//        assertEquals(newFirstName.substring(0, 1).toUpperCase(), ownProfilePage.getAvatarStyle().contains("profile-avatar") ? "" : "Check avatar initial if text", "Avatar initial might change.");
    }

    @Test
    public void testEditProfileValidationErrors() {
        loginAsValidUser();
        editProfilePage.navigateTo();

        // Delete first name and try to save
        editProfilePage.clearField("firstName");
        editProfilePage.clickSaveChanges();
        assertTrue(editProfilePage.isValidationErrorDisplayed(), "Validation error should be displayed for empty first name.");
        assertEquals("First name is required", editProfilePage.getFieldError("firstName"), "Error message should match.");

        // Restore first name, delete last name and try to save
        editProfilePage.enterFirstName("heko");
        editProfilePage.clearField("lastName");
        editProfilePage.clickSaveChanges();
        assertTrue(editProfilePage.isValidationErrorDisplayed(), "Validation error should be displayed for empty last name.");
        assertEquals("Last name is required", editProfilePage.getFieldError("lastName"), "Error message should match.");
    }

    @Test
    public void testEditProfileCancel() {
        loginAsValidUser();
        editProfilePage.navigateTo();

        String originalFirstName = editProfilePage.getFirstNameValue();
        editProfilePage.enterFirstName("CanceledName");
        editProfilePage.clickCancel();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/profile"));
        
        // Navigate back to edit to verify it wasn't saved
        editProfilePage.navigateTo();
        assertEquals(originalFirstName, editProfilePage.getFirstNameValue(), "Changes should not be saved after cancellation.");
    }

    @Test
    public void testEditProfileUnauthorizedAccess() {
        // Access edit profile without logging in
        editProfilePage.navigateTo();
        assertTrue(editProfilePage.is401Displayed(), "401 error should be displayed when accessing edit profile while not logged in.");
    }

    @Test
    public void testApiUpdateUserProfile() {
        loginAsValidUser();
        String token = loginPage.getStaybnbToken();
        assertNotNull(token, "Auth token should be present in localStorage.");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String updatePayload = "{\"firstName\": \"HekoAPI\", \"lastName\": \"NekoAPI\", \"phone\": \"+1234567890\", \"bio\": \"API Bio\", \"avatarUrl\": \"\"}";

        Object response = js.executeAsyncScript(
            "var callback = arguments[arguments.length - 1];" +
            "fetch('/api/t/" + SLUG + "/users/me', {" +
            "  method: 'PUT'," +
            "  headers: {" +
            "    'Content-Type': 'application/json'," +
            "    'Authorization': 'Bearer ' + localStorage.getItem('staybnb_token')" +
            "  }," +
            "  body: '" + updatePayload + "'" +
            "}).then(res => res.status === 200 ? res.json() : { status: res.status })" +
            "  .then(data => callback(JSON.stringify(data)))" +
            "  .catch(err => callback(err.message));"
        );

        String jsonResponse = (String) response;
        assertNotNull(jsonResponse, "API response should not be null.");
        assertTrue(jsonResponse.contains("\"firstName\":\"HekoAPI\""), "API should return the updated user object.");
    }
}
