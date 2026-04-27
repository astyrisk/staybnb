package com.staybnb.tests.ui.profile;

import com.staybnb.pages.EditProfilePage;
import com.staybnb.pages.ProfilePage;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestDataConstants;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Edit Profile")
@Tag("regression")
@ResourceLock("test-user-profile")
public class EditProfileTest extends BaseTest {
    private ProfilePage ownProfilePage;
    private EditProfilePage editProfilePage;

    @BeforeEach
    public void setup() {
        ownProfilePage = new ProfilePage(driver);
        editProfilePage = new EditProfilePage(driver);
        loginAsUser();
    }

    @AfterEach
    public void restoreProfile() {
        editProfilePage.updateMyProfileViaScript(
                TestDataConstants.OwnProfile.FIRST_NAME,
                TestDataConstants.OwnProfile.LAST_NAME,
                TestDataConstants.OwnProfile.PHONE,
                TestDataConstants.OwnProfile.BIO,
                TestDataConstants.OwnProfile.AVATAR_URL
        );
    }

    private void performEditProfileUpdate() {
        editProfilePage.updateProfile(
                TestDataConstants.EditProfile.NEW_FIRST_NAME,
                TestDataConstants.EditProfile.NEW_LAST_NAME,
                TestDataConstants.EditProfile.NEW_PHONE,
                TestDataConstants.EditProfile.NEW_BIO,
                TestDataConstants.EditProfile.NEW_AVATAR_URL
        );
        ownProfilePage.navigateViaNavbar();
        ownProfilePage.waitForFullNameToBe(TestDataConstants.EditProfile.NEW_FIRST_NAME + " " + TestDataConstants.EditProfile.NEW_LAST_NAME);

    }

    @Test
    @DisplayName("Edit profile persists updated full name")
    public void testEditProfilePersistsFullName() {
        performEditProfileUpdate();

        assertEquals(
                TestDataConstants.EditProfile.NEW_FIRST_NAME + " " + TestDataConstants.EditProfile.NEW_LAST_NAME,
                ownProfilePage.getFullName(),
                ErrorMessages.FULL_NAME_SHOULD_BE_UPDATED
        );
    }

    @Test
    @DisplayName("Edit profile persists updated phone")
    public void testEditProfilePersistsPhone() {
        performEditProfileUpdate();

        assertEquals(
                TestDataConstants.EditProfile.NEW_PHONE,
                ownProfilePage.getPhone(),
                ErrorMessages.PHONE_SHOULD_BE_UPDATED
        );
    }

    @Test
    @DisplayName("Edit profile persists updated bio")
    public void testEditProfilePersistsBio() {
        performEditProfileUpdate();

        assertEquals(
                TestDataConstants.EditProfile.NEW_BIO,
                ownProfilePage.getBio(),
                ErrorMessages.BIO_SHOULD_BE_UPDATED
        );
    }

    @Test
    @DisplayName("Validation message shown when first name is empty")
    public void testEditProfileValidationErrorMessageFirstName() {
        editProfilePage.submitWithEmptyField("firstName");

        assertEquals(
                ErrorMessages.FIRST_NAME_REQUIRED,
                editProfilePage.getFieldError("firstName"),
                ErrorMessages.VALIDATION_ERROR_MESSAGE_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Validation message shown when last name is empty")
    public void testEditProfileValidationErrorMessageLastName() {
        editProfilePage.submitWithEmptyField("lastName");

        assertEquals(
                ErrorMessages.LAST_NAME_REQUIRED,
                editProfilePage.getFieldError("lastName"),
                ErrorMessages.VALIDATION_ERROR_MESSAGE_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Cancelling edit profile does not save changes")
    public void testEditProfileCancel() {
        String originalFirstName = editProfilePage.attemptFirstNameChangeThenCancel("CanceledName");

        assertEquals(
                originalFirstName,
                editProfilePage.getFirstNameValue(),
                ErrorMessages.CHANGES_SHOULD_NOT_BE_SAVED_AFTER_CANCELLATION
        );
    }

    // fails
    @Test
    @DisplayName("Accessing edit profile while logged out shows 401 error")
    public void testEditProfileUnauthorizedAccess() {
        editProfilePage.logoutAndGoHome();
        editProfilePage.navigateTo();

        assertTrue(
                editProfilePage.is401Displayed(),
                ErrorMessages.EDIT_PROFILE_SHOULD_SHOW_401_WHEN_LOGGED_OUT
        );
    }

}
