package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.Navbar;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.pages.RegisterPage;
import com.staybnb.utils.Constants;
import com.staybnb.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BecomeHostTest extends BaseTest {
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private Navbar navbar;
    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        navbar = new Navbar(driver);
        ownProfilePage = new OwnProfilePage(driver);
    }

    private String registerNewUserAndLandOnHome() {
        registerPage.load();
        String uniqueEmail = "testhost_" + System.currentTimeMillis() + "@gmail.com";
        registerPage.registerAndWaitForUrl(
                TestConfig.TEST_FIRST_NAME,
                TestConfig.TEST_LAST_NAME,
                uniqueEmail,
                TestConfig.TEST_PASSWORD,
                Constants.HOME_URL
        );
        return uniqueEmail;
    }

    @Test
    public void testNavbarShowsBecomeHostForNonHostUser() {
        registerNewUserAndLandOnHome();
        assertTrue(navbar.isBecomeAHostDisplayed(), ErrorMessages.NAVBAR_BECOME_HOST_SHOULD_BE_VISIBLE_FOR_GUEST_USER);
    }

    @Test
    public void testNavbarDoesNotShowMyPropertiesForNonHostUser() {
        registerNewUserAndLandOnHome();
        assertFalse(navbar.isMyPropertiesDisplayed(), ErrorMessages.NAVBAR_MY_PROPERTIES_SHOULD_NOT_BE_VISIBLE_FOR_NON_HOST_USER);
    }

    @Test
    public void testBecomeHostFromNavbarRedirectsToHosting() {
        registerNewUserAndLandOnHome();
        navbar.clickBecomeAHost();
        assertTrue(isUrlContains(Constants.HOSTING_URL), ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE);
    }

    @Test
    public void testNavbarShowsMyPropertiesAfterBecomingHost() {
        registerNewUserAndLandOnHome();
        navbar.clickBecomeAHost();
        driver.get(Constants.HOME_URL);
        assertTrue(navbar.isMyPropertiesDisplayed(), ErrorMessages.NAVBAR_MY_PROPERTIES_SHOULD_BE_VISIBLE_FOR_HOST_USER);
    }

    @Test
    public void testNavbarDoesNotShowBecomeHostAfterBecomingHost() {
        registerNewUserAndLandOnHome();
        navbar.clickBecomeAHost();
        driver.get(Constants.HOME_URL);
        assertFalse(navbar.isBecomeAHostDisplayed(), ErrorMessages.NAVBAR_BECOME_HOST_SHOULD_NOT_BE_VISIBLE_FOR_HOST_USER);
    }

    @Test
    public void testMyPropertiesLinkNavigatesToHosting() {
        registerNewUserAndLandOnHome();
        navbar.clickBecomeAHost();
        driver.get(Constants.HOME_URL);
        navbar.clickMyProperties();
        assertTrue(isUrlContains(Constants.HOSTING_URL), ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE);
    }

    @Test
    public void testProfileShowsBecomeHostButtonForNonHostUser() {
        registerNewUserAndLandOnHome();
        ownProfilePage.load();
        assertTrue(ownProfilePage.isBecomeHostButtonVisible(),
                "Become a Host button should be visible on profile page for non-host.");
    }

    @Test
    public void testBecomeHostFromProfileRedirectsToHosting() {
        registerNewUserAndLandOnHome();
        ownProfilePage.load();
        ownProfilePage.clickBecomeHost();
        assertTrue(isUrlContains(Constants.HOSTING_URL), ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE);
    }

    @Test
    public void testBecomeHostApiReturns401WhenLoggedOut() {
        long status = ownProfilePage.becomeHostStatusLoggedOut();
        assertEquals(401L, status, ErrorMessages.BECOME_HOST_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN);
    }

    @Test
    public void testBecomeHostApiResponseNotNull() {
        String email = registerNewUserAndLandOnHome();

        // Defensive: ensure session is authenticated for the token-based API helper.
        driver.get(Constants.LOGIN_URL);
        loginPage.loginAndExpectSuccess(email, TestConfig.TEST_PASSWORD);

        String jsonResponse = ownProfilePage.becomeHostViaApi("{\"isHost\":true}");
        assertNotNull(jsonResponse, ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL);
    }

    @Test
    public void testBecomeHostApiResponseReflectsIsHostTrue() {
        String email = registerNewUserAndLandOnHome();

        driver.get(Constants.LOGIN_URL);
        loginPage.loginAndExpectSuccess(email, TestConfig.TEST_PASSWORD);

        String jsonResponse = ownProfilePage.becomeHostViaApi("{\"isHost\":true}");
        assertTrue(jsonResponse.contains("\"isHost\":true"), ErrorMessages.BECOME_HOST_API_SHOULD_REFLECT_IS_HOST_TRUE);
    }
}

