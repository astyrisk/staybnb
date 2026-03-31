package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BecomeHostTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
    }

    @Test
    public void testNavbarShowsBecomeHostForNonHostUser() {
        registerNewUserAndLandOnHome("testhost");
        assertTrue(ownProfilePage.navbar().isBecomeAHostDisplayed(), ErrorMessages.NAVBAR_BECOME_HOST_SHOULD_BE_VISIBLE_FOR_GUEST_USER);
    }

    @Test
    public void testNavbarDoesNotShowMyPropertiesForNonHostUser() {
        registerNewUserAndLandOnHome("testhost");
        assertFalse(ownProfilePage.navbar().isMyPropertiesDisplayed(), ErrorMessages.NAVBAR_MY_PROPERTIES_SHOULD_NOT_BE_VISIBLE_FOR_NON_HOST_USER);
    }

    @Test
    public void testNavbarShowsMyPropertiesAfterBecomingHost() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navbar().clickBecomeAHost();
        driver.get(Constants.HOME_URL);
        assertTrue(ownProfilePage.navbar().isMyPropertiesDisplayed(), ErrorMessages.NAVBAR_MY_PROPERTIES_SHOULD_BE_VISIBLE_FOR_HOST_USER);
    }

    @Test
    public void testNavbarDoesNotShowBecomeHostAfterBecomingHost() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navbar().clickBecomeAHost();
        driver.get(Constants.HOME_URL);
        assertFalse(ownProfilePage.navbar().isBecomeAHostDisplayed(), ErrorMessages.NAVBAR_BECOME_HOST_SHOULD_NOT_BE_VISIBLE_FOR_HOST_USER);
    }

    @Test
    public void testMyPropertiesLinkNavigatesToHosting() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navbar().clickBecomeAHost();
        driver.get(Constants.HOME_URL);
        ownProfilePage.navbar().clickMyProperties();
        assertTrue(isUrlContains(Constants.HOSTING_URL), ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE);
    }

    @Test
    public void testProfileShowsBecomeHostButtonForNonHostUser() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navigateTo();
        assertTrue(ownProfilePage.isBecomeHostButtonVisible(),
                "Become a Host button should be visible on profile page for non-host.");
    }

    @ParameterizedTest(name = "Become host redirect from {0}")
    @MethodSource("provideBecomeHostRedirectSources")
    public void testBecomeHostRedirectsToHosting(String source) {
        registerNewUserAndLandOnHome("testhost");
        switch (source) {
            case "navbar" -> ownProfilePage.navbar().clickBecomeAHost();
            case "profile page" -> {
                ownProfilePage.navigateTo();
                ownProfilePage.clickBecomeHost();
            }
            default -> throw new IllegalArgumentException("Unsupported source: " + source);
        }
        assertTrue(isUrlContains(Constants.HOSTING_URL), ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE);
    }

    @Test
    public void testBecomeHostApiReturns401WhenLoggedOut() {
        long status = ownProfilePage.becomeHostStatusLoggedOut();
        assertEquals(401L, status, ErrorMessages.BECOME_HOST_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN);
    }

    @Test
    public void testBecomeHostApiResponseNotNull() {
        String email = registerNewUserAndLandOnHome("testhost");

        loginAsUserAndLandOnHome(loginPage, email, TestConfig.TEST_PASSWORD);

        String jsonResponse = ownProfilePage.becomeHostViaApi("{\"isHost\":true}");
        assertNotNull(jsonResponse, ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL);
    }

    @Test
    public void testBecomeHostApiResponseReflectsIsHostTrue() {
        String email = registerNewUserAndLandOnHome("testhost");

        loginAsUserAndLandOnHome(loginPage, email, TestConfig.TEST_PASSWORD);

        String jsonResponse = ownProfilePage.becomeHostViaApi("{\"isHost\":true}");
        assertTrue(jsonResponse.contains("\"isHost\":true"), ErrorMessages.BECOME_HOST_API_SHOULD_REFLECT_IS_HOST_TRUE);
    }

    private static Stream<String> provideBecomeHostRedirectSources() {
        return Stream.of(
                "navbar",
                "profile page"
        );
    }
}

