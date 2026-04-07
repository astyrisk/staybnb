package com.staybnb.tests;

import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.LogoutPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Authentication")
@Feature("Logout")
@Tag("smoke")
public class LogoutTest extends BaseTest {
    private LoginPage loginPage;
    private LogoutPage logoutPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        logoutPage = new LogoutPage(driver);
    }

    @Test
    @DisplayName("Logout redirects to homepage")
    public void testLogoutRedirectionToHomepage() {
        loginAsTestUserAndLandOnHome(loginPage);
        logoutPage.logoutAndWaitForRedirectToHome();

        assertTrue(
                driver.getCurrentUrl().contains(Constants.HOME_URL),
                ErrorMessages.SHOULD_BE_REDIRECTED_TO_HOMEPAGE_AFTER_LOGOUT
        );
    }

    @Test
    @DisplayName("JWT token is present in localStorage after login")
    public void testTokenPresentAfterLogin() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jwt = loginPage.getStaybnbToken();

        assertNotNull(
                jwt,
                ErrorMessages.JWT_TOKEN_SHOULD_EXIST_AFTER_LOGIN
        );
    }

    @Test
    @DisplayName("JWT token is removed from localStorage after logout")
    public void testTokenRemovedAfterLogout() {
        loginAsTestUserAndLandOnHome(loginPage);
        logoutPage.logoutAndWaitForTokenCleared();
        String jwt = loginPage.getStaybnbToken();

        assertNull(
                jwt,
                ErrorMessages.JWT_TOKEN_SHOULD_BE_REMOVED_AFTER_LOGOUT
        );
    }
}
