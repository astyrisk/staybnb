package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.OwnProfilePage;
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

@Epic("Host Management")
@Feature("Become a Host")
@Tag("regression")
public class BecomeHostTest extends BaseTest {
    private LoginPage loginPage;
    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        ownProfilePage = new OwnProfilePage(driver);
    }

    @Test
    @DisplayName("Navbar shows 'Become a Host' link for a non-host user")
    public void testNavbarShowsBecomeHostForNonHostUser() {
        registerNewUserAndLandOnHome("testhost");

        assertTrue(
                ownProfilePage.navbar().isBecomeAHostDisplayed(),
                ErrorMessages.NAVBAR_BECOME_HOST_SHOULD_BE_VISIBLE_FOR_GUEST_USER
        );
    }

    @Test
    @DisplayName("Navbar does not show 'My Properties' for a non-host user")
    public void testNavbarDoesNotShowMyPropertiesForNonHostUser() {
        registerNewUserAndLandOnHome("testhost");

        assertFalse(
                ownProfilePage.navbar().isMyPropertiesDisplayed(),
                ErrorMessages.NAVBAR_MY_PROPERTIES_SHOULD_NOT_BE_VISIBLE_FOR_NON_HOST_USER
        );
    }

    private void becomeHostAsNewUser() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navbar().clickBecomeAHost();
        driver.get(Constants.HOME_URL);
    }

    @Test
    @DisplayName("Navbar shows 'My Properties' after becoming a host")
    public void testNavbarShowsMyPropertiesAfterBecomingHost() {
        becomeHostAsNewUser();

        assertTrue(
                ownProfilePage.navbar().isMyPropertiesDisplayed(),
                ErrorMessages.NAVBAR_MY_PROPERTIES_SHOULD_BE_VISIBLE_FOR_HOST_USER
        );
    }

    @Test
    @DisplayName("Navbar does not show 'Become a Host' after becoming a host")
    public void testNavbarDoesNotShowBecomeHostAfterBecomingHost() {
        becomeHostAsNewUser();

        assertFalse(
                ownProfilePage.navbar().isBecomeAHostDisplayed(),
                ErrorMessages.NAVBAR_BECOME_HOST_SHOULD_NOT_BE_VISIBLE_FOR_HOST_USER
        );
    }

    @Test
    @DisplayName("'My Properties' link navigates to hosting dashboard")
    public void testMyPropertiesLinkNavigatesToHosting() {
        becomeHostAsNewUser();
        ownProfilePage.navbar().clickMyProperties();

        assertTrue(
                isUrlContains(Constants.HOSTING_URL),
                ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE
        );
    }

    @Test
    @DisplayName("Profile page shows 'Become a Host' button for non-host user")
    public void testProfileShowsBecomeHostButtonForNonHostUser() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navigateTo();

        assertTrue(
                ownProfilePage.isBecomeHostButtonVisible(),
                ErrorMessages.BECOME_HOST_BUTTON_SHOULD_BE_VISIBLE_ON_PROFILE_PAGE
        );
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

        assertTrue(
                isUrlContains(Constants.HOSTING_URL),
                ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE
        );
    }

    @Test
    @DisplayName("Become host API returns 401 when not logged in")
    public void testBecomeHostApiReturns401WhenLoggedOut() {
        long status = ownProfilePage.becomeHostStatusLoggedOut();

        assertEquals(
                401L,
                status,
                ErrorMessages.BECOME_HOST_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }

    @Test
    @DisplayName("Become host API response is not null")
    public void testBecomeHostApiResponseNotNull() {
        String email = registerNewUserAndLandOnHome("testhost");
        loginAsUserAndLandOnHome(loginPage, email, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.becomeHostViaApi("{\"isHost\":true}");

        assertNotNull(
                jsonResponse,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @Test
    @DisplayName("Become host API response reflects isHost true")
    public void testBecomeHostApiResponseReflectsIsHostTrue() {
        String email = registerNewUserAndLandOnHome("testhost");
        loginAsUserAndLandOnHome(loginPage, email, TestConfig.TEST_PASSWORD);
        String jsonResponse = ownProfilePage.becomeHostViaApi("{\"isHost\":true}");

        assertTrue(
                jsonResponse.contains("\"isHost\":true"),
                ErrorMessages.BECOME_HOST_API_SHOULD_REFLECT_IS_HOST_TRUE
        );
    }

    private static Stream<String> provideBecomeHostRedirectSources() {
        return Stream.of(
                "navbar",
                "profile page"
        );
    }
}
