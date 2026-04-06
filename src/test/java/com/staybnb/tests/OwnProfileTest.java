package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OwnProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
    }

    @Test
    public void testOwnProfileAvatarDisplayed() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertTrue(
                ownProfilePage.isAvatarDisplayed(),
                ErrorMessages.AVATAR_SHOULD_BE_DISPLAYED
        );
    }

    @Test
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
    public void testOwnProfileMetaContainsMemberSince() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertTrue(
                ownProfilePage.getProfileMeta().contains("Member since"),
                ErrorMessages.PROFILE_META_SHOULD_CONTAIN_MEMBER_SINCE
        );
    }

    @Test
    public void testOwnProfileBioNotEmpty() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertFalse(
                ownProfilePage.getBio().isEmpty(),
                ErrorMessages.BIO_SHOULD_NOT_BE_EMPTY
        );
    }

    @Test
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
    public void testOwnProfileEditProfileButtonVisible() {
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateTo();

        assertTrue(
                ownProfilePage.isEditProfileButtonVisible(),
                ErrorMessages.EDIT_PROFILE_BUTTON_SHOULD_BE_VISIBLE
        );
    }

    @Test
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
    public void testAuthMeApiLoggedInResponseNotNull() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertNotNull(
                jsonResponse,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @Test
    public void testAuthMeApiLoggedInContainsId() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"id\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_ID
        );
    }

    @Test
    public void testAuthMeApiLoggedInContainsEmail() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"email\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_EMAIL
        );
    }

    @Test
    public void testAuthMeApiLoggedInContainsFirstName() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"firstName\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_FIRST_NAME
        );
    }

    @Test
    public void testAuthMeApiLoggedInContainsLastName() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(
                jsonResponse.contains("\"lastName\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_LAST_NAME
        );
    }

    @Test
    public void testAuthMeApiLoggedInContainsPhone() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"phone\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_PHONE
        );
    }

    @Test
    public void testAuthMeApiLoggedInContainsBio() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"bio\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_BIO
        );
    }

    @Test
    public void testAuthMeApiLoggedInContainsAvatarUrl() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"avatarUrl\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_AVATAR_URL
        );
    }

    @Test
    public void testAuthMeApiLoggedInContainsIsHost() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(
                jsonResponse.contains("\"isHost\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_IS_HOST
        );
    }

    @Test
    public void testAuthMeApiLoggedInContainsCreatedAt() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();

        assertTrue(
                jsonResponse.contains("\"createdAt\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_CREATED_AT
        );
    }

    @Test
    public void testAuthMeApiLoggedOut() {
        long status = ownProfilePage.getAuthMeApiStatusLoggedOut();

        assertEquals(
                401L,
                status,
                ErrorMessages.API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }
}
