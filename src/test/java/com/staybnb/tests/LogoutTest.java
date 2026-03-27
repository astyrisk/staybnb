package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.utils.Constants;
import com.staybnb.utils.ErrorMessages;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.LogoutPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest extends BaseTest {
    private LoginPage loginPage;
    private LogoutPage logoutPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        logoutPage = new LogoutPage(driver);
        loginPage.load();
    }

    @Test
    public void testLogoutRedirectionToHomepage() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        logoutPage.logoutAndWaitForRedirectToHome();
        assertTrue(driver.getCurrentUrl().contains(Constants.HOME_URL),
                ErrorMessages.SHOULD_BE_REDIRECTED_TO_HOMEPAGE_AFTER_LOGOUT);
    }

    @Test
    public void testTokenPresentAfterLogin() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        String jwt = loginPage.getStaybnbToken();
        assertNotNull(jwt, ErrorMessages.JWT_TOKEN_SHOULD_EXIST_AFTER_LOGIN);
    }

    @Test
    public void testTokenRemovedAfterLogout() {
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
        logoutPage.logoutAndWaitForTokenCleared();
        String jwt = loginPage.getStaybnbToken();
        assertNull(jwt, ErrorMessages.JWT_TOKEN_SHOULD_BE_REMOVED_AFTER_LOGOUT);
    }
}
