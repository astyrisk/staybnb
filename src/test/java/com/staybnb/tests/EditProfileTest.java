package com.staybnb.tests;

import com.staybnb.pages.EditProfilePage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.staybnb.utils.Constants;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class EditProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;
    private EditProfilePage editProfilePage;
    private final String SLUG = Constants.SLUG;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
        editProfilePage = new EditProfilePage(driver);
    }

    private void loginAsValidUser() {
        loginPage.navigateTo();
        loginPage.login(Constants.VALID_EMAIL, Constants.VALID_PASSWORD);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/t/" + SLUG));
    }

    @Test
    public void testEditProfilePersistence() {
        loginAsValidUser();
        editProfilePage.navigateTo();

        String newFirstName = TestData.EditProfile.NEW_FIRST_NAME;
        String newLastName = TestData.EditProfile.NEW_LAST_NAME;
        String newPhone = TestData.EditProfile.NEW_PHONE;
        String newBio = TestData.EditProfile.NEW_BIO;
        String newAvatarUrl = TestData.EditProfile.NEW_AVATAR_URL;

        editProfilePage.enterFirstName(newFirstName);
        editProfilePage.enterLastName(newLastName);
        editProfilePage.enterPhone(newPhone);
        editProfilePage.enterBio(newBio);
        editProfilePage.enterAvatarUrl(newAvatarUrl);
        editProfilePage.clickSaveChanges();

        // Verify reflection on OwnProfilePage
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/profile"));

        assertEquals(newFirstName + " " + newLastName, ownProfilePage.getFullName(), "Full name should be updated.");
        assertEquals(newPhone, ownProfilePage.getPhone(), "Phone should be updated.");
        assertEquals(newBio, ownProfilePage.getBio(), "Bio should be updated.");
    }

    @Test
    public void testEditProfileValidationErrors() {
        loginAsValidUser();
        editProfilePage.navigateTo();

        // Delete first name and try to save
        editProfilePage.clearField("firstName");
        editProfilePage.clickSaveChanges();
        assertTrue(editProfilePage.isValidationErrorDisplayed(), "Validation error should be displayed for empty first name.");
        assertEquals(TestData.EditProfile.ERROR_FIRST_NAME_REQUIRED, editProfilePage.getFieldError("firstName"), "Error message should match.");

        // Restore first name, delete last name and try to save
        editProfilePage.enterFirstName("heko");
        editProfilePage.clearField("lastName");
        editProfilePage.clickSaveChanges();
        assertTrue(editProfilePage.isValidationErrorDisplayed(), "Validation error should be displayed for empty last name.");
        assertEquals(TestData.EditProfile.ERROR_LAST_NAME_REQUIRED, editProfilePage.getFieldError("lastName"), "Error message should match.");
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

    //FIX should screenshot if it fails?
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
        String updatePayload = String.format("{\"firstName\": \"%s\", \"lastName\": \"%s\", \"phone\": \"%s\", \"bio\": \"%s\", \"avatarUrl\": \"\"}", 
            TestData.EditProfile.API_FIRST_NAME, TestData.EditProfile.API_LAST_NAME, TestData.EditProfile.API_PHONE, TestData.EditProfile.API_BIO);

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
        assertTrue(jsonResponse.contains("\"firstName\":\"" + TestData.EditProfile.API_FIRST_NAME + "\""), "API should return the updated user object.");
    }
}
