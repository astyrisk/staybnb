package com.staybnb.tests.ui.profile;

import com.staybnb.pages.ProfilePage;
import com.staybnb.config.TestDataConstants;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Other User Profile")
@Tag("regression")
public class OtherProfileTest extends BaseTest {
    private ProfilePage otherProfilePage;

    @BeforeEach
    public void setup() {
        otherProfilePage = new ProfilePage(driver);
        loginAsUser();
        otherProfilePage.navigateTo(TestDataConstants.OtherProfile.USER_ID);
    }

    @Test
    @DisplayName("Other user's profile displays the correct avatar")
    public void testOtherUserProfileAvatarSrc() {
        assertEquals(
                TestDataConstants.OtherProfile.AVATAR_SRC,
                otherProfilePage.getAvatarSrc(),
                ErrorMessages.AVATAR_URL_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Other user's profile shows correct name")
    public void testOtherUserProfileName() {
        assertEquals(
                TestDataConstants.OtherProfile.NAME,
                otherProfilePage.getFullName(),
                ErrorMessages.PROFILE_NAME_SHOULD_SHOW_FIRST_NAME_AND_LAST_INITIAL
        );
    }

    @Test
    @DisplayName("Other user's profile meta contains their role")
    public void testOtherUserProfileMetaContainsRole() {
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(
                meta.contains("Guest") || meta.contains("Host"),
                ErrorMessages.META_SHOULD_CONTAIN_USER_ROLE
        );
    }

    @Test
    @DisplayName("Other user's profile meta contains 'Member since'")
    public void testOtherUserProfileMetaContainsMemberSince() {
        assertTrue(
                otherProfilePage.getProfileMeta().contains("Member since"),
                ErrorMessages.META_SHOULD_CONTAIN_MEMBER_SINCE
        );
    }

    @Test
    @DisplayName("Other user's bio is displayed correctly")
    public void testOtherUserProfileBio() {
        assertEquals(
                TestDataConstants.OtherProfile.BIO,
                otherProfilePage.getBio(),
                ErrorMessages.BIO_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Other user's phone number is not visible on their profile")
    public void testOtherUserProfilePhoneNotVisible() {
        assertFalse(
                otherProfilePage.isPhoneSectionVisible(),
                ErrorMessages.PHONE_NUMBER_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE
        );
    }

    @Test
    @DisplayName("Other user's email is not visible on their profile")
    public void testOtherUserProfileEmailNotVisible() {
        assertFalse(
                otherProfilePage.isEmailSectionVisible(),
                ErrorMessages.EMAIL_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE
        );
    }

    @Test
    @DisplayName("Non-existent user profile shows 404")
    public void testNonExistentUserProfile() {
        otherProfilePage.navigateTo(TestDataConstants.NON_EXISTENT_ID);
        assertTrue(
                otherProfilePage.is404Displayed(),
                ErrorMessages.PAGE_SHOULD_INDICATE_404_FOR_NON_EXISTENT_USER
        );
    }
}
