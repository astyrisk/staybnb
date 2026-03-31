package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OtherProfilePage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OtherProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OtherProfilePage otherProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        otherProfilePage = new OtherProfilePage(driver);
    }

    @Test
    public void testOtherUserProfileAvatarDisplayed() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_101);
        assertTrue(otherProfilePage.isAvatarDisplayed(), ErrorMessages.AVATAR_SHOULD_BE_DISPLAYED);
    }

    @Test
    public void testOtherUserProfileAvatarText() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_101);
        assertEquals("B", otherProfilePage.getAvatarText(), ErrorMessages.AVATAR_SHOULD_CONTAIN_USERS_FIRST_INITIAL);
    }

    @Test
    public void testOtherUserProfileName() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_101);
        assertEquals("Bob J.", otherProfilePage.getProfileName(), ErrorMessages.PROFILE_NAME_SHOULD_SHOW_FIRST_NAME_AND_LAST_INITIAL);
    }

    @Test
    public void testOtherUserProfileMetaContainsRole() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_101);
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Guest") || meta.contains("Host"), ErrorMessages.META_SHOULD_CONTAIN_USER_ROLE);
    }

    @Test
    public void testOtherUserProfileMetaContainsMemberSince() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_101);
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Member since"), ErrorMessages.META_SHOULD_CONTAIN_MEMBER_SINCE);
    }

    @Test
    public void testOtherUserProfileBio() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_101);
        assertEquals("Adventure seeker and foodie.", otherProfilePage.getBio(), ErrorMessages.BIO_SHOULD_MATCH);
    }

    @Test
    public void testOtherUserProfilePhoneNotVisible() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_102);
        assertFalse(otherProfilePage.isPhoneSectionVisible(), ErrorMessages.PHONE_NUMBER_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE);
    }

    @Test
    public void testOtherUserProfileEmailNotVisible() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_102);
        assertFalse(otherProfilePage.isEmailSectionVisible(), ErrorMessages.EMAIL_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE);
    }

    @Test
    public void testOtherUserProfileNameMasked() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_102);
        String name = otherProfilePage.getProfileName();
        assertTrue(name.matches("^[A-Za-z]+ [A-Z]\\.$"), ErrorMessages.PROFILE_NAME_SHOULD_ONLY_SHOW_FIRST_NAME_AND_LAST_INITIAL);
    }

    @Test
    public void testNonExistentUserProfile() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.NON_EXISTENT_ID);
        assertTrue(otherProfilePage.is404Displayed(), ErrorMessages.PAGE_SHOULD_INDICATE_404_FOR_NON_EXISTENT_USER);
    }

    @Test
    public void testApiViewOtherUserResponseNotNull() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);
        assertNotNull(jsonResponse, ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL);
    }

    @Test
    public void testApiViewOtherUserContainsFirstName() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);
        assertTrue(jsonResponse.contains("\"firstName\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_FIRST_NAME);
    }

    @Test
    public void testApiViewOtherUserContainsLastName() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);
        assertTrue(jsonResponse.contains("\"lastName\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_LAST_NAME);
    }

    @Test
    public void testApiViewOtherUserContainsBio() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);
        assertTrue(jsonResponse.contains("\"bio\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_BIO);
    }

    @Test
    public void testApiViewOtherUserContainsIsHost() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);
        assertTrue(jsonResponse.contains("\"isHost\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_IS_HOST);
    }

    @Test
    public void testApiViewOtherUserDoesNotContainEmail() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);
        assertFalse(jsonResponse.contains("\"email\""), ErrorMessages.RESPONSE_SHOULD_NOT_CONTAIN_EMAIL);
    }

    @Test
    public void testApiViewOtherUserDoesNotContainPhone() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);
        assertFalse(jsonResponse.contains("\"phone\""), ErrorMessages.RESPONSE_SHOULD_NOT_CONTAIN_PHONE);
    }
}
