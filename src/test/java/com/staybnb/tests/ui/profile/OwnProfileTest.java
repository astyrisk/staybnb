package com.staybnb.tests.ui.profile;

import com.staybnb.config.AppConstants;
import com.staybnb.config.TestDataConstants;
import com.staybnb.pages.EditProfilePage;
import com.staybnb.pages.ProfilePage;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Own Profile")
@Tag("regression")
@ResourceLock(value = "test-user-profile", mode = ResourceAccessMode.READ)
public class OwnProfileTest extends BaseTest {
    private ProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        ownProfilePage = new ProfilePage(driver);
        loginAsUser();
        ownProfilePage.navigateViaNavbar();
    }

    @Test
    @DisplayName("Own profile displays correct avatar")
    public void testOwnProfileAvatarDisplayed() {
        assertEquals(
                TestDataConstants.OwnProfile.AVATAR_URL,
                ownProfilePage.getAvatarSrc(),
                ErrorMessages.AVATAR_URL_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Own profile shows correct first name")
    public void testOwnProfileFirstName() {
        assertEquals(
                TestDataConstants.OwnProfile.FIRST_NAME,
                ownProfilePage.getFirstName(),
                ErrorMessages.FIRST_NAME_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Own profile shows correct last name")
    public void testOwnProfileLastName() {
        assertEquals(
                TestDataConstants.OwnProfile.LAST_NAME,
                ownProfilePage.getLastName(),
                ErrorMessages.LAST_NAME_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Own profile meta contains 'Member since'")
    public void testOwnProfileMetaContainsMemberSince() {
        assertTrue(
                ownProfilePage.getProfileMeta().contains("Member since"),
                ErrorMessages.PROFILE_META_SHOULD_CONTAIN_MEMBER_SINCE
        );
    }

    @Test
    @DisplayName("Own profile bio is not empty")
    public void testOwnProfileBioNotEmpty() {
        assertFalse(
                ownProfilePage.getBio().isEmpty(),
                ErrorMessages.BIO_SHOULD_NOT_BE_EMPTY
        );
    }

    @Test
    @DisplayName("Own profile shows correct phone number")
    public void testOwnProfilePhone() {
        assertEquals(
                TestDataConstants.OwnProfile.PHONE,
                ownProfilePage.getPhone(),
                ErrorMessages.PHONE_NUMBER_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Own profile shows Edit Profile button")
    public void testOwnProfileEditProfileButtonVisible() {
        assertTrue(
                ownProfilePage.isEditProfileButtonVisible(),
                ErrorMessages.EDIT_PROFILE_BUTTON_SHOULD_BE_VISIBLE
        );
    }

    @Test
    @DisplayName("Clicking Edit Profile button navigates to edit profile page")
    public void testOwnProfileEditProfileButtonNavigation() {
        EditProfilePage editProfilePage = ownProfilePage.clickEditProfile();

        assertTrue(
                editProfilePage.urlContains(AppConstants.EDIT_PROFILE_URL),
                ErrorMessages.SHOULD_NAVIGATE_TO_EDIT_PROFILE_PAGE
        );
    }
}
