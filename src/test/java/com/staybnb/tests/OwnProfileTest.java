package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Own Profile")
@Tag("regression")
public class OwnProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
    }

    @Test
    @DisplayName("Own profile displays avatar")
    public void testOwnProfileAvatarDisplayed() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertTrue(
                ownProfilePage.isAvatarDisplayed(),
                ErrorMessages.AVATAR_SHOULD_BE_DISPLAYED
        );
    }

    @Test
    @DisplayName("Own profile shows correct full name")
    public void testOwnProfileFullName() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertEquals(
                Constants.OwnProfile.FULL_NAME,
                ownProfilePage.getFullName(),
                ErrorMessages.FULL_NAME_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Own profile meta contains 'Member since'")
    public void testOwnProfileMetaContainsMemberSince() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertTrue(
                ownProfilePage.getProfileMeta().contains("Member since"),
                ErrorMessages.PROFILE_META_SHOULD_CONTAIN_MEMBER_SINCE
        );
    }

    @Test
    @DisplayName("Own profile bio is not empty")
    public void testOwnProfileBioNotEmpty() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertFalse(
                ownProfilePage.getBio().isEmpty(),
                ErrorMessages.BIO_SHOULD_NOT_BE_EMPTY
        );
    }

    @Test
    @DisplayName("Own profile shows correct phone number")
    public void testOwnProfilePhone() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertEquals(
                Constants.OwnProfile.PHONE,
                ownProfilePage.getPhone(),
                ErrorMessages.PHONE_NUMBER_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Own profile shows Edit Profile button")
    public void testOwnProfileEditProfileButtonVisible() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertTrue(
                ownProfilePage.isEditProfileButtonVisible(),
                ErrorMessages.EDIT_PROFILE_BUTTON_SHOULD_BE_VISIBLE
        );
    }

    @Test
    @DisplayName("Clicking Edit Profile button navigates to edit profile page")
    public void testOwnProfileEditProfileButtonNavigation() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();
        ownProfilePage.clickEditProfile();

        assertTrue(
                driver.getCurrentUrl().contains(Constants.EDIT_PROFILE_URL),
                ErrorMessages.SHOULD_NAVIGATE_TO_EDIT_PROFILE_PAGE
        );
    }

    @Test
    @DisplayName("Auth/me API response is not null when logged in")
    public void testAuthMeApiLoggedInResponseNotNull() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertNotNull(
                jsonResponse,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'id' field")
    public void testAuthMeApiLoggedInContainsId() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"id\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_ID
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'email' field")
    public void testAuthMeApiLoggedInContainsEmail() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"email\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_EMAIL
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'firstName' field")
    public void testAuthMeApiLoggedInContainsFirstName() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"firstName\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_FIRST_NAME
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'lastName' field")
    public void testAuthMeApiLoggedInContainsLastName() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(
                jsonResponse.contains("\"lastName\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_LAST_NAME
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'phone' field")
    public void testAuthMeApiLoggedInContainsPhone() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"phone\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_PHONE
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'bio' field")
    public void testAuthMeApiLoggedInContainsBio() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"bio\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_BIO
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'avatarUrl' field")
    public void testAuthMeApiLoggedInContainsAvatarUrl() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"avatarUrl\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_AVATAR_URL
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'isHost' field")
    public void testAuthMeApiLoggedInContainsIsHost() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(
                jsonResponse.contains("\"isHost\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_IS_HOST
        );
    }

    @Test
    @DisplayName("Auth/me API response contains 'createdAt' field")
    public void testAuthMeApiLoggedInContainsCreatedAt() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"createdAt\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_CREATED_AT
        );
    }

    @Test
    @DisplayName("Auth/me API returns 401 when not logged in")
    public void testAuthMeApiLoggedOut() {
        long status = ownProfilePage.getAuthMeApiStatusLoggedOut();

        assertEquals(
                401L,
                status,
                ErrorMessages.API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }
}
