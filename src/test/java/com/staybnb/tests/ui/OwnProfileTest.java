package com.staybnb.tests.ui;

import com.staybnb.config.AppConstants;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.data.Constants;
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
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
        ownProfilePage.navigateViaNavbar();
    }

    @Test
    @DisplayName("Own profile displays avatar")
    public void testOwnProfileAvatarDisplayed() {
        assertTrue(
                ownProfilePage.isAvatarDisplayed(),
                ErrorMessages.AVATAR_SHOULD_BE_DISPLAYED
        );
    }

    @Test
    @DisplayName("Own profile shows correct full name")
    public void testOwnProfileFullName() {
        assertEquals(
                Constants.OwnProfile.FULL_NAME,
                ownProfilePage.getFullName(),
                ErrorMessages.FULL_NAME_SHOULD_MATCH
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
                Constants.OwnProfile.PHONE,
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
        ownProfilePage.clickEditProfile();

        assertTrue(
                isUrlContains(AppConstants.EDIT_PROFILE_URL),
                ErrorMessages.SHOULD_NAVIGATE_TO_EDIT_PROFILE_PAGE
        );
    }
}
