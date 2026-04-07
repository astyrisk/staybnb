package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OtherProfilePage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Other User Profile")
@Tag("regression")
public class OtherProfileTest extends BaseTest {
    private LoginPage loginPage;
    private OtherProfilePage otherProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        otherProfilePage = new OtherProfilePage(driver);
    }

    private void loginAndNavigateToUser101() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_101);
    }

    private void loginAndNavigateToUser102() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.USER_ID_102);
    }

    @Test
    @DisplayName("Other user's profile displays an avatar")
    public void testOtherUserProfileAvatarDisplayed() {
        loginAndNavigateToUser101();
        assertTrue(otherProfilePage.isAvatarDisplayed(), ErrorMessages.AVATAR_SHOULD_BE_DISPLAYED);
    }

    @Test
    @DisplayName("Other user's avatar shows the user's first initial")
    public void testOtherUserProfileAvatarText() {
        loginAndNavigateToUser101();
        assertEquals("B", otherProfilePage.getAvatarText(), ErrorMessages.AVATAR_SHOULD_CONTAIN_USERS_FIRST_INITIAL);
    }

    @Test
    @DisplayName("Other user's profile shows first name and last initial")
    public void testOtherUserProfileName() {
        loginAndNavigateToUser101();
        assertEquals("Bob J.", otherProfilePage.getProfileName(), ErrorMessages.PROFILE_NAME_SHOULD_SHOW_FIRST_NAME_AND_LAST_INITIAL);
    }

    @Test
    @DisplayName("Other user's profile meta contains their role (Guest or Host)")
    public void testOtherUserProfileMetaContainsRole() {
        loginAndNavigateToUser101();
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Guest") || meta.contains("Host"), ErrorMessages.META_SHOULD_CONTAIN_USER_ROLE);
    }

    @Test
    @DisplayName("Other user's profile meta contains 'Member since'")
    public void testOtherUserProfileMetaContainsMemberSince() {
        loginAndNavigateToUser101();
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Member since"), ErrorMessages.META_SHOULD_CONTAIN_MEMBER_SINCE);
    }

    @Test
    @DisplayName("Other user's bio is displayed correctly")
    public void testOtherUserProfileBio() {
        loginAndNavigateToUser101();
        assertEquals("Adventure seeker and foodie.", otherProfilePage.getBio(), ErrorMessages.BIO_SHOULD_MATCH);
    }

    @Test
    @DisplayName("Other user's phone number is not visible on their profile")
    public void testOtherUserProfilePhoneNotVisible() {
        loginAndNavigateToUser102();
        assertFalse(otherProfilePage.isPhoneSectionVisible(), ErrorMessages.PHONE_NUMBER_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE);
    }

    @Test
    @DisplayName("Other user's email is not visible on their profile")
    public void testOtherUserProfileEmailNotVisible() {
        loginAndNavigateToUser102();
        assertFalse(otherProfilePage.isEmailSectionVisible(), ErrorMessages.EMAIL_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE);
    }

    @Test
    @DisplayName("Other user's name shows only first name and last initial")
    public void testOtherUserProfileNameMasked() {
        loginAndNavigateToUser102();
        String name = otherProfilePage.getProfileName();
        assertTrue(name.matches("^[A-Za-z]+ [A-Z]\\.$"), ErrorMessages.PROFILE_NAME_SHOULD_ONLY_SHOW_FIRST_NAME_AND_LAST_INITIAL);
    }

    @Test
    @DisplayName("Non-existent user profile shows 404")
    public void testNonExistentUserProfile() {
        loginAsTestUserAndLandOnHome(loginPage);
        otherProfilePage.navigateTo(Constants.NON_EXISTENT_ID);
        assertTrue(otherProfilePage.is404Displayed(), ErrorMessages.PAGE_SHOULD_INDICATE_404_FOR_NON_EXISTENT_USER);
    }

    @Test
    @DisplayName("View other user API response is not null")
    public void testApiViewOtherUserResponseNotNull() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);
        assertNotNull(jsonResponse, ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL);
    }

    @ParameterizedTest(name = "API response contains field: {0}")
    @MethodSource("provideExpectedApiFields")
    public void testApiViewOtherUserContainsField(String field) {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);

        assertTrue(
                jsonResponse.contains("\"" + field + "\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_EXPECTED_FIELD
        );
    }

    @ParameterizedTest(name = "API response excludes field: {0}")
    @MethodSource("provideExcludedApiFields")
    public void testApiViewOtherUserDoesNotContainField(String field) {
        loginAsTestUserAndLandOnHome(loginPage);
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(Constants.USER_ID_101);

        assertFalse(
                jsonResponse.contains("\"" + field + "\""),
                ErrorMessages.RESPONSE_SHOULD_NOT_CONTAIN_EXCLUDED_FIELD
        );
    }

    private static Stream<String> provideExpectedApiFields() {
        return Stream.of("firstName", "lastName", "bio", "isHost");
    }

    private static Stream<String> provideExcludedApiFields() {
        return Stream.of("email", "phone");
    }
}
