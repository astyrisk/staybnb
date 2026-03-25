package com.staybnb.tests;

import com.staybnb.config.TestConfig;
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
        loginPage.navigateTo(TestConfig.BASE_URL + "/login");
        loginPage.login(TestConfig.TEST_USERNAME, TestConfig.TEST_PASSWORD);
        WebDriverWait wait = getWait(10);
        wait.until(ExpectedConditions.urlToBe(TestConfig.BASE_URL));
    }

    private void performEditProfileUpdate(String firstName, String lastName, String phone, String bio, String avatarUrl) {
        loginAsValidUser();
        editProfilePage.navigateTo(TestConfig.BASE_URL + "/profile/edit");
        editProfilePage.enterFirstName(firstName);
        editProfilePage.enterLastName(lastName);
        editProfilePage.enterPhone(phone);
        editProfilePage.enterBio(bio);
        editProfilePage.enterAvatarUrl(avatarUrl);
        editProfilePage.clickSaveChanges();
        WebDriverWait wait = getWait(10);
        wait.until(ExpectedConditions.urlContains("/profile"));
    }

    @Test
    public void testEditProfilePersistenceFullName() {
        String newFirstName = TestData.EditProfile.NEW_FIRST_NAME;
        String newLastName = TestData.EditProfile.NEW_LAST_NAME;
        performEditProfileUpdate(newFirstName, newLastName, TestData.EditProfile.NEW_PHONE, TestData.EditProfile.NEW_BIO, TestData.EditProfile.NEW_AVATAR_URL);
        assertEquals(newFirstName + " " + newLastName, ownProfilePage.getFullName(), "Full name should be updated.");
    }

    @Test
    public void testEditProfilePersistencePhone() {
        String newPhone = TestData.EditProfile.NEW_PHONE;
        performEditProfileUpdate(TestData.EditProfile.NEW_FIRST_NAME, TestData.EditProfile.NEW_LAST_NAME, newPhone, TestData.EditProfile.NEW_BIO, TestData.EditProfile.NEW_AVATAR_URL);
        assertEquals(newPhone, ownProfilePage.getPhone(), "Phone should be updated.");
    }

    @Test
    public void testEditProfilePersistenceBio() {
        String newBio = TestData.EditProfile.NEW_BIO;
        performEditProfileUpdate(TestData.EditProfile.NEW_FIRST_NAME, TestData.EditProfile.NEW_LAST_NAME, TestData.EditProfile.NEW_PHONE, newBio, TestData.EditProfile.NEW_AVATAR_URL);
        assertEquals(newBio, ownProfilePage.getBio(), "Bio should be updated.");
    }

    @Test
    public void testEditProfileValidationErrorFirstNameRequired() {
        loginAsValidUser();
        editProfilePage.navigateTo(TestConfig.BASE_URL + "/profile/edit");
        editProfilePage.clearField("firstName");
        editProfilePage.clickSaveChanges();
        assertTrue(editProfilePage.isValidationErrorDisplayed(), "Validation error should be displayed for empty first name.");
    }

    @Test
    public void testEditProfileValidationErrorMessageFirstNameRequired() {
        loginAsValidUser();
        editProfilePage.navigateTo(TestConfig.BASE_URL + "/profile/edit");
        editProfilePage.clearField("firstName");
        editProfilePage.clickSaveChanges();
        assertEquals(TestData.EditProfile.ERROR_FIRST_NAME_REQUIRED, editProfilePage.getFieldError("firstName"), "Error message should match.");
    }

    @Test
    public void testEditProfileValidationErrorLastNameRequired() {
        loginAsValidUser();
        editProfilePage.navigateTo(TestConfig.BASE_URL + "/profile/edit");
        editProfilePage.enterFirstName("heko");
        editProfilePage.clearField("lastName");
        editProfilePage.clickSaveChanges();
        assertTrue(editProfilePage.isValidationErrorDisplayed(), "Validation error should be displayed for empty last name.");
    }

    @Test
    public void testEditProfileValidationErrorMessageLastNameRequired() {
        loginAsValidUser();
        editProfilePage.navigateTo(TestConfig.BASE_URL + "/profile/edit");
        editProfilePage.enterFirstName("heko");
        editProfilePage.clearField("lastName");
        editProfilePage.clickSaveChanges();
        assertEquals(TestData.EditProfile.ERROR_LAST_NAME_REQUIRED, editProfilePage.getFieldError("lastName"), "Error message should match.");
    }

    @Test
    public void testEditProfileCancel() {
        loginAsValidUser();
        editProfilePage.navigateTo(TestConfig.BASE_URL + "/profile/edit");

        String originalFirstName = editProfilePage.getFirstNameValue();
        editProfilePage.enterFirstName("CanceledName");
        editProfilePage.clickCancel();

        WebDriverWait wait = getWait(10);
        wait.until(ExpectedConditions.urlContains("/profile"));
        
        editProfilePage.navigateTo(TestConfig.BASE_URL + "/profile/edit");
        assertEquals(originalFirstName, editProfilePage.getFirstNameValue(), "Changes should not be saved after cancellation.");
    }

    @Test
    public void testEditProfileUnauthorizedAccess() {
        editProfilePage.navigateTo(TestConfig.BASE_URL + "/profile/edit");
        assertTrue(editProfilePage.is401Displayed(), "401 error should be displayed when accessing edit profile while not logged in.");
    }

    @Test
    public void testApiUpdateUserProfileTokenNotNull() {
        loginAsValidUser();
        String token = loginPage.getStaybnbToken();
        assertNotNull(token, "Auth token should be present in localStorage.");
    }

    @Test
    public void testApiUpdateUserProfileResponseNotNull() {
        loginAsValidUser();
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
    }

    @Test
    public void testApiUpdateUserProfileResponseContainsUpdatedFirstName() {
        loginAsValidUser();
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
        assertTrue(jsonResponse != null && jsonResponse.contains("\"firstName\":\"" + TestData.EditProfile.API_FIRST_NAME + "\""), "API should return the updated user object.");
    }
}
