package com.staybnb.tests.ui.auth;

import com.staybnb.config.AppConstants;
import com.staybnb.config.TestConfig;
import com.staybnb.pages.RegisterPage;
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
@Feature("Registration")
@Tag("smoke")
public class RegisterTest extends BaseTest {
    private RegisterPage registerPage;

    @BeforeEach
    public void setup() {
        registerPage = new RegisterPage(driver);
        registerPage.navigateViaNavbar();
    }

    @Test
    @DisplayName("Successful registration redirects to home page")
    public void testSuccessfulRegistration() {
        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@gmail.com";

        registerPage.registerAndWaitForUrl(
                TestConfig.TEST_FIRST_NAME,
                TestConfig.TEST_LAST_NAME,
                uniqueEmail,
                TestConfig.TEST_PASSWORD,
                AppConstants.HOME_URL
        );

        assertTrue(
                registerPage.urlContains(AppConstants.HOME_URL)
        );
    }

    @Test
    @DisplayName("Registration with an already registered email shows error")
    public void testRegistrationWithExistingEmail() {
        registerPage.submitRegistration(
                TestConfig.TEST_FIRST_NAME,
                TestConfig.TEST_LAST_NAME,
                TestConfig.TEST_USER_EMAIL,
                TestConfig.TEST_PASSWORD
        );

        String error = registerPage.getGlobalErrorMessageText();

        assertTrue(
                error.toLowerCase().contains("exists") || error.toLowerCase().contains("already"),
                ErrorMessages.EXPECTED_EMAIL_ALREADY_EXISTS
        );
    }

    @Test
    @DisplayName("Blank registration fields shows inline validation error")
    public void testRegistrationBlankFields() {
        registerPage.clickRegister();

        assertTrue(
                registerPage.isInlineErrorDisplayed(ErrorMessages.REQUIRED)
        );
    }

    @Test
    @DisplayName("Registration with a password shorter than 8 characters shows inline validation error")
    public void testRegistrationWithShortPassword() {
        registerPage.submitRegistrationWithShortPassword(
                TestConfig.TEST_FIRST_NAME,
                TestConfig.TEST_LAST_NAME,
                "shortpass_" + System.currentTimeMillis() + "@gmail.com"
        );

        assertTrue(
                registerPage.isInlineErrorDisplayed("8"),
                ErrorMessages.PASSWORD_TOO_SHORT
        );
    }

    @Test
    @DisplayName("Registration with mismatched passwords shows inline validation error")
    public void testRegistrationWithMismatchedPasswords() {
        registerPage.submitRegistrationWithMismatchedPasswords(
                TestConfig.TEST_FIRST_NAME,
                TestConfig.TEST_LAST_NAME,
                "mismatch_" + System.currentTimeMillis() + "@gmail.com",
                TestConfig.TEST_PASSWORD
        );

        assertTrue(
                registerPage.isInlineErrorDisplayed("match"),
                ErrorMessages.PASSWORDS_DO_NOT_MATCH
        );
    }

    @Test
    @DisplayName("Register page has a 'Log in' link that navigates to the login page")
    public void testRegisterPageHasLoginLink() {
        registerPage.clickLoginLink();

        assertTrue(
                registerPage.urlContains(AppConstants.LOGIN_URL),
                ErrorMessages.REGISTER_PAGE_SHOULD_NAVIGATE_TO_LOGIN
        );
    }
}
