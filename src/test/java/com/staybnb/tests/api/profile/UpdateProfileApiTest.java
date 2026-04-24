package com.staybnb.tests.api.profile;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestDataConstants;
import com.staybnb.pages.EditProfilePage;
import com.staybnb.pages.LoginPage;
import com.staybnb.tests.BaseApiTest;
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
@Feature("Update Profile API")
@Tag("api")
@ResourceLock("test-user-profile")
public class UpdateProfileApiTest extends BaseApiTest {
    private LoginPage loginPage;
    private EditProfilePage editProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        editProfilePage = new EditProfilePage(driver);
        loginAsUser();
    }

    @AfterEach
    public void restoreProfile() {
        String restorePayload = String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"phone\":\"%s\",\"bio\":\"%s\",\"avatarUrl\":\"%s\"}",
                TestDataConstants.EditProfile.NEW_FIRST_NAME,
                TestDataConstants.EditProfile.NEW_LAST_NAME,
                TestDataConstants.EditProfile.NEW_PHONE,
                TestDataConstants.EditProfile.NEW_BIO,
                TestDataConstants.EditProfile.NEW_AVATAR_URL
        );
        editProfilePage.updateMyProfileViaApi(restorePayload);
    }

    @Test
    @DisplayName("Auth token is present in localStorage when logged in")
    public void testApiUpdateUserProfileTokenNotNull() {
        String token = loginPage.getStaybnbToken();

        assertNotNull(
                token,
                ErrorMessages.AUTH_TOKEN_SHOULD_BE_PRESENT_IN_LOCAL_STORAGE
        );
    }

    @Test
    @DisplayName("Update profile API response is not null")
    public void testApiUpdateUserProfileResponseNotNull() {
        String updatePayload = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"phone\":\"%s\",\"bio\":\"%s\",\"avatarUrl\":\"\"}",
                TestDataConstants.EditProfile.API_FIRST_NAME, TestDataConstants.EditProfile.API_LAST_NAME, TestDataConstants.EditProfile.API_PHONE, TestDataConstants.EditProfile.API_BIO);
        String jsonResponse = editProfilePage.updateMyProfileViaApi(updatePayload);

        assertNotNull(
                jsonResponse,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @Test
    @DisplayName("Update profile API response contains the updated first name")
    public void testApiUpdateUserProfileResponseContainsUpdatedFirstName() {
        String updatePayload = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"phone\":\"%s\",\"bio\":\"%s\",\"avatarUrl\":\"\"}",
                TestDataConstants.EditProfile.API_FIRST_NAME, TestDataConstants.EditProfile.API_LAST_NAME, TestDataConstants.EditProfile.API_PHONE, TestDataConstants.EditProfile.API_BIO);
        String jsonResponse = editProfilePage.updateMyProfileViaApi(updatePayload);

        assertTrue(
                jsonResponse != null && jsonResponse.contains("\"firstName\":\"" + TestDataConstants.EditProfile.API_FIRST_NAME + "\""),
                "API should return the updated user object."
        );
    }
}
