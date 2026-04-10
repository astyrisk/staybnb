package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.pages.EditProfilePage;
import com.staybnb.pages.LoginPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
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
public class UpdateProfileApiTest extends BaseTest {
    private LoginPage loginPage;
    private EditProfilePage editProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        editProfilePage = new EditProfilePage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
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
                Constants.EditProfile.API_FIRST_NAME, Constants.EditProfile.API_LAST_NAME, Constants.EditProfile.API_PHONE, Constants.EditProfile.API_BIO);
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
                Constants.EditProfile.API_FIRST_NAME, Constants.EditProfile.API_LAST_NAME, Constants.EditProfile.API_PHONE, Constants.EditProfile.API_BIO);
        String jsonResponse = editProfilePage.updateMyProfileViaApi(updatePayload);

        assertTrue(
                jsonResponse != null && jsonResponse.contains("\"firstName\":\"" + Constants.EditProfile.API_FIRST_NAME + "\""),
                "API should return the updated user object."
        );
    }
}
