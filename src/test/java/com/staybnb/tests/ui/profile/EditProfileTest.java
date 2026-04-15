package com.staybnb.tests.ui.profile;

import com.staybnb.pages.EditProfilePage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.AppConstants;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Edit Profile")
@Tag("regression")
@ResourceLock("test-user-profile")
public class EditProfileTest extends BaseTest {
    private OwnProfilePage ownProfilePage;
    private EditProfilePage editProfilePage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
        editProfilePage = new EditProfilePage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private void performEditProfileUpdate(String firstName, String lastName, String phone, String bio, String avatarUrl) {
        editProfilePage.updateProfile(firstName, lastName, phone, bio, avatarUrl);
        ownProfilePage.navigateViaNavbar();
    }

    @ParameterizedTest(name = "Edit profile persistence: {0}")
    @MethodSource("provideEditProfilePersistenceCases")
    public void testEditProfilePersistence(String checkName) {
        performEditProfileUpdate(
                AppConstants.EditProfile.NEW_FIRST_NAME,
                AppConstants.EditProfile.NEW_LAST_NAME,
                AppConstants.EditProfile.NEW_PHONE,
                AppConstants.EditProfile.NEW_BIO,
                AppConstants.EditProfile.NEW_AVATAR_URL
        );

        switch (checkName) {
            case "full name" -> assertEquals(
                    AppConstants.EditProfile.NEW_FIRST_NAME + " " + AppConstants.EditProfile.NEW_LAST_NAME,
                    ownProfilePage.getFullName(),
                    "Full name should be updated."
            );
            case "phone" -> assertEquals(
                    AppConstants.EditProfile.NEW_PHONE,
                    ownProfilePage.getPhone(),
                    "Phone should be updated."
            );
            case "bio" -> assertEquals(
                    AppConstants.EditProfile.NEW_BIO,
                    ownProfilePage.getBio(),
                    "Bio should be updated."
            );
            default -> throw new IllegalArgumentException("Unsupported case: " + checkName);
        }
    }

    @Test
    @DisplayName("Submitting with empty first name shows validation error")
    public void testEditProfileValidationErrorFirstNameRequired() {
        editProfilePage.submitWithEmptyFirstName();

        assertTrue(
                editProfilePage.isValidationErrorDisplayed(),
                "Validation error should be displayed for empty first name."
        );
    }

    @Test
    @DisplayName("Submitting with empty last name shows validation error")
    public void testEditProfileValidationErrorLastNameRequired() {
        editProfilePage.submitWithEmptyLastName("heko");

        assertTrue(
                editProfilePage.isValidationErrorDisplayed(),
                "Validation error should be displayed for empty last name."
        );
    }

    @ParameterizedTest(name = "Validation message for required {0}")
    @MethodSource("provideRequiredFieldValidationCases")
    public void testEditProfileValidationErrorMessages(String fieldName) {
        switch (fieldName) {
            case "firstName" -> {
                editProfilePage.submitWithEmptyFirstName();

                assertEquals(
                        ErrorMessages.FIRST_NAME_REQUIRED,
                        editProfilePage.getFieldError("firstName"),
                        "Error message should match."
                );
            }
            case "lastName" -> {
                editProfilePage.submitWithEmptyLastName("heko");

                assertEquals(
                        ErrorMessages.LAST_NAME_REQUIRED,
                        editProfilePage.getFieldError("lastName"),
                        "Error message should match."
                );
            }
            default -> throw new IllegalArgumentException("Unsupported field: " + fieldName);
        }
    }

    @Test
    @DisplayName("Cancelling edit profile does not save changes")
    public void testEditProfileCancel() {
        String originalFirstName = editProfilePage.attemptFirstNameChangeThenCancel("CanceledName");

        assertEquals(
                originalFirstName,
                editProfilePage.getFirstNameValue(),
                "Changes should not be saved after cancellation."
        );
    }

    @Test
    @DisplayName("Accessing edit profile while logged out shows 401 error")
    public void testEditProfileUnauthorizedAccess() {
        editProfilePage.navbar().clickLogoutAndWaitForRedirectToHome();
        editProfilePage.navigateTo();

        assertTrue(
                editProfilePage.is401Displayed(),
                "401 error should be displayed when accessing edit profile while not logged in."
        );
    }

    private static Stream<String> provideEditProfilePersistenceCases() {
        return Stream.of(
                "full name",
                "phone",
                "bio"
        );
    }

    private static Stream<String> provideRequiredFieldValidationCases() {
        return Stream.of(
                "firstName",
                "lastName"
        );
    }
}
