package com.staybnb.tests.ui.profile;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OtherProfilePage;
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
    private OtherProfilePage otherProfilePage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        otherProfilePage = new OtherProfilePage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private void navigateToUser101() {
        otherProfilePage.navigateTo(TestDataConstants.OTHER_USER_ID_1);
    }

    private void navigateToUser102() {
        otherProfilePage.navigateTo(TestDataConstants.OTHER_USER_ID_2);
    }

    @Test
    @DisplayName("Other user's profile displays an avatar")
    public void testOtherUserProfileAvatarDisplayed() {
        navigateToUser101();
        assertTrue(otherProfilePage.isAvatarDisplayed(), ErrorMessages.AVATAR_SHOULD_BE_DISPLAYED);
    }

    @Test
    @DisplayName("Other user's avatar shows the user's first initial")
    public void testOtherUserProfileAvatarText() {
        navigateToUser101();
        assertEquals("F", otherProfilePage.getAvatarText(), ErrorMessages.AVATAR_SHOULD_CONTAIN_USERS_FIRST_INITIAL);
    }

    @Test
    @DisplayName("Other user's profile shows first name and last initial")
    public void testOtherUserProfileName() {
        navigateToUser101();
        assertEquals("Foo B.", otherProfilePage.getProfileName(), ErrorMessages.PROFILE_NAME_SHOULD_SHOW_FIRST_NAME_AND_LAST_INITIAL);
    }

    @Test
    @DisplayName("Other user's profile meta contains their role (Guest or Host)")
    public void testOtherUserProfileMetaContainsRole() {
        navigateToUser101();
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Guest") || meta.contains("Host"), ErrorMessages.META_SHOULD_CONTAIN_USER_ROLE);
    }

    @Test
    @DisplayName("Other user's profile meta contains 'Member since'")
    public void testOtherUserProfileMetaContainsMemberSince() {
        navigateToUser101();
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Member since"), ErrorMessages.META_SHOULD_CONTAIN_MEMBER_SINCE);
    }

    @Test
    @DisplayName("Other user's bio is displayed correctly")
    public void testOtherUserProfileBio() {
        navigateToUser101();
        assertEquals("Adventure seeker and foodie.", otherProfilePage.getBio(), ErrorMessages.BIO_SHOULD_MATCH);
    }

    @Test
    @DisplayName("Other user's phone number is not visible on their profile")
    public void testOtherUserProfilePhoneNotVisible() {
        navigateToUser102();
        assertFalse(otherProfilePage.isPhoneSectionVisible(), ErrorMessages.PHONE_NUMBER_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE);
    }

    @Test
    @DisplayName("Other user's email is not visible on their profile")
    public void testOtherUserProfileEmailNotVisible() {
        navigateToUser102();
        assertFalse(otherProfilePage.isEmailSectionVisible(), ErrorMessages.EMAIL_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE);
    }

    @Test
    @DisplayName("Other user's name shows only first name and last initial")
    public void testOtherUserProfileNameMasked() {
        navigateToUser102();
        String name = otherProfilePage.getProfileName();
        assertTrue(name.matches("^[A-Za-z]+ [A-Za-z]\\.$"), ErrorMessages.PROFILE_NAME_SHOULD_ONLY_SHOW_FIRST_NAME_AND_LAST_INITIAL);
    }

    @Test
    @DisplayName("Non-existent user profile shows 404")
    public void testNonExistentUserProfile() {
        otherProfilePage.navigateTo(TestDataConstants.NON_EXISTENT_ID);
        assertTrue(otherProfilePage.is404Displayed(), ErrorMessages.PAGE_SHOULD_INDICATE_404_FOR_NON_EXISTENT_USER);
    }
}
