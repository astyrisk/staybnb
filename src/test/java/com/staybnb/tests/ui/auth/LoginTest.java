package com.staybnb.tests.ui.auth;

import com.staybnb.config.AppConstants;
import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.tests.BaseTest;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Authentication")
@Feature("Login")
@Tag("smoke")
public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        loginPage.navigateViaNavbar();
    }

    @Test
    @DisplayName("Successful login redirects to home page")
    public void testSuccessfulLoginRedirection() {
        loginPage.login(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);

        assertTrue(
                loginPage.urlContains(AppConstants.HOME_URL)
        );
    }

    @Test
    @DisplayName("Login with invalid credentials shows error message")
    public void testLoginWithInvalidCredentials() {
        loginPage.login("wrong@email.com", "wrong_password");
        String inlineError = loginPage.getInlineErrorMessageText();

        assertTrue(
                inlineError.toLowerCase().contains("invalid email or password"),
                ErrorMessages.EXPECTED_INVALID_CREDENTIALS_OR_UNAUTHORIZED
        );
    }

    @Test
    @DisplayName("Login with blank fields shows inline validation error")
    public void testLoginWithBlankFields() {
        loginPage.clickSubmit();
        String inlineError = loginPage.getInlineErrorMessageText();

        assertTrue(
                inlineError.toLowerCase().contains("email and password are required"),
                ErrorMessages.EXPECTED_INVALID_CREDENTIALS_OR_UNAUTHORIZED
        );
    }

    @Test
    @DisplayName("Login page has a 'Register' link that navigates to the register page")
    public void testLoginPageHasRegisterLink() {
        loginPage.clickRegisterLink();

        assertTrue(
                loginPage.urlContains(AppConstants.REGISTER_URL),
                ErrorMessages.LOGIN_PAGE_SHOULD_NAVIGATE_TO_REGISTER
        );
    }
}
