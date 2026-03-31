package com.staybnb.tests;

import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testSuccessfulLoginRedirection() {
        loginAsTestUserAndLandOnHome(loginPage);
        assertTrue(driver.getCurrentUrl().contains(Constants.HOME_URL));
    }

    @Test
    public void testSuccessfulLoginTokenRetrieved() {
        loginAsTestUserAndLandOnHome(loginPage);
        String jwt = loginPage.getStaybnbToken();
        assertNotNull(jwt, ErrorMessages.JWT_TOKEN_NOT_RETRIEVED);
    }

    @Test
    public void testInvalidCredentials() {
        loginPage.login(Constants.INVALID_EMAIL, Constants.INVALID_PASSWORD);
        String errorText = loginPage.getGlobalErrorMessageText();
        assertTrue(errorText.toLowerCase().contains("invalid") || errorText.toLowerCase().contains("unauthorized"),
                ErrorMessages.EXPECTED_INVALID_CREDENTIALS_OR_UNAUTHORIZED);
    }

    @Test
    public void testBlankFieldsValidation() {
        loginPage.clickLoginButton();
        assertTrue(loginPage.isInlineErrorDisplayed(ErrorMessages.EMAIL_AND_PASSWORD_REQUIRED));
    }
}
