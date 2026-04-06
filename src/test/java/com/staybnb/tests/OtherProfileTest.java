package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OtherProfilePage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testOtherUserProfileAvatarDisplayed() {
        loginAndNavigateToUser101();
        assertTrue(otherProfilePage.isAvatarDisplayed(), ErrorMessages.AVATAR_SHOULD_BE_DISPLAYED);
    }

    @Test
    public void testOtherUserProfileAvatarText() {
        loginAndNavigateToUser101();
        assertEquals("B", otherProfilePage.getAvatarText(), ErrorMessages.AVATAR_SHOULD_CONTAIN_USERS_FIRST_INITIAL);
    }

    @Test
    public void testOtherUserProfileName() {
        loginAndNavigateToUser101();
        assertEquals("Bob J.", otherProfilePage.getProfileName(), ErrorMessages.PROFILE_NAME_SHOULD_SHOW_FIRST_NAME_AND_LAST_INITIAL);
    }

    @Test
    public void testOtherUserProfileMetaContainsRole() {
        loginAndNavigateToUser101();
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Guest") || meta.contains("Host"), ErrorMessages.META_SHOULD_CONTAIN_USER_ROLE);
    }

    @Test
    public void testOtherUserProfileMetaContainsMemberSince() {
        loginAndNavigateToUser101();
        String meta = otherProfilePage.getProfileMeta();
        assertTrue(meta.contains("Member since"), ErrorMessages.META_SHOULD_CONTAIN_MEMBER_SINCE);
    }

    @Test
    public void testOtherUserProfileBio() {
        loginAndNavigateToUser101();
        assertEquals("Adventure seeker and foodie.", otherProfilePage.getBio(), ErrorMessages.BIO_SHOULD_MATCH);
    }

    @Test
    public void testOtherUserProfilePhoneNotVisible() {
        loginAndNavigateToUser102();
        assertFalse(otherProfilePage.isPhoneSectionVisible(), ErrorMessages.PHONE_NUMBER_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE);
    }

    @Test
    public void testOtherUserProfileEmailNotVisible() {
        loginAndNavigateToUser102();
        assertFalse(otherProfilePage.isEmailSectionVisible(), ErrorMessages.EMAIL_SHOULD_NOT_BE_VISIBLE_ON_OTHERS_PROFILE);
    }

    @Test
    public void testOtherUserProfileNameMasked() {
        loginAndNavigateToUser102();
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
