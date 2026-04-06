package com.staybnb.tests;

import com.staybnb.pages.EditProfilePage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EditProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;
    private EditProfilePage editProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
        editProfilePage = new EditProfilePage(driver);
    }

    private void performEditProfileUpdate(String firstName, String lastName, String phone, String bio, String avatarUrl) {
        loginAsTestUserAndLandOnHome(loginPage);
        editProfilePage.updateProfile(firstName, lastName, phone, bio, avatarUrl);
        ownProfilePage.navigateTo();
    }

    @ParameterizedTest(name = "Edit profile persistence: {0}")
    @MethodSource("provideEditProfilePersistenceCases")
    public void testEditProfilePersistence(String checkName) {
        performEditProfileUpdate(
                Constants.EditProfile.NEW_FIRST_NAME,
                Constants.EditProfile.NEW_LAST_NAME,
                Constants.EditProfile.NEW_PHONE,
                Constants.EditProfile.NEW_BIO,
                Constants.EditProfile.NEW_AVATAR_URL
        );

        switch (checkName) {
            case "full name" -> assertEquals(
                    Constants.EditProfile.NEW_FIRST_NAME + " " + Constants.EditProfile.NEW_LAST_NAME,
                    ownProfilePage.getFullName(),
                    "Full name should be updated."
            );
            case "phone" -> assertEquals(
                    Constants.EditProfile.NEW_PHONE,
                    ownProfilePage.getPhone(),
                    "Phone should be updated."
            );
            case "bio" -> assertEquals(
                    Constants.EditProfile.NEW_BIO,
                    ownProfilePage.getBio(),
                    "Bio should be updated."
            );
            default -> throw new IllegalArgumentException("Unsupported case: " + checkName);
        }
    }

    @Test
    public void testEditProfileValidationErrorFirstNameRequired() {
        loginAsTestUserAndLandOnHome(loginPage);
        editProfilePage.submitWithEmptyFirstName();

        assertTrue(
                editProfilePage.isValidationErrorDisplayed(),
                "Validation error should be displayed for empty first name."
        );
    }

    @Test
    public void testEditProfileValidationErrorLastNameRequired() {
        loginAsTestUserAndLandOnHome(loginPage);
        editProfilePage.submitWithEmptyLastName("heko");

        assertTrue(
                editProfilePage.isValidationErrorDisplayed(),
                "Validation error should be displayed for empty last name."
        );
    }

    @ParameterizedTest(name = "Validation message for required {0}")
    @MethodSource("provideRequiredFieldValidationCases")
    public void testEditProfileValidationErrorMessages(String fieldName) {
        loginAsTestUserAndLandOnHome(loginPage);
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
    public void testEditProfileCancel() {
        loginAsTestUserAndLandOnHome(loginPage);
        String originalFirstName = editProfilePage.attemptFirstNameChangeThenCancel("CanceledName");

        assertEquals(
                originalFirstName,
                editProfilePage.getFirstNameValue(),
                "Changes should not be saved after cancellation."
        );
    }

    @Test
    public void testEditProfileUnauthorizedAccess() {
        editProfilePage.navigateTo();

        assertTrue(
                editProfilePage.is401Displayed(),
                "401 error should be displayed when accessing edit profile while not logged in."
        );
    }

    @Test
    public void testApiUpdateUserProfileTokenNotNull() {
        loginAsTestUserAndLandOnHome(loginPage);
        String token = loginPage.getStaybnbToken();

        assertNotNull(
                token,
                ErrorMessages.AUTH_TOKEN_SHOULD_BE_PRESENT_IN_LOCAL_STORAGE
        );
    }

    @Test
    public void testApiUpdateUserProfileResponseNotNull() {
        loginAsTestUserAndLandOnHome(loginPage);
        String updatePayload = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"phone\":\"%s\",\"bio\":\"%s\",\"avatarUrl\":\"\"}",
                Constants.EditProfile.API_FIRST_NAME, Constants.EditProfile.API_LAST_NAME, Constants.EditProfile.API_PHONE, Constants.EditProfile.API_BIO);
        String jsonResponse = editProfilePage.updateMyProfileViaApi(updatePayload);

        assertNotNull(
                jsonResponse,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @Test
    public void testApiUpdateUserProfileResponseContainsUpdatedFirstName() {
        loginAsTestUserAndLandOnHome(loginPage);
        String updatePayload = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"phone\":\"%s\",\"bio\":\"%s\",\"avatarUrl\":\"\"}",
                Constants.EditProfile.API_FIRST_NAME, Constants.EditProfile.API_LAST_NAME, Constants.EditProfile.API_PHONE, Constants.EditProfile.API_BIO);
        String jsonResponse = editProfilePage.updateMyProfileViaApi(updatePayload);

        assertTrue(
                jsonResponse != null && jsonResponse.contains("\"firstName\":\"" + Constants.EditProfile.API_FIRST_NAME + "\""),
                "API should return the updated user object."
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
