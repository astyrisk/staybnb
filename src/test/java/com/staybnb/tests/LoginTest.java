package com.staybnb.tests;

import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.LoginPage;
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
    }

    @Test
    @DisplayName("Successful login redirects to home page")
    public void testSuccessfulLoginRedirection() {
        loginAsTestUserAndLandOnHome(loginPage);

        assertTrue(
                driver.getCurrentUrl().contains(Constants.HOME_URL)
        );
    }

    @Test
    @DisplayName("Successful login stores JWT token in localStorage")
    public void testSuccessfulLoginTokenRetrieved() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jwt = loginPage.getStaybnbToken();

        assertNotNull(
                jwt,
                ErrorMessages.JWT_TOKEN_NOT_RETRIEVED
        );
    }

    @Test
    @DisplayName("Invalid credentials shows error message")
    public void testInvalidCredentials() {
        loginPage.login(Constants.INVALID_EMAIL, Constants.INVALID_PASSWORD);
        String errorText = loginPage.getGlobalErrorMessageText();

        assertTrue(
                errorText.toLowerCase().contains("invalid") || errorText.toLowerCase().contains("unauthorized"),
                ErrorMessages.EXPECTED_INVALID_CREDENTIALS_OR_UNAUTHORIZED
        );
    }

    @Test
    @DisplayName("Blank login fields shows inline validation error")
    public void testBlankFieldsValidation() {
        loginPage.clickLoginButton();

        assertTrue(
                loginPage.isInlineErrorDisplayed(ErrorMessages.EMAIL_AND_PASSWORD_REQUIRED)
        );
    }
}
