package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.EditProfilePage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.staybnb.utils.Constants;
import com.staybnb.utils.ErrorMessages;

import static org.junit.jupiter.api.Assertions.*;

public class EditProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;
    private EditProfilePage editProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        loginPage.load();
        ownProfilePage = new OwnProfilePage(driver);
        editProfilePage = new EditProfilePage(driver);
    }

    private void performEditProfileUpdate(String firstName, String lastName, String phone, String bio, String avatarUrl) {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        editProfilePage.updateProfile(firstName, lastName, phone, bio, avatarUrl);
        ownProfilePage.load();
    }

    @Test
    public void testEditProfilePersistenceFullName() {
        performEditProfileUpdate(
                Constants.EditProfile.NEW_FIRST_NAME,
                Constants.EditProfile.NEW_LAST_NAME,
                Constants.EditProfile.NEW_PHONE,
                Constants.EditProfile.NEW_BIO,
                Constants.EditProfile.NEW_AVATAR_URL
        );
        assertEquals(
                Constants.EditProfile.NEW_FIRST_NAME + " " + Constants.EditProfile.NEW_LAST_NAME,
                ownProfilePage.getFullName(),
                "Full name should be updated."
        );
    }

    @Test
    public void testEditProfilePersistencePhone() {
        performEditProfileUpdate(
                Constants.EditProfile.NEW_FIRST_NAME,
                Constants.EditProfile.NEW_LAST_NAME,
                Constants.EditProfile.NEW_PHONE,
                Constants.EditProfile.NEW_BIO,
                Constants.EditProfile.NEW_AVATAR_URL
        );
        assertEquals(Constants.EditProfile.NEW_PHONE, ownProfilePage.getPhone(), "Phone should be updated.");
    }

    @Test
    public void testEditProfilePersistenceBio() {
        performEditProfileUpdate(
                Constants.EditProfile.NEW_FIRST_NAME,
                Constants.EditProfile.NEW_LAST_NAME,
                Constants.EditProfile.NEW_PHONE,
                Constants.EditProfile.NEW_BIO,
                Constants.EditProfile.NEW_AVATAR_URL
        );
        assertEquals(Constants.EditProfile.NEW_BIO, ownProfilePage.getBio(), "Bio should be updated.");
    }

    @Test
    public void testEditProfileValidationErrorFirstNameRequired() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        editProfilePage.submitWithEmptyFirstName();
        assertTrue(editProfilePage.isValidationErrorDisplayed(), "Validation error should be displayed for empty first name.");
    }

    @Test
    public void testEditProfileValidationErrorMessageFirstNameRequired() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        editProfilePage.submitWithEmptyFirstName();
        assertEquals(ErrorMessages.FIRST_NAME_REQUIRED, editProfilePage.getFieldError("firstName"), "Error message should match.");
    }

    @Test
    public void testEditProfileValidationErrorLastNameRequired() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        editProfilePage.submitWithEmptyLastName("heko");
        assertTrue(editProfilePage.isValidationErrorDisplayed(), "Validation error should be displayed for empty last name.");
    }

    @Test
    public void testEditProfileValidationErrorMessageLastNameRequired() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        editProfilePage.submitWithEmptyLastName("heko");
        assertEquals(ErrorMessages.LAST_NAME_REQUIRED, editProfilePage.getFieldError("lastName"), "Error message should match.");
    }

    @Test
    public void testEditProfileCancel() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String originalFirstName = editProfilePage.attemptFirstNameChangeThenCancel("CanceledName");
        assertEquals(originalFirstName, editProfilePage.getFirstNameValue(), "Changes should not be saved after cancellation.");
    }

    @Test
    public void testEditProfileUnauthorizedAccess() {
        editProfilePage.load();
        assertTrue(editProfilePage.is401Displayed(), "401 error should be displayed when accessing edit profile while not logged in.");
    }

    @Test
    public void testApiUpdateUserProfileTokenNotNull() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String token = loginPage.getStaybnbToken();
        assertNotNull(token, ErrorMessages.AUTH_TOKEN_SHOULD_BE_PRESENT_IN_LOCAL_STORAGE);
    }

    @Test
    public void testApiUpdateUserProfileResponseNotNull() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String updatePayload = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"phone\":\"%s\",\"bio\":\"%s\",\"avatarUrl\":\"\"}",
                Constants.EditProfile.API_FIRST_NAME, Constants.EditProfile.API_LAST_NAME, Constants.EditProfile.API_PHONE, Constants.EditProfile.API_BIO);
        String jsonResponse = editProfilePage.updateMyProfileViaApi(updatePayload);
        assertNotNull(jsonResponse, ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL);
    }

    @Test
    public void testApiUpdateUserProfileResponseContainsUpdatedFirstName() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String updatePayload = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"phone\":\"%s\",\"bio\":\"%s\",\"avatarUrl\":\"\"}",
                Constants.EditProfile.API_FIRST_NAME, Constants.EditProfile.API_LAST_NAME, Constants.EditProfile.API_PHONE, Constants.EditProfile.API_BIO);
        String jsonResponse = editProfilePage.updateMyProfileViaApi(updatePayload);
        assertTrue(jsonResponse != null && jsonResponse.contains("\"firstName\":\"" + Constants.EditProfile.API_FIRST_NAME + "\""),
                "API should return the updated user object.");
    }
}
