package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.utils.Constants;
import com.staybnb.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OwnProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        loginPage.load();
        ownProfilePage = new OwnProfilePage(driver);
    }

    @Test
    public void testOwnProfileAvatarDisplayed() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        ownProfilePage.load();
        assertTrue(ownProfilePage.isAvatarDisplayed(), ErrorMessages.AVATAR_SHOULD_BE_DISPLAYED);
    }

    @Test
    public void testOwnProfileFullName() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        ownProfilePage.load();
        assertEquals(Constants.OwnProfile.FULL_NAME, ownProfilePage.getFullName(), ErrorMessages.FULL_NAME_SHOULD_MATCH);
    }

    @Test
    public void testOwnProfileMetaContainsMemberSince() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        ownProfilePage.load();
        assertTrue(ownProfilePage.getProfileMeta().contains("Member since"), ErrorMessages.PROFILE_META_SHOULD_CONTAIN_MEMBER_SINCE);
    }

    @Test
    public void testOwnProfileBioNotEmpty() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        ownProfilePage.load();
        assertFalse(ownProfilePage.getBio().isEmpty(), ErrorMessages.BIO_SHOULD_NOT_BE_EMPTY);
    }

    @Test
    public void testOwnProfilePhone() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        ownProfilePage.load();
        assertEquals(Constants.OwnProfile.PHONE, ownProfilePage.getPhone(), ErrorMessages.PHONE_NUMBER_SHOULD_MATCH);
    }

    @Test
    public void testOwnProfileEditProfileButtonVisible() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        ownProfilePage.load();
        assertTrue(ownProfilePage.isEditProfileButtonVisible(), ErrorMessages.EDIT_PROFILE_BUTTON_SHOULD_BE_VISIBLE);
    }

    @Test
    public void testOwnProfileEditProfileButtonNavigation() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        ownProfilePage.load();
        ownProfilePage.clickEditProfile();
        assertTrue(driver.getCurrentUrl().contains(Constants.EDIT_PROFILE_URL), ErrorMessages.SHOULD_NAVIGATE_TO_EDIT_PROFILE_PAGE);
    }

    @Test
    public void testAuthMeApiLoggedInResponseNotNull() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertNotNull(jsonResponse, ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL);
    }

    @Test
    public void testAuthMeApiLoggedInContainsId() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"id\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_ID);
    }

    @Test
    public void testAuthMeApiLoggedInContainsEmail() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"email\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_EMAIL);
    }

    @Test
    public void testAuthMeApiLoggedInContainsFirstName() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"firstName\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_FIRST_NAME);
    }

    @Test
    public void testAuthMeApiLoggedInContainsLastName() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"lastName\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_LAST_NAME);
    }

    @Test
    public void testAuthMeApiLoggedInContainsPhone() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"phone\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_PHONE);
    }

    @Test
    public void testAuthMeApiLoggedInContainsBio() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"bio\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_BIO);
    }

    @Test
    public void testAuthMeApiLoggedInContainsAvatarUrl() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"avatarUrl\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_AVATAR_URL);
    }

    @Test
    public void testAuthMeApiLoggedInContainsIsHost() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"isHost\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_IS_HOST);
    }

    @Test
    public void testAuthMeApiLoggedInContainsCreatedAt() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.getAuthMeApiResponse();
        assertTrue(jsonResponse.contains("\"createdAt\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_CREATED_AT);
    }

    @Test
    public void testAuthMeApiLoggedOut() {
        long status = ownProfilePage.getAuthMeApiStatusLoggedOut();
        assertEquals(401L, status, ErrorMessages.API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN);
    }
}
