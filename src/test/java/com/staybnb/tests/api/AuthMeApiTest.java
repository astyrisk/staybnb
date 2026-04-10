package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Auth Me API")
@Tag("api")
public class AuthMeApiTest extends BaseTest {
    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    @Test
    @DisplayName("Auth/me API response is not null when logged in")
    public void testAuthMeApiLoggedInResponseNotNull() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertNotNull(
                jsonResponse,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'id' field")
    public void testAuthMeApiLoggedInContainsId() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"id\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_ID
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'email' field")
    public void testAuthMeApiLoggedInContainsEmail() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"email\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_EMAIL
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'firstName' field")
    public void testAuthMeApiLoggedInContainsFirstName() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"firstName\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_FIRST_NAME
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'lastName' field")
    public void testAuthMeApiLoggedInContainsLastName() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"lastName\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_LAST_NAME
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'phone' field")
    public void testAuthMeApiLoggedInContainsPhone() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"phone\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_PHONE
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'bio' field")
    public void testAuthMeApiLoggedInContainsBio() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"bio\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_BIO
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'avatarUrl' field")
    public void testAuthMeApiLoggedInContainsAvatarUrl() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"avatarUrl\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_AVATAR_URL
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'isHost' field")
    public void testAuthMeApiLoggedInContainsIsHost() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"isHost\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_IS_HOST
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'createdAt' field")
    public void testAuthMeApiLoggedInContainsCreatedAt() {
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"createdAt\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_CREATED_AT
        );
    }

    @Test
    @DisplayName("Auth/me API returns 401 when not logged in")
    public void testAuthMeApiLoggedOut() {
        ownProfilePage.navbar().clickLogoutAndWaitForRedirectToHome();
        long status = ownProfilePage.getAuthMeApiStatusLoggedOut();

        assertEquals(
                401L,
                status,
                ErrorMessages.API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }
}
